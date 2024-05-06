package createTable

import antidoteSQL_to_veriFx.System_Constants._
import antidoteSQL_to_veriFx.System_Utils._
import createTable.codeGenerators._

import java.io._
import scala.collection.mutable
import scala.collection.mutable.ListBuffer


/**
 * Class to process a given list of commands and generate the veriFx code for a new table
 *
 * Example of a command to process:
 *
 * .   CREATE UPDATE-WINS TABLE Albums (
 * .        title VARCHAR PRIMARY KEY,
 * .        artist VARCHAR foreign key update-wins references Artist(name),
 * .        year LWW INT check(year >= 1900 AND year <= 2022)
 * .  )
 *
 * @param cmdTokens - all tokens with the AntidoteSQL commands given in file input.aql
 * @param line      - current line of the tokens for processing
 */
@SerialVersionUID(1594622920505253437L)
class Table(cmdTokens: List[(Int, Array[String])], var line: Int, sysTablesMap: mutable.Map[String, Table]) extends Serializable {

  private val initial_cmd_line = line
  private val lineTokens = cmdTokens(line)._2
  println("Line " + cmdTokens(line)._1 + ": " + lineTokens.mkString(", ") + " \t\t» creating table at Table class ")

  //UPDATE POLICY
  val update_policy: String = getTableUpdatePolicy

  // TABLE NAMES: (Element, ElementsTable, Elements_FK_System)
  val tableNames: (String, String, String) = getTableNames

  // ATTRIBUTES + PKs + FKs
  val attributesList: mutable.Seq[TabAttribute] = getTableAttributes
  val pk_attributes: mutable.Seq[TabAttribute] = attributesList.filter(_.attribInvariant.isPrimaryKey)
  val (pk_name, pk_data_type) = setTablePK()
  val fk_attributes: mutable.Seq[TabAttribute] = attributesList.filter(_.attribInvariant.fk_options.isDefined)
  checkIf_FKs_ReferenceAllPK_Of_ReferencedTable()

  /**
   * Generate FOLDERS AND CLASSES
   */
  // folder systemTables
  createDirectory(SYSTEM_TABLES_FOLDER_PATH)

  // folder "Table"
  private val folderPath = s"$SYSTEM_TABLES_FOLDER_PATH/${tableNames._1.toLowerCase()}s"
  createDirectory(folderPath)

  // file Element Class
  private var filePath = s"$folderPath/${tableNames._1}.vfx"
  private var classContent = ElemCodeGenerator.generate_Elem_ClassCode(this, cmdTokens.slice(initial_cmd_line, line))
  createClassFile(classContent, filePath)

  // file Elem TABLE Class
  filePath = s"$folderPath/${tableNames._2}.vfx"
  classContent = ElemTableCodeGenerator.generate_ElemTable_ClassCode(this)
  createClassFile(classContent, filePath)

  // file Table_FK_System Class
  if (fk_attributes.nonEmpty) {
    filePath = s"$folderPath/${tableNames._3}.vfx"
    classContent = FK_System_Class.generate_FK_System_ClassCode(this)
    createClassFile(classContent, filePath)
  }

  // folder PROOFS directory
  createDirectory(SYSTEM_PROOFS_FOLDER_PATH)

  // file ProverUtils
  filePath = s"$SYSTEM_PROOFS_FOLDER_PATH/ProverUtils.scala"
  classContent = Proofs_Utils_Class.generate_ProverUtils_ClassCode()
  createClassFile(classContent, filePath)

  // file PROOFS Elem class
  filePath = s"$SYSTEM_PROOFS_FOLDER_PATH/${tableNames._1}Proofs.scala"
  classContent = Proofs_Class.generate_Proofs_ClassCode(this)
  createClassFile(classContent, filePath)



  ////////////////////////////////////////////////////////////////////
  ////////////---------- HELPER METHODS ---------- /////////////////
  ////////////////////////////////////////////////////////////////////

