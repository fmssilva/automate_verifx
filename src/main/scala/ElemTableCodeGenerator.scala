
/**
 * object to generate the code for a Table Class (a table of rows of elements)
 */
object ElemTableCodeGenerator {

  def generate_ElemTable_ClassCode(table: Table, systemTablesFolderName: String): StringBuilder = {

    println("generating file code at generate_ElemTable_ClassCode() at CreateTable class")

    val classContent = new StringBuilder
    val tableName = table.tableName
    val update_policy = table.update_policy

    // IMPORTS
    classContent.append(
      s"import $systemTablesFolderName.${tableName.toLowerCase()}s.$tableName" +
        s"\nimport antidote.crdts.lemmas.CvRDTProof1" +
        (update_policy match {
          case "UPDATE-WINS" => s"\nimport antidote.crdts.tables.UWTable"
          //TODO: other cases
        })
    )

    //CLASS HEADER
    classContent.append(
      s"\n\nclass ${tableName}sTable[Time] (before: (Time, Time) => Boolean, " +
        s"\n\t\t\t\telements: Map[String, Tuple[$tableName, MVRegister[Int,Time]]])" +
        (update_policy match {
          case "UPDATE-WINS" => s"\n\t\t\t\textends UWTable[$tableName, Time, ${tableName}sTable[Time]]{ "
          case "DELETE-WINS" => throw new Exception("check args of DWTable trait")
          case "NO_CONCURRENCY" => throw new Exception("What to do with table NO_CONCURRENCY")
        })
    )

    classContent.append("\n\n\n\t//Implement Methods DECLARED in UWTable trait")
    //COPY
    classContent.append(
      s"\n\n\tdef copy(newElements: Map[String, Tuple[$tableName, MVRegister[Int, Time]]]) =" +
        s"\n\t\t    new ${tableName}sTable(this.before, newElements)"
    )

    //MAINTAIN STATE
    classContent.append(s"\n\n\tdef maintainState() = this")

    //END CLASS
    classContent.append(s"\n\n}")

    //OBJECT CLASS
    classContent.append(s"\n\nobject ${tableName}sTable extends CvRDTProof1[${tableName}sTable]")

  }


}
