import java.io._
import java.nio.file.{Files, Paths}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}


//EXAMPLE OF ANTIDOTE SQL COMMAND TO PROCCESS:
//    CREATE UPDATE-WINS TABLE Albums (
//      title VARCHAR PRIMARY KEY,
//      artist VARCHAR foreign key update-wins references Artist(name),
//      year LWW INT check(year >= 1900 AND year <= 2022)
//    )

/**
 * Class to process a given list of commands and generate the veriFx code for a new table
 *
 * @param cmdTokens
 * @param line
 */
@SerialVersionUID(1594622920505253437L)
class Table(cmdTokens: List[(Int, Array[String])], var line: Int, sysTablesMap: mutable.Map[String, Table]) extends Serializable {
  private val TABLE_UPDATE_POLICIES = Array("UPDATE-WINS", "DELETE-WINS", "NO_CONCURRENCY")

  private val tokens_initial_line = line

  /**
   * Get Table - update-policy, name, and attributes
   */
  private val lineTokens = cmdTokens(line)._2
  println("Line " + line + ": " + lineTokens.mkString(", ") + " \t\t» creating table at CreateTable class ")
  val update_policy = getTableUpdatePolicy()
  val tableName: String = getTableName()
  val attributesList: mutable.Seq[TabAttribute] = getTableAttributes()
  val fk_attributes = attributesList.filter(_.attribInvariant.fk_options.isDefined)
  println("\n\n\n "+ fk_attributes)
  checkIfTableFKsReferenceAllPK(fk_attributes)


  /**
   * Create FOLDERS AND CLASSES
   */
  // folder systemTables
  private var systemTablesFolderName = "systemTables"
  private var folderPath = s"./src/main/verifx/$systemTablesFolderName"
  createDirectory(folderPath)

  // folder "Table"
  folderPath = s"$folderPath/${tableName.toLowerCase()}s"
  createDirectory(folderPath)

  // file Element Class
  private var filePath = s"$folderPath/${tableName}.vfx"
  private var classContent = ElemCodeGenerator.generate_Elem_ClassCode(this, cmdTokens.slice(tokens_initial_line, line))
  createClassFile()

  // file Elem TABLE Class
  filePath = s"$folderPath/${tableName}sTable.vfx"
  classContent = ElemTableCodeGenerator.generate_ElemTable_ClassCode(this, systemTablesFolderName)
  createClassFile()

  // file Table_FK_References Class
  val fk_system_name = s"${tableName}s_FK_System"
  if (fk_attributes.nonEmpty) {
    filePath = s"$folderPath/${fk_system_name}.vfx"
    classContent = FK_References_Class.generate_FK_References_ClassCode(this, systemTablesFolderName, fk_system_name)
    createClassFile()
  }





  // folder PROOFS directory
  systemTablesFolderName = s"${systemTablesFolderName}Proofs"
  folderPath = s"./src/test/scala/${systemTablesFolderName}"
  createDirectory(folderPath)
  // file PROOFS Elem class
  filePath = s"$folderPath/${tableName}Proofs.scala"
  classContent = generate_Proofs_ClassCode()
  createClassFile()





  ////////////////////////////////////////////////////////////////////
  ////////////---------- AUXILIAR METHODS ---------- /////////////////
  ////////////////////////////////////////////////////////////////////

  /**
   * Get Table Policy
   */
  private def getTableUpdatePolicy(): String = {
    lineTokens.lift(1) match { //1 = idx of Update Policy in the create table statement
      case Some(policy) if TABLE_UPDATE_POLICIES.contains(policy) => policy
      case _ => throw new IllegalArgumentException(s"At Create Table - Line: $line - Table Update policy has to be of the kind: ${TABLE_UPDATE_POLICIES.mkString(" | ")}")
    }
  }

  /**
   * Get Table Name
   */
  private def getTableName(): String = {
    lineTokens.lift(3) match { //3 = idx of table name in the create table statement
      case Some("(") | None => throw new IllegalArgumentException(s"At Create Table - At Line: $line - Table needs to be given a name")
      case Some(name) =>
        val formatedName = name.replaceAll("\\s", "_").toLowerCase.capitalize
        if (sysTablesMap.contains(formatedName))
          throw new IllegalArgumentException(s"At Line: $line - The table name ${name} is already used in another table")
        println("\n\n\n  " + formatedName)
        formatedName
    }
  }

