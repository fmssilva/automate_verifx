package createTable.codeGenerators

import createTable.{TabAttribute, Table}
import scala.collection.mutable

/**
 * object to generate the code for an Element Class (a row of the table)
 */
object ElemCodeGenerator {

  // Global Variables
  private val CRDT_CLOCK = "LamportClock" //clock to be used for CRDTs
  private var classContent: StringBuilder = _
  // tableNames: (Element, ElementsTable, Elements_FK_System)
  private var tableNames: (String, String, String) = _
  private var attributesList: mutable.Seq[TabAttribute] = _
  private var updatableAttributes: mutable.Seq[TabAttribute] = _
  private var non_PK_attributes: mutable.Seq[TabAttribute] = _
  private var pk_name: String = _
  private var pk_data_type: String = _

  /**
   * Generate the code for the Element Class
   *
   * @param table     - the table to generate the code for
   * @param cmdTokens - tokens of this CREATE TABLE command
   * @return the code for the Element Class
   */
  def generate_Elem_ClassCode(table: Table, cmdTokens: List[(Int, Array[String])]): StringBuilder = {
    println("generating file code at generate_Elem_ClassCode() at CreateTable class")

    // Global Variables
    this.classContent = new StringBuilder
    this.tableNames = table.tableNames
    this.attributesList = table.attributesList
    this.updatableAttributes = attributesList.filter(at => at.allowConcurrentUpdates)
    this.non_PK_attributes = attributesList.filter(!_.attribInvariant.isPrimaryKey)
    this.pk_name = table.pk_name
    this.pk_data_type = table.pk_data_type

    // Generate Imports, [composite PKs class], Comments and Class Header
    gen_imports()
    if (table.pk_attributes.size > 1)
      gen_composite_PK_Class(table)
    gen_ClassComments(cmdTokens)
    gen_ClassHeader()

    //Implement Methods DECLARED in CvRDT trait
    classContent.append("\n\n\n  /*\n   * IMPLEMENTATION OF METHODS DECLARED IN CVRDT TRAIT\n   */")
    gen_Merge()
    gen_Compare()

    //Override Methods IMPLEMENTED in CvRDT trait
    classContent.append("\n\n\n\n  /*\n   * OVERRIDE METHODS IMPLEMENTED IN CVRDT TRAIT\n   */")
    gen_Compatible()
    val attributesWithChecks = attributesList.filter(at => at.attribInvariant.check_options.isDefined)
    if (attributesWithChecks.nonEmpty)
      gen_Reachable(attributesWithChecks)

    // Generate update method for updatable attributes
    if (updatableAttributes.nonEmpty) {
      classContent.append("\n\n\n\n  /*\n   * IMPLEMENT METHODS FOR CONCURRENTLY UPDATABLE ATTRIBUTES,\n   * to be used by the prover, useful in CmRDT - Operations\n   */")
      gen_UpdateAttributes()
    }

    // class closing
    classContent.append("\n\n}")

    // Generate extra proofs for the update of updatable attributes
    if (updatableAttributes.nonEmpty) {
      classContent.append("\n\n\n\n\n/*\n* Object to implement the proof functions for the updatable attributes\n*/")
      gen_ObjectWithExtraProofs()
    }

    classContent
  }


  ////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////  HELPER METHODS /////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Generate Imports
   */
  private def gen_imports(): Unit = {
    classContent.append(
      s"\nimport antidote.crdts.lemmas.CvRDT" +
        s"\nimport antidote.crdts.lemmas.CvRDTProof" +
        //TODO: confirm more imports for other data types besides "LWWRegister"??
        (if (attributesList.exists(a => a.attribDataType_CRDT.contains("LWWRegister")))
          s"\nimport antidote.crdts.registers.LWWRegister" +
            s"\nimport antidote.crdts.$CRDT_CLOCK"
        else s""
          )
    )
  }

  /**
   * Composite PK Class
   */
  private def gen_composite_PK_Class(table: Table): Unit = {
    //class comments
    classContent.append(
      s"\n\n/**\n * Class representing the composite primary key of the element(row):::  ${tableNames._1.toUpperCase()}" +
        s"\n * this is a class of PKs so No Concurrent Updates allowed for the same values of PKs," +
        s"\n * so no need to extend CvRDT" +
        s"\n */"
    )
    //class header
    classContent.append(
      s"\nclass ${tableNames._1}_PKs(" +
        table.pk_attributes.map(
          pk_at => s"${pk_at.attribName}: ${pk_at.attribDataType}"
        ).mkString(", ") +
        s") { }"
    )
  }


