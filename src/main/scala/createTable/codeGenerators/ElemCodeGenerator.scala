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
  // (Element, ElementsTable, Elements_FK_System)
  private var tableNames: (String, String, String) = _
  private var attributesList: mutable.Seq[TabAttribute] = _
  private var updatableAttributes: mutable.Seq[TabAttribute] = _

  def generate_Elem_ClassCode(table: Table, cmdTokens: List[(Int, Array[String])]): StringBuilder = {
    println("generating file code at generate_Elem_ClassCode() at CreateTable class")

    // Global Variables
    this.classContent = new StringBuilder
    this.tableNames = table.tableNames
    this.attributesList = table.attributesList
    this.updatableAttributes = attributesList.filter(at => at.allowConcurrentUpdates)

    // Generate Imports, Comments and Class Header
    gen_imports()
    gen_ClassComments(cmdTokens)
    gen_ClassHeader

    //Implement Methods DECLARED in CvRDT trait
    classContent.append("\n\n\n\n  /*\n   * IMPLEMENTATION OF METHODS DECLARED IN CVRDT TRAIT\n   */")
    gen_Merge
    gen_Compare

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
   * Generate CLASS COMMENTS
   *
   * @param cmdTokens - tokens of this CREATE TABLE command
   */
  private def gen_ClassComments(cmdTokens: List[(Int, Array[String])]): Unit = {
    classContent.append(
      s"\n\n/**\n * Class representing the element(row):::  ${tableNames._1.toUpperCase()} " +
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
  private def gen_ClassHeader = {
    classContent.append(
      s"\nclass ${tableNames._1}(" +
        attributesList.map(
          at => s"${at.attribName}: ${if (at.allowConcurrentUpdates) at.attribDataType_CRDT else at.attribDataType}"
        ).mkString(", ") +
        s") extends CvRDT[${tableNames._1}] {"
    )
  }

  /**
   * Generate MERGE
   */
  private def gen_Merge = {
    classContent.append(
      s"\n\n\t//merge this ${tableNames._1} with that ${tableNames._1}" +
        s"\n\tdef merge(that: ${tableNames._1}) = " +
        gen_comment_to_explain_args() +
        //code to merge
        s"\n\t\tnew ${tableNames._1}(" +
        attributesList.map(
          at =>
            if (at.allowConcurrentUpdates) s"this.${at.attribName}.merge(that.${at.attribName})"
            else s"this.${at.attribName}"
        ).mkString(", ") +
        ")"
    )
  }


  /**
   * Generate COMPARE
   */
  private def gen_Compare = {
    classContent.append(
      s"\n\n\t//compare this ${tableNames._1} with that ${tableNames._1}" +
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
   * Generate REACHABLE
   * if we have no CHECK, we don't need to override. it's always TRUE
   *
   * @param attributesWithChecks - list of attributes with CHECK conditions
   */
  private def gen_Reachable(attributesWithChecks: mutable.Seq[TabAttribute]): Unit = {
    classContent.append(
      s"\n\n\t//${tableNames._1} is reachable given the CHECK conditions of its attributes?" +
        s"\n\toverride def reachable() = { " +
        attributesWithChecks.map { at =>
          "\n\t\t" + at.attribInvariant.check_options.get.map {
            case attrib if attributesList.exists(_.attribName.toUpperCase().equals(attrib)) => s" this.${attrib.toLowerCase()}.value"
            case "AND" => " &&"
            case "OR" => " ||"
            case other => s" $other"
            //TODO: do other cases: IN, BETWEEN ... e parenthesis tree...
          }.mkString
        }.mkString(" && ") +
        "\n\t}"
    )
  }

  /**
   * Generate COMPATIBLE
   */
  private def gen_Compatible(): Unit = {
    classContent.append(
      s"\n\n\t//this ${tableNames._1} is compatible with that ${tableNames._1}?" +
        s"\n\toverride def compatible(that: ${tableNames._1}) = " +
        gen_comment_to_explain_args() + "\n\t\t" +
        attributesList.map(
          at =>
            if (at.allowConcurrentUpdates)
              s"this.${at.attribName}.compatible(that.${at.attribName})"
            else
              s"this.${at.attribName} == that.${at.attribName}"
        ).mkString(" &&\n\t\t")
    )
  }

  /**
   * Implement Update methods for concurrently updatable attributes to be used by the prover, useful in CmRDT - Operations
   */
  private def gen_UpdateAttributes(): Unit = {
    //helper method
    def code_for_newElement(atName: String) = {
      s"\n\t\t\tnew ${tableNames._1}(" +
        attributesList.map(
          at =>
            if (at.attribName.equals(atName))
              s"this.${at.attribName}.assign(new${atName.capitalize}, stamp${atName.capitalize})"
            else
              s"this.${at.attribName}"
        ).mkString(", ") + ")"
    }

    // updateAttribute() generation code
    updatableAttributes.map {
      at =>
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

  //generate comment to explain each arg of a method
  private def gen_comment_to_explain_args(): String = {
    s"\n\t\t//args: " +
      attributesList.map(
        at =>
          if (at.attribInvariant.isPrimaryKey) s"${at.attribName} (PK)"
          else if (at.attribInvariant.fk_options.isDefined) s"${at.attribName} (FK)"
          else s"${at.attribName} (attrib)"
      ).mkString("; ")
  }

  /**
   * Generate extra proofs for the attributes that are updatable
   */
  private def gen_ObjectWithExtraProofs(): Unit = {
    //OBJECT HEADER
    classContent.append(
      s"\n\nobject ${tableNames._1} extends CvRDTProof[${tableNames._1}] {")

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
            // imply => {
            s"\n\t\t\t\t //simulate the update of the element in 2 replicas, creating elem 1 and 2, and then merging them in elem12" +
            s"\n\t\t\t\t val elem1 = elem.update${atName.capitalize}(${atName}1, c1)" +
            s"\n\t\t\t\t val elem2 = elem.update${atName.capitalize}(${atName}2, c2)" +
            s"\n\t\t\t\t val elem12 = elem1.merge(elem2)" +
            s"\n\t\t\t\t //check if the update in elem 1 and 2 kept the correct values" +
            attributesList.map(at_in =>
              if (at_in.attribName.equals(atName))
                s"\n\t\t\t\t elem1.$atName.value == ${atName}1 && elem2.$atName.value == ${atName}2"
              else
                s"\n\t\t\t\t elem1.${at_in.attribName} == elem.${at_in.attribName} && elem2.${at_in.attribName} == elem.${at_in.attribName}"
            ).mkString(" &&") + " &&" +
            s"\n\t\t\t\t //check if the merged values are correct and according to the chosen update-policy " +
            attributesList.map(at_in =>
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