  /**
   * Get table Attributes
   *
   * @Pre: each instruction is in a new line
   */
  private def getTableAttributes(): ListBuffer[TabAttribute] = {
    println("geting table attributes at getTableAttributes(), at CreateTable class")
    val listOfAttributes = ListBuffer[TabAttribute]()
    line += 1
    while (line < cmdTokens.length && cmdTokens(line)._2(0) != ")") {
      val tokens = cmdTokens(line)._2
      if (tokens(0) == "PRIMARY") {
        tokens match {
          case Array("PRIMARY", "KEY", "(", tail@_*) =>
            tail.foreach {
              case "," | ")" => // Do nothing for these cases
              case str => {
                val at = listOfAttributes.find(_.attribName.toUpperCase() == str)
                if (at.isEmpty)
                  throw new IllegalArgumentException(s"At Create Table - At Line: $line - Attribute $str must be declared before setting as PK")
                else
                  at.get.setAttribPK()
              }
            }
          case _ =>
            throw new IllegalArgumentException(s"At Create Table - At Line: $line - To set multiple attributes as PK write: PRIMARY KEY (at1, at2 ... atn), and just AFTER declaring each of those attributes")
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

  private def checkIfTableFKsReferenceAllPK(fk_attributes: mutable.Seq[TabAttribute]): Unit = {
    fk_attributes.foreach { at =>
      val referencedTable = at.attribInvariant.getReferencedTable()
      val refTab_PKs = referencedTable.attributesList.filter(_.attribInvariant.isPrimaryKey)
      if (!refTab_PKs.forall(pk => fk_attributes.exists(att_in => att_in.attribInvariant.getReferencedPK().attribName.equals(pk.attribName))))
        throw new IllegalArgumentException(s"Table ${tableName} should reference all PK's of the referenced table " + referencedTable.tableName)
    }
  }


  /**
   * Create Directories
   */
  private def createDirectory(folderPath: String): Unit = {
    println("creating directory     $folderPath      at createDirectory() at CreateTable class")
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


  /**
   * ///////////////////////////////////////////////////////
   * ///////////////--------- PROOFS ---------///////////////
   * ///////////////////////////////////////////////////////
   */
  private def generate_Proofs_ClassCode(): StringBuilder = {
    println("generating file code at generate_Proofs_ClassCode() at CreateTable class")

    val classContent = new StringBuilder

    // PACKAGE
    classContent.append(s"package ${systemTablesFolderName}")

    // IMPORTS
    classContent.append(s"\n\nimport be.vub.kdeporre.crdtproofs.Prover" +
      s"\nimport org.scalatest.FlatSpec")

    // CLASS HEADER
    classContent.append(s"\n\nclass ${tableName}Proofs extends FlatSpec with Prover { ")

    // Helper methods to generate comment
    def generateProofFunctionsComments(comment_str: String) = {
      classContent.append(
        s"\n\n\t////////////////////////////////////////////////////////////" +
          s"\n\t// ${comment_str} " +
          s"\n\t////////////////////////////////////////////////////////////")
    }

    // Helper methods to generate given proofs for a given Element or Table name
    def generateProofFunction(proofs: mutable.LinkedHashMap[String, String], elemNameToProve: String): Unit = {
      proofs.foreach { case (key, value) =>
        classContent.append(
          s"""\n\n\t\"${elemNameToProve}\" should \"${key}\" in {""" +
            s"""\n\t\tval p = (\"${elemNameToProve}\", \"${value}\") """ +
            s"""\n\t\tprove(p)""" +
            s"""\n\t\tp""" +
            s"""\n\t}"""
        )
      }
    }

    // GENERAL PROOFS OF ELEMENT - CvRDTProof
    generateProofFunctionsComments(s"GENERAL PROOFS OF ELEMENT:  ${tableName}  - specified in CvRDTProof trait")
    var proofs = mutable.LinkedHashMap(
      "be a CvRDT" -> "is_a_CvRDT", // WORKS
      "compatible commutes" -> "compatibleCommutes", // WORKS
      "compare correct" -> "compareCorrect") // WORKS
    generateProofFunction(proofs, tableName)

    // PROOFS OF ELEMENT FOR UPDATABLE ATTRIBUTES - object TableName extends CvRDTProof
    generateProofFunctionsComments(s"PROOFS FOR UPDATABLE ATTRIBUTES OF ELEMENT - specified in object TableName extends CvRDTProof")
    proofs = mutable.LinkedHashMap() //updateYear for each concurrently updatable attribute
    attributesList.foreach( //        WORKS
      at => if (at.allowConcurrentUpdates)
        proofs += s"update${at.attribName.capitalize} works" -> s"${tableName}_update${at.attribName.capitalize}_works"
    )
    generateProofFunction(proofs, tableName)

    // GENERAL PROOFS OF elem TABLE
    generateProofFunctionsComments(s"GENERAL PROOFS OF TABLE:  ${tableName}sTable - specified in CvRDTProof1 trait")
    proofs = mutable.LinkedHashMap(
      "be commutative" -> "mergeCommutative", //WORKS
      "be idempotent" -> "mergeIdempotent", //REBENTA???
      "be associative" -> "mergeAssociative", //REBENTA???
      "be associative2" -> "mergeAssociative2",
      "compatible commutes" -> "compatibleCommutes"
    )
    generateProofFunction(proofs, s"${tableName}sTable")

    // FK REFERENTIAL PROOFS
    if (fk_attributes.nonEmpty) {
      generateProofFunctionsComments(s"FK PROOFS    ")
      proofs = mutable.LinkedHashMap(
        "maintain referential integrity generic proof" -> "genericReferentialIntegrity", //REBENTA
        "be commutative" -> "mergeCommutative", // WORKS
        "be idempotent" -> "mergeIdempotent", // REBENTA
        "be associative" -> "mergeAssociative", // REBENTA
        "be associative (with assumptions)" -> "mergeIsAssociative", // REBENTA
        "be associative2" -> "mergeAssociative2", // REBENTA
        "compatible commutes" -> "compatibleCommutes" // WORKS
      )
      generateProofFunction(proofs, fk_system_name)

    }

    //CLASS CLOSE
    classContent.append(s"\n\n}")

  }


  override def toString: String = {
    s"Table: $tableName - $update_policy: ${attributesList.map("\n\t\t\t\t" + _.toString).mkString("; ")}"
  }

}
