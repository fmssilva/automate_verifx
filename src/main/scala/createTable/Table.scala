package createTable

import createTable.codeGenerators.{ElemCodeGenerator, ElemTableCodeGenerator, FK_References_Class, Proofs_Class}

import java.io._
import java.nio.file.{Files, Paths}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}


//EXAMPLE OF ANTIDOTE SQL COMMAND TO PROCESS:
//    CREATE UPDATE-WINS TABLE Albums (
//      title VARCHAR PRIMARY KEY,
//      artist VARCHAR foreign key update-wins references Artist(name),
//      year LWW INT check(year >= 1900 AND year <= 2022)
//    )

/**
 * Class to process a given list of commands and generate the veriFx code for a new table
 *
 * @param cmdTokens - all tokens with the AntidoteSQL commands given in file input.txt
 * @param line      - current line of the tokens for processing
 */
@SerialVersionUID(1594622920505253437L)
class Table(cmdTokens: List[(Int, Array[String])], var line: Int, sysTablesMap: mutable.Map[String, Table]) extends Serializable {

  private val TABLE_UPDATE_POLICIES = Array("UPDATE-WINS", "DELETE-WINS", "NO_CONCURRENCY")

  private val tokens_init_cmd_line = line

  /**
   * Get createTable.Table - update-policy, name, and attributes
   */
  private val lineTokens = cmdTokens(line)._2
  println("Line " + line + ": " + lineTokens.mkString(", ") + " \t\t» creating table at CreateTable class ")
  val update_policy: String = getTableUpdatePolicy
  val tableName: String = getTableName
  val attributesList: mutable.Seq[TabAttribute] = getTableAttributes
  val fk_attributes: mutable.Seq[TabAttribute] = attributesList.filter(_.attribInvariant.fk_options.isDefined)
  checkIfFKsReferenceAllPKOfReferencedTable(fk_attributes)

  /**
   * Generate FOLDERS AND CLASSES
   */
  // folder systemTables
  private var systemTablesFolderName = "generatedSysTables"
  private var folderPath = s"./src/main/verifx/$systemTablesFolderName"
  createDirectory(folderPath)

  // folder "createTable.Table"
  folderPath = s"$folderPath/${tableName.toLowerCase()}s"
  createDirectory(folderPath)

  // file Element Class
  private var filePath = s"$folderPath/$tableName.vfx"
  private var classContent = ElemCodeGenerator.generate_Elem_ClassCode(this, cmdTokens.slice(tokens_init_cmd_line, line))
  createClassFile()

  // file Elem TABLE Class
  filePath = s"$folderPath/${tableName}sTable.vfx"
  classContent = ElemTableCodeGenerator.generate_ElemTable_ClassCode(this, systemTablesFolderName)
  createClassFile()

  // file Table_FK_References Class
  val fk_system_name = s"${tableName}s_FK_References"
  if (fk_attributes.nonEmpty) {
    filePath = s"$folderPath/$fk_system_name.vfx"
    classContent = FK_References_Class.generate_FK_References_ClassCode(this, systemTablesFolderName, fk_system_name)
    createClassFile()
  }

  // folder PROOFS directory
  systemTablesFolderName = s"${systemTablesFolderName}Proofs"
  folderPath = s"./src/test/scala/$systemTablesFolderName"
  createDirectory(folderPath)

  // file PROOFS Elem class
  filePath = s"$folderPath/${tableName}Proofs.scala"
  classContent = Proofs_Class.generate_Proofs_ClassCode(this, systemTablesFolderName, fk_system_name)
  createClassFile()



  ////////////////////////////////////////////////////////////////////
  ////////////---------- HELPER METHODS ---------- /////////////////
  ////////////////////////////////////////////////////////////////////

