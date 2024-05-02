package createTable.codeGenerators

import createTable.Table

/**
 * object to generate the code for a createTable.Table Class (a table of rows of elements)
 */
object ElemTableCodeGenerator {
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
  def generate_ElemTable_ClassCode(table: Table): StringBuilder = {

    println("generating file code at generate_ElemTable_ClassCode() at CreateTable class")

    // Global Variables
    this.classContent = new StringBuilder
    this.table = table
    this.update_policy = table.update_policy
    this.tableNames = table.tableNames

    // IMPORTS, COMMENTS, CLASS HEADER
    gen_Class_Imports()
    gen_Class_Comments()
    gen_Class_Header()

    //METHODS DECLARED IN UWTable TRAIT
    classContent.append("\n\n\n\t/*\n\t* Implement Methods DECLARED in UWTable trait\n\t*/")
    //COPY
    classContent.append(
      s"\n\tdef copy(newElements: Map[${table.pk_data_type}, Tuple[${tableNames._1}, MVRegister[Int, Time]]]) =" +
        s"\n\t\t    new ${tableNames._2}(this.before, newElements)"
    )
    //MAINTAIN STATE
    classContent.append(s"\n\n\tdef maintainState() = this")


    //END CLASS
    classContent.append(s"\n\n}")

    //OBJECT CLASS
    classContent.append(s"\n\nobject ${tableNames._2} extends CvRDTProof1[${tableNames._2}]")

    classContent
  }

  ////////////////////////////////////////////////////////////////////
  ////////////---------- HELPER METHODS ---------- /////////////////
  ////////////////////////////////////////////////////////////////////

  /**
   * IMPORTS
   */
  private def gen_Class_Imports(): Unit = {
    classContent.append(
      s"\nimport ${Table.SYSTEM_TABLES_FOLDER_NAME}.${tableNames._1.toLowerCase()}s.${tableNames._1}" +
        s"\nimport antidote.crdts.lemmas.CvRDTProof1" +
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
        s"\n *                   where Key is a String == the PK of the Table (TODO: To be generalized for more types and number of PKs)" +
        s"\n *                   and Value is a Tuple with the Element itself," +
        s"\n *                          and a MVRegister to store - the flags for Insert/Update, Touched/Referenced, Delete, as Int values, " +
        s"\n *                                                    - and a Time" +
        s"\n */"
    )
  }

  /**
   * CLASS HEADER
   */
  private def gen_Class_Header(): Unit = {
    classContent.append(
      s"\nclass ${tableNames._2}[Time] (before: (Time, Time) => Boolean,\t\t\t\t\t\t\t//function" +
        s"\n\t\t\t\t\t   elements: Map[${table.pk_data_type}, Tuple[${tableNames._1}, MVRegister[Int,Time]]]\t//row" +
        s"\n\t\t\t\t\t  ) extends " +
        (update_policy match {
          case "UPDATE-WINS" => s"UWTable[ ${table.pk_data_type} ,${tableNames._1}, Time, ${tableNames._2}[Time]]{ "
          case "DELETE-WINS" => throw new Exception("check args of DWTable trait")
          case "NO_CONCURRENCY" => throw new Exception("What to do with table NO_CONCURRENCY")
        })
    )
  }
}
