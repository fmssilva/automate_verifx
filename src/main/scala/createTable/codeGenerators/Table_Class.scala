package createTable.codeGenerators

import antidoteSQL_to_veriFx.System_Constants._
import createTable.Table

/**
 * object to generate the code for a createTable.Table Class (a table of rows of elements)
 */
object Table_Class {
  // Global Variables
  private var classContent: StringBuilder = _
  private var table: Table = _
  private var update_policy: String = _
  //(Element, ElementsTable, Elements_FK_System)
  private var tableNames: (String, String, String) = _


  /**
   * Generate the code for a Table Class (a table of rows of elements)
   *
   * @param table - table to generate the code for
   * @return - the code for the class
   */
  def generate_ElemTable_ClassCode(table: Table, pkIsGenericType: Boolean): StringBuilder = {

    println("generating file code at generate_ElemTable_ClassCode() at CreateTable class")

    // Global Variables
    this.classContent = new StringBuilder
    this.table = table
    this.update_policy = table.update_policy
    this.tableNames = table.tableNames

    // IMPORTS, COMMENTS, CLASS HEADER
    gen_Class_Imports(pkIsGenericType)
    gen_Class_Comments()
    gen_Class_Header(pkIsGenericType)

    //METHODS DECLARED IN UWTable TRAIT
    classContent.append("\n\n\n\t/*\n\t* Implement Methods DECLARED in UWTable trait\n\t*/")
    //COPY
    classContent.append(
      s"\n\tdef copy(newElements: Map[${get_PK_type_generic_vs_specific(pkIsGenericType)}, Tuple[${tableNames._1}, MVRegister[Int, Time]]]) =" +
        s"\n\t\t    new ${tableNames._2}${if (pkIsGenericType) GENERIC_PK_FILE_NAME else ""}(this.before, newElements)"
    )
    //MAINTAIN STATE
    classContent.append(s"\n\n\tdef maintainState() = this")


    //END CLASS
    classContent.append(s"\n\n}")

    //OBJECT CLASS
    classContent.append(s"\n\nobject ${tableNames._2}${if (pkIsGenericType) GENERIC_PK_FILE_NAME else ""} extends CvRDTProof${get_Proof_import_for_generic_vs_specific_PK(pkIsGenericType)}[${tableNames._2}${if (pkIsGenericType) GENERIC_PK_FILE_NAME else ""}]")

    classContent
  }

  ////////////////////////////////////////////////////////////////////
  ////////////---------- HELPER METHODS ---------- /////////////////
  ////////////////////////////////////////////////////////////////////

  /**
   * IMPORTS
   */
  private def gen_Class_Imports(pkIsGenericType: Boolean): Unit = {
    classContent.append(
      s"\nimport $SYSTEM_TABLES_FOLDER_NAME.${tableNames._1.toLowerCase()}s.${tableNames._1}" +
        s"\nimport antidote.crdts.lemmas.CvRDTProof${get_Proof_import_for_generic_vs_specific_PK(pkIsGenericType)}" +
        (update_policy match {
          case "UPDATE-WINS" => s"\nimport antidote.crdts.tables.UWTable"
          //TODO: other cases
        })
    )
  }


  /**
   * CLASS COMMENT
   */
  private def gen_Class_Comments(): Unit = {
    classContent.append(
      s"\n\n/**" +
        s"\n * CLASS REPRESENTING A TABLE OF ROWS OF ELEMENTS OF TYPE:" +
        s"\n *\t\t- ${tableNames._1.toUpperCase()} -" +
        s"\n *" +
        s"\n * @param before - function to compare two Time values" +
        s"\n * @param elements - Map of elements/rows in the table," +
        s"\n *                   where Key is the PK of the Table" +
        s"\n *                   and Value is a Tuple with the Element itself," +
        s"\n *                          and a MVRegister to store - the flags for Insert/Update, Touched/Referenced, Delete, as Int values, " +
        s"\n *                                                    - and a Time" +
        s"\n */"
    )
  }

  /**
   * CLASS HEADER
   */
  private def gen_Class_Header(pkIsGenericType: Boolean): Unit = {
    classContent.append(
      s"\nclass ${tableNames._2}${if (pkIsGenericType) GENERIC_PK_FILE_NAME else ""}[Time${if (pkIsGenericType) ", " + GENERIC_PK else SPECIFIC_PK}] " +
        s"\n\t\t\t\t\t( before: (Time, Time) => Boolean,\t\t\t\t\t\t//function" +
        s"\n\t\t\t\t\t  elements: Map[${get_PK_type_generic_vs_specific(pkIsGenericType)}, Tuple[${tableNames._1}, MVRegister[Int,Time]]]\t//row" +
        s"\n\t\t\t\t\t  ) extends " +
        (update_policy match {
          case "UPDATE-WINS" => s"UWTable[${if (pkIsGenericType) GENERIC_PK else table.pk_data_type}, ${tableNames._1}, Time, ${tableNames._2}${if (pkIsGenericType) GENERIC_PK_FILE_NAME else ""}[Time${if (pkIsGenericType) ", " + GENERIC_PK else ""}]]{ "
          case "DELETE-WINS" => throw new Exception("check args of DWTable trait")
          case "NO_CONCURRENCY" => throw new Exception("What to do with table NO_CONCURRENCY")
        })
    )
  }

  private def get_PK_type_generic_vs_specific(pkIsGenericType: Boolean): String = {
    if (pkIsGenericType) GENERIC_PK else table.pk_data_type
  }

  private def get_Proof_import_for_generic_vs_specific_PK(pkIsGenericType: Boolean): String = {
    if (pkIsGenericType) "2" else "1"
  }


}