  /**
   * Generate CLASS COMMENTS
   *
   * @param cmdTokens - tokens of this CREATE TABLE command
   */
  private def gen_ClassComments(cmdTokens: List[(Int, Array[String])]): Unit = {
    classContent.append(
      s"\n\n\n\n/**\n * Class representing the element(row):::  ${tableNames._1.toUpperCase()} " +
        s"\n *\n * given by the Antidote SQL command:\n *" +
        cmdTokens.map(
          cmdToken => s"\n *\t\t${cmdToken._2.mkString(" ")}"
        ).mkString +
        s"\n */"
    )
  }


  /**
   * Generate CLASS HEADER
   */
  private def gen_ClassHeader(): Unit = {
    classContent.append(
      s"\nclass ${tableNames._1}(" +
        s"$pk_name: $pk_data_type, " +
        non_PK_attributes.map(
          at => s"${at.attribName}: ${if (at.allowConcurrentUpdates) at.attribDataType_CRDT else at.attribDataType}"
        ).mkString(", ") +
        s") extends CvRDT[${tableNames._1}] {"
    )
  }

  /**
   * Generate MERGE
   */
  private def gen_Merge(): Unit = {
    classContent.append(
      //comment
      s"\n\n\t//merge this ${tableNames._1} with that ${tableNames._1} (only concurrently updatable attributes are merged as a CRDT)" +
        //method
        s"\n\tdef merge(that: ${tableNames._1}) = " +
        s"\n\t\tnew ${tableNames._1}(this.$pk_name, " +
        non_PK_attributes.map(at =>
          if (at.allowConcurrentUpdates) s"this.${at.attribName}.merge(that.${at.attribName})"
          else s"this.${at.attribName}"
        ).mkString(", ") +
        ")"
    )
  }


  /**
   * Generate COMPARE
   */
  private def gen_Compare(): Unit = {
    classContent.append(
      //comment
      s"\n\n\t//compare this ${tableNames._1} with that ${tableNames._1} (only concurrently updatable attributes matter)" +
        //method
        s"\n\tdef compare(that: ${tableNames._1}) = " +
        (if (updatableAttributes.isEmpty)
          s"\n\t\ttrue"
        else
          updatableAttributes.map(
            at => s"\n\t\tthis.${at.attribName}.compare(that.${at.attribName})").mkString(" &&")
          )
    )
  }


  /**
   * Generate COMPATIBLE
   */
  private def gen_Compatible(): Unit = {
    classContent.append(
      //comment
      s"\n\n\t//this ${tableNames._1} is compatible with that ${tableNames._1}?" +
        //method
        s"\n\toverride def compatible(that: ${tableNames._1}) = " +
        s"\n\t\tthis.$pk_name == that.$pk_name && \n\t\t" +
        non_PK_attributes.map(at =>
          if (at.allowConcurrentUpdates) s"this.${at.attribName}.compatible(that.${at.attribName})"
          else s"this.${at.attribName} == that.${at.attribName}"
        ).mkString(" &&\n\t\t")
    )
  }

  /**
   * Generate REACHABLE
   * if we have no CHECK, we don't need to override. it's always TRUE
   *
   * @param attributesWithChecks - list of attributes with CHECK conditions
   */
  private def gen_Reachable(attributesWithChecks: mutable.Seq[TabAttribute]): Unit = {
    classContent.append(
      //comment
      s"\n\n\t//${tableNames._1} is reachable given the CHECK conditions of its attributes?" +
        //method
        s"\n\toverride def reachable() = { " +
        attributesWithChecks.map { at =>
          "\n\t\t" + at.attribInvariant.check_options.get.map {
            //for each token in the check condition:
            case attrib if attributesList.exists(_.attribName.toUpperCase().equals(attrib)) => s" this.${attrib.toLowerCase()}.value"
            case "AND" => " &&"
            case "OR" => " ||"
            case other => s" $other" //what's the same in SQL and veriFX: numbers, <=, >= ...
            //TODO: do other cases: IN, BETWEEN ... e parenthesis tree...
          }.mkString
        }.mkString(" && ") +
        "\n\t}"
    )
  }

