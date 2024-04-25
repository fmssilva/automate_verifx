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
  private val CRDT_CLOCK = "LamportClock" //clock to be used for CRDTs

  private val tokens_initial_line = line

  /**
   * Get Table - update-policy, name, and attributes
   */
  private val lineTokens = cmdTokens(line)._2
  println("Line " + line + ": " + lineTokens.mkString(", ") + " \t\t» creating table at CreateTable class ")
  private val update_policy = getTableUpdatePolicy()
  val tableName: String = getTableName()
  val attributesList: mutable.Seq[TabAttribute] = getTableAttributes()

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
  private var classContent = generate_Elem_ClassCode()
  createClassFile()
  // file Elem TABLE Class
  filePath = s"$folderPath/${tableName}sTable.vfx"
  classContent = generate_ElemTable_ClassCode()
  createClassFile()
  // file Table_FK_References Class
  val fk_attributes: mutable.Seq[TabAttribute] = attributesList.filter(_.attribInvariant.fk_options.isDefined)
  val fk_system_name = s"${tableName}s_FK_System"
  if (fk_attributes.nonEmpty) {
    filePath = s"$folderPath/${fk_system_name}.vfx"
    classContent = generate_FK_References_ClassCode()
    createClassFile()
  }
  // folder TESTS directory
  systemTablesFolderName = s"${systemTablesFolderName}Tests"
  folderPath = s"./src/test/scala/${systemTablesFolderName}"
  createDirectory(folderPath)
  // file TESTS Elem class
  filePath = s"$folderPath/${tableName}Tests.scala"
  classContent = generate_Tests_ClassCode()
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
      listOfAttributes.addOne(new TabAttribute(cmdTokens(line)._2, line, sysTablesMap))
      line += 1
    }
    line += 1 //to pass the line of closing ) of the create table command
    listOfAttributes
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
   * /////////////////       ELEMENT       /////////////////
   * ///////////////////////////////////////////////////////
   */
  private def generate_Elem_ClassCode(): StringBuilder = {
    println("generating file code at generate_Elem_ClassCode() at CreateTable class")

    val classContent = new StringBuilder

    //IMPORTS
    classContent.append(
      "import antidote.crdts.lemmas.CvRDT" +
        s"\nimport antidote.crdts.lemmas.CvRDTProof" +
        //TODO: confirmar mais imports pra outros tipos de dados além de "LWWRegister"??
        (if (attributesList.exists(a => a.attribDataType_CRDT.contains("LWWRegister")))
          s"\nimport antidote.crdts.registers.LWWRegister" +
            s"\nimport antidote.crdts.${CRDT_CLOCK}"
        else s""
          )
    )

    //CLASS COMMENTS
    classContent.append(
      s"\n\n/**\n * Class representing the element:   ${tableName.toUpperCase()} " +
        s"\n * given by the Antidote SQL command:" +
        cmdTokens.slice(tokens_initial_line, line).map(
          cmdToken => s"\n *\t\t${cmdToken._2.mkString(" ")}"
        ).mkString +
        s"\n */"
    )

    //CLASS HEADER
    classContent.append(
      s"\nclass $tableName(" +
        attributesList.map(
          at => s"${at.attribName}: ${if (at.allowConcurrentUpdates) at.attribDataType_CRDT else at.attribDataType}"
        ).mkString(", ") +
        s") extends CvRDT[$tableName] {"
    )

    //Implement Methods DECLARED in CvRDT trait
    classContent.append("\n\n\n\t//Implementation of methods DECLARED in CvRDT trait")

    // MERGE
    classContent.append(
      s"\n\n\tdef merge(that: $tableName) = " +
        s"\n\t\tnew $tableName(" +
        attributesList.map(
          at =>
            if (at.allowConcurrentUpdates) s"this.${at.attribName}.merge(that.${at.attribName})"
            else s"this.${at.attribName}"
        ).mkString(", ") +
        ")"
    )

    // COMPARE
    val updatablesAttributes = attributesList.filter(at => at.allowConcurrentUpdates)
    classContent.append(
      s"\n\n\tdef compare(that: $tableName) = " +
        (if (updatablesAttributes.isEmpty)
          s"\n\t\ttrue"
        else
          updatablesAttributes.map(
            at => s"\n\t\tthis.${at.attribName}.compare(that.${at.attribName})").mkString(" &&")
          )
    )

    //Override Methods IMPLEMENTED in CvRDT trait
    classContent.append("\n\n\n\t//Override Methods IMPLEMENTED in CvRDT trait")

    //REACHABLE
    //if we have no CHECKs, we don't need to override. it's always TRUE
    val attributesWithChecks = attributesList.filter(at => at.attribInvariant.check_options.isDefined)
    if (attributesWithChecks.nonEmpty) {
      classContent.append(
        s"\n\n\toverride def reachable() = { " +
          attributesWithChecks.map { at =>
            "\n\t\t" + at.attribInvariant.check_options.get.map {
              case attrib if (attributesList.exists(_.attribName.toUpperCase().equals(attrib))) => s" this.${attrib.toLowerCase()}.value"
              case "AND" => " &&"
              case "OR" => " ||"
              case other => s" $other"
              //TODO: falta outras coisas como IN, BETWEEN ... e arvore de parentesis...
            }.mkString
          }.mkString(" && ") +
          "\n\t}"
      )
    }

    // COMPATIBLE
    classContent.append(
      s"\n\n\toverride def compatible(that: $tableName) = \n\t\t" +
        attributesList.map(
          at =>
            if (at.allowConcurrentUpdates)
              s"this.${at.attribName}.compatible(that.${at.attribName})"
            else
              s"this.${at.attribName} == that.${at.attribName}"
        ).mkString(" &&\n\t\t")
    )


    //UPDATE
    //Implement methods for concurrently updatable attributes to be used by the tester, useful in CmRDT - Operations
    if (updatablesAttributes.nonEmpty) {
      //generate comment
      classContent.append("\n\n\n\t//Implement methods for concurrently updatable attributes to be used by the tester,\n\t//useful in CmRDT - Operations")

      //helper method
      def code_for_newElement(atName: String) = {
        s"\n\t\t\tnew $tableName(" +
          attributesList.map(
            at =>
              if (at.attribName.equals(atName))
                s"this.${at.attribName}.assign(new${atName.capitalize}, stamp${atName.capitalize})"
              else
                s"this.${at.attribName}"
          ).mkString(", ") + ")"
      }

      // updateAtribute() generation code
      updatablesAttributes.map {
        at =>
          val atName = at.attribName.capitalize
          classContent.append(
            s"\n\n\tdef update${atName}(new${atName}: ${at.attribDataType.capitalize}, stamp${atName}: ${CRDT_CLOCK}) = { " +
              (if (at.attribInvariant.check_options.isDefined) {
                s"\n\t\tif(" + at.attribInvariant.check_options.get.map {
                  case word if word.equals(at.attribName.toUpperCase) => s" new$atName"
                  case "AND" => " &&"
                  case "OR" => " ||"
                  case other => s" $other"
                }.mkString + ")" +
                  code_for_newElement(at.attribName) +
                  "\n\t\telse" +
                  "\n\t\t\tthis"
              } else
                code_for_newElement(at.attribName)
                ) +
              "\n\t}"
          )
      }
    }

    // CLASS CLOSING
    classContent.append("\n\n}")


    //PROOF OBJECT (INSIDE THE ELEM CLASS)
    if (updatablesAttributes.isEmpty)
      classContent.append("")
    else {
      //OBJECT COMMENTS
      classContent.append("\n\n\n\n\n/*\n* Object to implement the proof functions for the updatable attributes\n*/")

      //OBJECT HEADER
      classContent.append(
        s"\n\nobject ${tableName} extends CvRDTProof[${tableName}] {")

      // UPDATE PROOFS
      attributesList.filter(at => at.allowConcurrentUpdates).map {
        at =>
          val atName = at.attribName
          classContent.append(
            s"\n\n\tproof ${tableName}_update${atName.capitalize}_works {" +
              //forall (...)
              s"\n\t\tforall(elem: $tableName, ${atName}1: ${at.attribDataType}, ${atName}2: ${at.attribDataType}, c1: $CRDT_CLOCK, c2: $CRDT_CLOCK ) {" +
              // assumptions to start
              s"\n\t\t\t( elem.reachable()  &&  c1.smaller(c2)" +
              (if (at.attribInvariant.check_options.isDefined) {
                s" &&" +
                  s"\n\t\t\t  " +
                  (for (i <- 1 to 2) yield {
                    at.attribInvariant.check_options.get.map {
                      case word if (word.equals(atName.toUpperCase)) => s"${atName}$i"
                      case "AND" => "&&"
                      case "OR" => "||"
                      case other => s"$other"
                      //todo falta outras coisas como IN, BETWEEN ... e arvore de parentesis...
                    }.mkString(" ")
                  }).mkString(" && ")
              } else ""
                ) + "\n\t\t\t ) =>: {" +
              // imply => {
              s"\n\t\t\t\t //simulate the update of the element in 2 replicas, creating elem 1 and 2, and then merging them in elem12" +
              s"\n\t\t\t\t val elem1 = elem.update${atName.capitalize}(${atName}1, c1)" +
              s"\n\t\t\t\t val elem2 = elem.update${atName.capitalize}(${atName}2, c2)" +
              s"\n\t\t\t\t val elem12 = elem1.merge(elem2)" +
              s"\n\t\t\t\t //check if the update in elem 1 and 2 kept the correct values" +
              attributesList.map(at_in =>
                if (at_in.attribName.equals(atName))
                  s"\n\t\t\t\t elem1.${atName}.value == ${atName}1 && elem2.${atName}.value == ${atName}2"
                else
                  s"\n\t\t\t\t elem1.${at_in.attribName} == elem.${at_in.attribName} && elem2.${at_in.attribName} == elem.${at_in.attribName}"
              ).mkString(" &&") + " &&" +
              s"\n\t\t\t\t //check if the merged values are correct and according to the chosen update-policy " +
              attributesList.map(at_in =>
                if (at_in.attribName.equals(atName)) {
                  if (at_in.attribPolicy.equals("LWW") || at_in.attribPolicy.equals("NO_CONCURRENCY"))
                    s"\n\t\t\t\t elem12.${atName}.value == ${atName}2"
                  else if (at_in.attribPolicy.equals("ADDITIVE"))
                    s"\n\t\t\t\t elem12.${atName}.value == elem.${atName} + ${atName}1 + ${atName}2"
                  else //MULTI-VALUE
                    s"\n\t\t\t\t elem12.${atName}.value == ???????" //TODO: ??????
                } else
                  s"\n\t\t\t\t elem12.${at_in.attribName} == elem.${at_in.attribName}"
              ).mkString(" &&") +
              s"\n\t\t\t}" +
              s"\n\t\t}" +
              s"\n\t}"
          )
      }

      //OBJECT CLOSING
      classContent.append("\n\n}")
    }
  }


  /**
   * ///////////////////////////////////////////////////////
   * /////////////--------- Elem_Table---------/////////////
   * ///////////////////////////////////////////////////////
   */
  private def generate_ElemTable_ClassCode(): StringBuilder = {
    println("generating file code at generate_ElemTable_ClassCode() at CreateTable class")

    val classContent = new StringBuilder

    // IMPORTS
    classContent.append(
      s"import ${systemTablesFolderName}.${tableName.toLowerCase()}s.${tableName}" +
        s"\nimport antidote.crdts.lemmas.CvRDTProof1" +
        (update_policy match {
          case "UPDATE-WINS" => s"\nimport antidote.crdts.tables.UWTable"
          //TODO: outros casos
        })
    )

    //CLASS HEADER
    classContent.append(
      s"\n\nclass ${tableName}sTable[Time] (before: (Time, Time) => Boolean, " +
        s"\n\t\t\t\telements: Map[String, Tuple[${tableName}, MVRegister[Int,Time]]])" +
        (update_policy match {
          case "UPDATE-WINS" => s"\n\t\t\t\textends UWTable[${tableName}, Time, ${tableName}sTable[Time]]{ "
          case "DELETE-WINS" => throw new Exception("check args of DWTable trait")
          case "NO_CONCURRENCY" => throw new Exception("What to do with table NO_CONCURRENCY")
        })
    )

    classContent.append("\n\n\n\t//Implement Methods DECLARED in UWTable trait")
    //COPY
    classContent.append(
      s"\n\n\tdef copy(newElements: Map[String, Tuple[${tableName}, MVRegister[Int, Time]]]) =" +
        s"\n\t\t    new ${tableName}sTable(this.before, newElements)"
    )

    //MAINTAIN STATE
    classContent.append(s"\n\n\tdef maintainState() = this")

    //END CLASS
    classContent.append(s"\n\n}")

    //OBJECT CLASS
    classContent.append(s"\n\nobject ${tableName}sTable extends CvRDTProof1[${tableName}sTable]")

  }


  /**
   * ///////////////////////////////////////////////////////
   * //////////-------- FK_SYSTEM_CLASS --------////////
   * ///////////////////////////////////////////////////////
   */
  private def generate_FK_References_ClassCode() = {
    println("generating file code at generate_FK_References_ClassCode() at CreateTable class")

    val referencedTableNames = fk_attributes.map(_.attribInvariant.fk_options.map(_.referencedTable.tableName).get).toSet.toList
    val at_name = attributesList.head.attribName
    val fk_tab_name = referencedTableNames.head.toLowerCase()
    val this_tab_name = tableName.toLowerCase()


    val classContent = new StringBuilder

    //IMPORTS
    classContent.append(
      s"import antidote.crdts.lemmas.CvRDT" +
        s"\nimport antidote.crdts.LamportClock" +
        s"\nimport antidote.crdts.VersionVector" +
        s"\nimport antidote.crdts.lemmas.CvRDTProof1" +
        //TODO: import de CvRDTProof 2...
        s"\nimport ${systemTablesFolderName}.${this_tab_name}s.${tableName}sTable" +
        referencedTableNames.map(t_Name =>
          s"\nimport ${systemTablesFolderName}.${t_Name.toLowerCase()}s.${t_Name}sTable"
        ).mkString
    )

    // CLASS HEADER
    classContent.append(
      s"\n\nclass ${fk_system_name}[Time](" +
        s"${this_tab_name}s: ${tableName}sTable[Time]," +
        referencedTableNames.map(
          t_Name =>
            s" ${t_Name.toLowerCase()}s: ${t_Name}sTable[Time]"
        ).mkString(", ")
        + s") \n\t\t\t extends CvRDT[${fk_system_name}[Time]] {"
    )


    //override default implementation of methods in CvRDT trait
    classContent.append("\n\n\t//override default implementation of methods in CvRDT trait")

    //REACHABLE
    classContent.append(
      s"\n\n\toverride def reachable(): Boolean =" +
        s"\n\t\tthis.${this_tab_name}s.reachable() && this.${fk_tab_name}s.reachable()"
    )

    //COMPATIBLE
    classContent.append(
      s"\n\n\toverride def compatible(that: ${fk_system_name}[Time]): Boolean =" +
        s"\n\t\tthis.${this_tab_name}s.compatible(that.${this_tab_name}s) &&" +
        s" this.${fk_tab_name}s.compatible(that.${fk_tab_name}s)"
    )

    //EQUALS
    classContent.append(
      s"\n\n\toverride def equals(that: ${fk_system_name}[Time]) =" +
        s"\n\t\tthis == that"
    )


    //implement declared methods in CvRDT trait
    classContent.append("\n\n\t//implement declared methods in CvRDT trait")

    //MERGE
    classContent.append(
      s"\n\n\tdef merge(that: ${fk_system_name}[Time]) =" +
        s"\n\t\tnew ${fk_system_name}(this.${this_tab_name}s.merge(that.${this_tab_name}s), " +
        s"this.${fk_tab_name}s.merge(that.${fk_tab_name}s))"
    )

    //COMPARE
    classContent.append(
      s"\n\n\tdef compare(that: ${fk_system_name}[Time]) = //ignore" +
        s"\n\t\ttrue"
    )


    //methods to use in the Ref Integrity Proof
    classContent.append("\n\n\t//methods to use in the Ref Integrity Proof")

    //DEF REF_INTEGRITY_PROOF
    classContent.append(
      s"\n\n\tdef refIntegrityHolds(${at_name}: ${fk_attributes.head.attribDataType}) = {" +
        s"\n\t\t(this.${this_tab_name}s.isVisible(${at_name}) " +
        s"\n\t\t) =>: {" +
        s"\n\t\t\tval ${this_tab_name} = this.${this_tab_name}s.get(${at_name})" +
        s"\n\t\t\tthis.${fk_tab_name}s.isVisible(${this_tab_name}.fst.${at_name})" +
        s"\n\t\t}" +
        s"\n\t}"
    )




    //other methods??
    classContent.append("\n\n\t//other methods??")

    classContent.append(
      s"\n\n\tdef reachableWithAssociativityAssumptions(): Boolean = {" +
        s"\n\t\tthis.${this_tab_name}s.reachable() && this.${fk_tab_name}s.reachable() &&" +
        s"\n\t\t\tthis.mergeValuesAssumptions()" +
        s"\n\t}"
    )

    classContent.append(
      s"\n\n\tprivate def mergeValuesAssumptions(): Boolean = {" +
        s"\n\t\tforall(v1: ${tableName}sTable[Time], v2: ${tableName}sTable[Time], v3: ${tableName}sTable[Time]) {" +
        s"\n\t\t  v1.merge(v2).merge(v3) == v1.merge(v2.merge(v3)) //merge is associative" +
        s"\n\t\t} &&" +
        s"\n\t\tforall(v1: ${fk_tab_name.capitalize}sTable[Time], v2: ${fk_tab_name.capitalize}sTable[Time], v3: ${fk_tab_name.capitalize}sTable[Time]) {" +
        s"\n\t\t   v1.merge(v2).merge(v3) == v1.merge(v2.merge(v3)) //merge is associative" +
        s"\n\t\t}" +
        s"\n\t}"
    )

    // CLASS END
    classContent.append("}")


    //OBJECT
    classContent.append("\n\n\t//OBJECT FOR OTHER PROOFS")

    //OBJECT HEADER
    classContent.append(
      s"\n\nobject ${fk_system_name} extends CvRDTProof1[${fk_system_name}] {"
    )

    //REF_INTEGRITY
    classContent.append(
      s"\n\n\tproof genericReferentialIntegrity[Time] {" +
        s"\n\t\tforall(s1: ${fk_system_name}[Time], s2: ${fk_system_name}[Time], ${at_name}: ${fk_attributes.head.attribDataType}) {" +
        s"\n\t\t\t( s1.reachable() && s2.reachable() && " +
        s"\n\t\t\t  s1.compatible(s2) &&" +
        s"\n\t\t\t  s1.refIntegrityHolds(${at_name}) && s2.refIntegrityHolds(${at_name}) " +
        s"\n\t\t\t) =>: {" +
        s"\n\t\t\t\ts1.merge(s2).refIntegrityHolds(${at_name})" +
        s"\n\t\t\t}" +
        s"\n\t\t}" +
        s"\n\t}"
    )

    //MERGE_ASSOCIATIVE
    classContent.append(
      s"\n\n\tproof mergeIsAssociative[Time] {" +
        s"\n\t\tforall(s1: ${fk_system_name}[Time], s2: ${fk_system_name}[Time], s3: ${fk_system_name}[Time]) {" +
        s"\n\t\t\t(s1.reachable() && s2.reachable() && s3.reachable() &&" +
        s"\n\t\t\t  s1.compatible(s2) && s1.compatible(s3) && s2.compatible(s3)" +
        s"\n\t\t\t) =>: {" +
        s"\n\t\t\t\tval aux = s1.merge(s2).merge(s3)" +
        s"\n\t\t\t\taux.equals(s1.merge(s2.merge(s3)))" +
        s"\n\t\t\t\taux.reachable()" +
        s"\n\t\t\t\t}" +
        s"\n\t\t}" +
        s"\n\t}"
    )

    // OBJECT END
    classContent.append("}")

  }


  /**
   * ///////////////////////////////////////////////////////
   * ///////////////--------- TESTS ---------///////////////
   * ///////////////////////////////////////////////////////
   */
  private def generate_Tests_ClassCode(): StringBuilder = {
    println("generating file code at generate_Tests_ClassCode() at CreateTable class")

    val classContent = new StringBuilder

    // PACKAGE
    classContent.append(s"package ${systemTablesFolderName}")

    // IMPORTS
    classContent.append(s"\n\nimport be.vub.kdeporre.crdtproofs.Prover" +
      s"\nimport org.scalatest.FlatSpec")

    // CLASS HEADER
    classContent.append(s"\n\nclass ${tableName}Tests extends FlatSpec with Prover { ")

    // Helper methods to generate comment
    def generateTestFunctionsComments(comment_str: String) = {
      classContent.append(
        s"\n\n\t////////////////////////////////////////////////////////////" +
          s"\n\t// ${comment_str} " +
          s"\n\t////////////////////////////////////////////////////////////")
    }

    // Helper methods to generate given proofs for a given Element or Table name
    def generateTestFunction(tests: mutable.LinkedHashMap[String, String], elemNameToTest: String): Unit = {
      tests.foreach { case (key, value) =>
        classContent.append(
          s"""\n\n\t\"${elemNameToTest}\" should \"${key}\" in {""" +
            s"""\n\t\tval p = (\"${elemNameToTest}\", \"${value}\") """ +
            s"""\n\t\tprove(p)""" +
            s"""\n\t\tp""" +
            s"""\n\t}"""
        )
      }
    }

    // GENERAL TESTS OF ELEMENT - CvRDTProof
    generateTestFunctionsComments(s"GENERAL TESTS OF ELEMENT:  ${tableName}  - specified in CvRDTProof trait")
    var tests = mutable.LinkedHashMap(
      "be a CvRDT" -> "is_a_CvRDT", // WORKS, but table must have more than 1 arg???
      "compatible commutes" -> "compatibleCommutes", // WORKS
      "compare correct" -> "compareCorrect") // WORKS
    generateTestFunction(tests, tableName)

    // TESTS OF ELEMENT FOR UPDATABLE ATTRIBUTES - object TableName extends CvRDTProof
    generateTestFunctionsComments(s"TESTS FOR UPDATABLE ATTRIBUTES OF ELEMENT - specified in object TableName extends CvRDTProof")
    tests = mutable.LinkedHashMap() //updateYear for each concurrently updatable attribute
    attributesList.foreach( //        WORKS
      at => if (at.allowConcurrentUpdates)
        tests += s"update${at.attribName.capitalize} works" -> s"${tableName}_update${at.attribName.capitalize}_works"
    )
    generateTestFunction(tests, tableName)

    // GENERAL TESTS OF elem TABLE
    generateTestFunctionsComments(s"GENERAL TESTS OF TABLE:  ${tableName}sTable - specified in CvRDTProof1 trait")
    tests = mutable.LinkedHashMap(
      "be commutative" -> "mergeCommutative", //WORKS
      "be idempotent" -> "mergeIdempotent", //REBENTA???
      "be associative" -> "mergeAssociative", //REBENTA???
      "be associative2" -> "mergeAssociative2",
      "compatible commutes" -> "compatibleCommutes"
    )
    generateTestFunction(tests, s"${tableName}sTable")

    // FK REFERENTIAL TESTS
    if (fk_attributes.nonEmpty) {
      generateTestFunctionsComments(s"FK TESTS    ")
      tests = mutable.LinkedHashMap(
        "maintain referential integrity generic test" -> "genericReferentialIntegrity", //REBENTA
        "be commutative" -> "mergeCommutative", // WORKS
        "be idempotent" -> "mergeIdempotent", // REBENTA
        "be associative" -> "mergeAssociative", // REBENTA
        "be associative (with assumptions)" -> "mergeIsAssociative", // REBENTA
        "be associative2" -> "mergeAssociative2", // REBENTA
        "compatible commutes" -> "compatibleCommutes" // WORKS
      )
      generateTestFunction(tests, fk_system_name)

    }

    //CLASS CLOSE
    classContent.append(s"\n\n}")

  }




  override def toString: String = {
    s"Table: $tableName - $update_policy: ${attributesList.map("\n\t\t\t\t" + _.toString).mkString("; ")}"
  }

}