  /**
   * Get createTable.Table Policy
   */
  private def getTableUpdatePolicy: String = {
    lineTokens.lift(1) match { //1 = idx of Update Policy in the create table statement
      case Some(policy) if TABLE_UPDATE_POLICIES.contains(policy) => policy
      case _ => throw new IllegalArgumentException(s"At Create createTable.Table - Line: $line - createTable.Table Update policy has to be of the kind: ${TABLE_UPDATE_POLICIES.mkString(" | ")}")
    }
  }

  /**
   * Get createTable.Table Name
   */
  private def getTableName: String = {
    lineTokens.lift(3) match { //3 = idx of table name in the create table statement
      case Some("(") | None => throw new IllegalArgumentException(s"At Create createTable.Table - At Line: $line - createTable.Table needs to be given a name")
      case Some(name) =>
        val formatedName = name.replaceAll("\\s", "_").toLowerCase.capitalize
        if (sysTablesMap.contains(formatedName))
          throw new IllegalArgumentException(s"At Line: $line - The table name $name is already used in another table")
        formatedName
    }
  }

  /**
   * Get table Attributes
   */
  private def getTableAttributes: ListBuffer[TabAttribute] = {
    println("getting table attributes at getTableAttributes(), at CreateTable class")
    val listOfAttributes = ListBuffer[TabAttribute]()
    line += 1
    while (line < cmdTokens.length && cmdTokens(line)._2(0) != ")") {
      val tokens = cmdTokens(line)._2
      if (tokens(0) == "PRIMARY") {
        tokens match {
          case Array("PRIMARY", "KEY", "(", tail@_*) =>
            tail.foreach {
              case "," | ")" => // Do nothing for these cases
              case str =>
                val at = listOfAttributes.find(_.attribName.toUpperCase() == str)
                if (at.isEmpty)
                  throw new IllegalArgumentException(s"At Create createTable.Table - At Line: $line - Attribute $str must be declared before setting as PK")
                else
                  at.get.setAttribPK()
            }
          case _ =>
            throw new IllegalArgumentException(s"At Create createTable.Table - At Line: $line - To set multiple attributes as PK write: PRIMARY KEY (at1, at2 ... atn), and just AFTER declaring each of those attributes")
        }
      }
      else {
        listOfAttributes.addOne(new TabAttribute(tokens, line, sysTablesMap))
      }
      line += 1
    }
    line += 1 //to pass the line of closing ) of the create table command
    listOfAttributes
  }

  private def checkIfFKsReferenceAllPKOfReferencedTable(fk_attributes: mutable.Seq[TabAttribute]): Unit = {
    fk_attributes.foreach { at =>
      val referencedTable = at.attribInvariant.getReferencedTable
      val refTab_PKs = referencedTable.attributesList.filter(_.attribInvariant.isPrimaryKey)
      if (!refTab_PKs.forall(pk => fk_attributes.exists(att_in => att_in.attribInvariant.getReferencedPK.attribName.equals(pk.attribName))))
        throw new IllegalArgumentException(s"createTable.Table $tableName should reference all PK's of the referenced table " + referencedTable.tableName)
    }
  }


  /**
   * Create Directories
   */
  private def createDirectory(folderPath: String): Unit = {
    println("creating directory     $folderPath      at createDirectory() at CreateTable class")
    //TODO: do the delete table to clean the packages and table system map...
    if (!Files.exists(Paths.get(folderPath)))
      Files.createDirectories(Paths.get(folderPath))
  }


  /**
   * Create Element Class File
   */
  private def createClassFile(): Unit = {
    var writer: PrintWriter = null
    try {
      writer = new PrintWriter(new File(filePath))
      writer.println(classContent)
    } catch {
      case e: IOException => println(s"Error writing in the file: ${e.getMessage}")
    } finally {
      if (writer != null) {
        Try(writer.close()) match {
          case Success(_) => println(s"Content written to $filePath")
          case Failure(ex) => println(s"Error closing writer: ${ex.getMessage}")
        }
      }
    }
  }


  override def toString: String = {
    s"createTable.Table: $tableName - $update_policy: ${attributesList.map("\n\t\t\t\t" + _.toString).mkString("; ")}"
  }

}