  /**
   * Get Table Policy
   */
  private def getTableUpdatePolicy: String = {
    lineTokens.lift(1) match { //1 = idx of Update Policy in the create table statement
      case Some(policy) if TABLE_UPDATE_POLICIES.contains(policy) => policy
      case _ => throw new IllegalArgumentException(s"At Create Table - Line: ${cmdTokens(line)._1} - Table Update policy has to be of the kind: ${TABLE_UPDATE_POLICIES.mkString(" | ")}")
    }
  }

  /**
   * Get Table Names of the format: (Element, ElementsTable, Elements_FK_System)
   */
  private def getTableNames: (String, String, String) = {
    lineTokens.lift(3) match { //3 = idx of table name in the create table statement
      case Some("(") | None => throw new IllegalArgumentException(s"At Create Table - At Line: ${cmdTokens(line)._1} - Table needs to be given a name")
      case Some(name) =>
        val elemName = name.replaceAll("\\s", "_").toLowerCase.capitalize //change spaces in the name by _  and capitalize
        if (sysTablesMap.contains(elemName))
          throw new IllegalArgumentException(s"At Line: ${cmdTokens(line)._1} - The table name $name is already used in another table")
        (elemName, elemName + "sTable", elemName + "_FK_System")
    }
  }

  /**
   * Get table Attributes
   */
  private def getTableAttributes: ListBuffer[TabAttribute] = {
    println("getting table attributes at getTableAttributes(), at Table class")
    val listOfAttributes = ListBuffer[TabAttribute]()
    line += 1
    while (line < cmdTokens.length && cmdTokens(line)._2(0) != ")") {
      val tokens = cmdTokens(line)._2
      if (tokens(0) == PRIMARY) { // when multiple attributes are declared first and just after that they are set as PK, ex: PRIMARY KEY (at1, at2 ... atn)
        tokens match {
          case Array(PRIMARY, KEY, "(", tail@_*) =>
            tail.foreach {
              case "," | ")" => // Do nothing for these cases
              case word =>
                val declared_att = listOfAttributes.find(_.attribName.toUpperCase() == word)
                if (declared_att.isEmpty)
                  throw new IllegalArgumentException(s"At Create Table - At Line: ${cmdTokens(line)._1} - Attribute $word must be declared before setting as PK")
                declared_att.get.attribInvariant.setAttribPK()
            }
          case _ =>
            throw new IllegalArgumentException(s"At Create Table - At Line: ${cmdTokens(line)._1} - To set multiple attributes as PK write: PRIMARY KEY (at1, at2 ... atn), and just AFTER declaring each of those attributes")
        }
      }
      else
        listOfAttributes.addOne(new TabAttribute(tokens, cmdTokens(line)._1, sysTablesMap))
      line += 1
    }
    line += 1 //to pass the line of closing ) of the create table command
    listOfAttributes
  }

  /**
   * Set the PK of the table
   */
  private def setTablePK(): (String, String) = {
    if (pk_attributes.isEmpty)
      throw new IllegalArgumentException(s"At Create Table - ${tableNames._1} should have at least one attribute as PRIMARY KEY")
    if (pk_attributes.size == 1)
      (pk_attributes.head.attribName, pk_attributes.head.attribDataType)
    else
      (tableNames._1.toLowerCase() + "_PKs", tableNames._1 + "_PKs")
  }

  /**
   * Check if the FKs of this table reference all the PKs of the referenced tables
   * Example, if Artist has PKs (name, age), then Album should reference both name and age
   */
  private def checkIf_FKs_ReferenceAllPK_Of_ReferencedTable(): Unit = {
    fk_attributes.foreach { at =>
      val referencedTable = at.attribInvariant.getReferencedTable
      if (!referencedTable.pk_attributes.forall(refTab_pk => fk_attributes.exists(_.attribInvariant.getReferenced_PK.attribName.equals(refTab_pk.attribName))))
        throw new IllegalArgumentException(s"Table ${tableNames._1} should reference all PK's of the referenced table ${referencedTable.tableNames._1}")
    }
  }


  /**
   * toString
   */
  override def toString: String = {
    s"Table: ${tableNames._1} - $update_policy: ${attributesList.map("\n\t\t\t" + _.toString).mkString("; ")}"
  }

}