  /**
   * Implement Update methods for concurrently updatable attributes to be used by the prover, useful in CmRDT - Operations
   */
  private def gen_UpdateAttributes(): Unit = {
    //helper method
    def code_for_newElement(atName: String) = {
      s"\n\t\t\tnew ${tableNames._1}(this.$pk_name, " +
        non_PK_attributes.map(at =>
          if (at.attribName.equals(atName))
            s"this.${at.attribName}.assign(new${atName.capitalize}, stamp${atName.capitalize})"
          else
            s"this.${at.attribName}"
        ).mkString(", ") + ")"
    }

    // updateAttribute() generation code
    updatableAttributes.map { at =>
      val atName = at.attribName.capitalize
      classContent.append(
        s"\n\n\tdef update$atName(new$atName: ${at.attribDataType.capitalize}, stamp$atName: $CRDT_CLOCK) = { " +
          (if (at.attribInvariant.check_options.isDefined) {
            s"\n\t\tif(" + at.attribInvariant.check_options.get.map {
              case word if word.equals(at.attribName.toUpperCase()) => s" new$atName"
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


  /**
   * Generate extra proofs for the attributes that are updatable
   */
  private def gen_ObjectWithExtraProofs(): Unit = {
    //OBJECT HEADER
    classContent.append(
      s"\n\nobject ${tableNames._1} extends CvRDTProof[${tableNames._1}] {"
    )

    // UPDATE PROOFS
    attributesList.filter(at => at.allowConcurrentUpdates).map {
      at =>
        val atName = at.attribName
        classContent.append(
          s"\n\n\tproof ${tableNames._1}_update${atName.capitalize}_works {" +
            //forall (...)
            s"\n\t\tforall(elem: ${tableNames._1}, ${atName}1: ${at.attribDataType}, ${atName}2: ${at.attribDataType}, c1: $CRDT_CLOCK, c2: $CRDT_CLOCK ) {" +
            // assumptions to start
            s"\n\t\t\t( elem.reachable()  &&  c1.smaller(c2)" +
            (if (at.attribInvariant.check_options.isDefined) {
              s" &&" +
                s"\n\t\t\t  " +
                (for (i <- 1 to 2) yield {
                  at.attribInvariant.check_options.get.map {
                    case word if word.equals(atName.toUpperCase()) => s"$atName$i"
                    case "AND" => "&&"
                    case "OR" => "||"
                    case other => s"$other"
                    //todo other cases like IN, BETWEEN ... parenthesis tree...
                  }.mkString(" ")
                }).mkString(" && ")
            } else ""
              ) + "\n\t\t\t ) =>: {" +
            //simulate the update of the element in 2 replicas...
            s"\n\t\t\t\t //simulate the update of the element in 2 replicas, creating elem 1 and 2, and then merging them in elem12" +
            s"\n\t\t\t\t val elem1 = elem.update${atName.capitalize}(${atName}1, c1)" +
            s"\n\t\t\t\t val elem2 = elem.update${atName.capitalize}(${atName}2, c2)" +
            s"\n\t\t\t\t val elem12 = elem1.merge(elem2)" +
            //check if the update in elem 1 and 2 kept the correct values
            s"\n\t\t\t\t //check if the update in elem 1 and 2 kept the correct values" +
            s"\n\t\t\t\t elem1.$pk_name == elem.$pk_name && elem2.$pk_name == elem.$pk_name &&" +
            non_PK_attributes.map(at_in =>
              if (at_in.attribName.equals(atName))
                s"\n\t\t\t\t elem1.$atName.value == ${atName}1 && elem2.$atName.value == ${atName}2"
              else
                s"\n\t\t\t\t elem1.${at_in.attribName} == elem.${at_in.attribName} && elem2.${at_in.attribName} == elem.${at_in.attribName}"
            ).mkString(" &&") + " &&" +
            //check if the merged values are correct and according to the chosen update-policy
            s"\n\t\t\t\t //check if the merged values are correct and according to the chosen update-policy " +
            s"\n\t\t\t\t elem12.$pk_name == elem.$pk_name &&" +
            non_PK_attributes.map(at_in =>
              if (at_in.attribName.equals(atName)) {
                if (at_in.attribPolicy.equals("LWW") || at_in.attribPolicy.equals("NO_CONCURRENCY"))
                  s"\n\t\t\t\t elem12.$atName.value == ${atName}2"
                else if (at_in.attribPolicy.equals("ADDITIVE"))
                  s"\n\t\t\t\t elem12.$atName.value == elem.$atName + ${atName}1 + ${atName}2"
                else //MULTI-VALUE
                  s"\n\t\t\t\t elem12.$atName.value == ???????" //TODO: ??????
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
