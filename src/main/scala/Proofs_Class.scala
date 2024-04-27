import scala.collection.mutable

/**
 * object to generate the code of the Class with the Proofs for each Element, Table, and FK system if there are FK
 */
object Proofs_Class {

  // Global Variables
  private var classContent: StringBuilder = _

  def generate_Proofs_ClassCode(table: Table, systemTablesFolderName: String, fk_system_name: String): StringBuilder = {

    println("generating file code at generate_Proofs_ClassCode() at CreateTable class")

    // Global Variables
    classContent = new StringBuilder

    val tableName = table.tableName
    val attributesList = table.attributesList
    val fk_attributes = table.fk_attributes

    // PACKAGE
    classContent.append(s"package $systemTablesFolderName")

    // IMPORTS
    classContent.append(s"\n\nimport be.vub.kdeporre.crdtproofs.Prover" +
      s"\nimport org.scalatest.FlatSpec")

    // CLASS HEADER
    classContent.append(s"\n\nclass ${tableName}Proofs extends FlatSpec with Prover { ")

    // GENERAL PROOFS OF ELEMENT
    generateProofFunctionsComments(s"GENERAL PROOFS OF ELEMENT:  $tableName  - specified in CvRDTProof trait")
    var proofs = mutable.LinkedHashMap(
      "be a CvRDT" -> "is_a_CvRDT", // WORKS
      "compatible commutes" -> "compatibleCommutes", // WORKS
      "compare correct" -> "compareCorrect") // WORKS
    generateProofFunction(proofs, tableName)

    // PROOFS OF ELEMENT FOR UPDATABLE ATTRIBUTES
    generateProofFunctionsComments(s"PROOFS OF ELEMENT FOR UPDATABLE ATTRIBUTES - specified in object TableName extends CvRDTProof")
    proofs = mutable.LinkedHashMap()
    attributesList.foreach(
      at => if (at.allowConcurrentUpdates) //        WORKS
        proofs += s"update${at.attribName.capitalize} works" -> s"${tableName}_update${at.attribName.capitalize}_works"
    )
    generateProofFunction(proofs, tableName)

    // GENERAL PROOFS OF TABLE
    generateProofFunctionsComments(s"GENERAL PROOFS OF TABLE:  ${tableName}sTable - specified in CvRDTProof1 trait")
    proofs = mutable.LinkedHashMap(
      "be commutative" -> "mergeCommutative", //WORKS
      "be idempotent" -> "mergeIdempotent", // JVM TERMINATION???
      "be associative" -> "mergeAssociative", //JVM TERMINATION???
      "be associative2" -> "mergeAssociative2",
      "compatible commutes" -> "compatibleCommutes"
    )
    generateProofFunction(proofs, s"${tableName}sTable")

    // FK REFERENTIAL PROOFS
    if (fk_attributes.nonEmpty) {
      generateProofFunctionsComments(s"FK REFERENTIAL PROOFS    ")
      proofs = mutable.LinkedHashMap(
        "maintain referential integrity generic proof" -> "genericReferentialIntegrity", //JVM TERMINATION
        "be commutative" -> "mergeCommutative", // WORKS
        "be idempotent" -> "mergeIdempotent", // JVM TERMINATION
        "be associative" -> "mergeAssociative", // JVM TERMINATION
        "be associative (with assumptions)" -> "mergeIsAssociative", // JVM TERMINATION
        "be associative2" -> "mergeAssociative2", // JVM TERMINATION
        "compatible commutes" -> "compatibleCommutes" // WORKS
      )
      generateProofFunction(proofs, fk_system_name)
    }

    //CLASS CLOSE
    classContent.append(s"\n\n}")

  }


  /**
   * Helper method to generate comment
   *
   * @param comment_str - string to add to comment
   */
  private def generateProofFunctionsComments(comment_str: String): Unit = {
    classContent.append(
      s"\n\n\t////////////////////////////////////////////////////////////" +
        s"\n\t// $comment_str " +
        s"\n\t////////////////////////////////////////////////////////////")
  }


  /**
   * Helper method to generate given proofs for a given Element or Table name
   *
   * @param proofs          - map of proofs description and title
   * @param elemNameToProve - name of the element or table to prove upon
   */
  private def generateProofFunction(proofs: mutable.LinkedHashMap[String, String], elemNameToProve: String): Unit = {
    proofs.foreach { case (key, value) =>
      classContent.append(
        s"""\n\n\t\"$elemNameToProve\" should \"$key\" in {""" +
          s"""\n\t\tval p = (\"$elemNameToProve\", \"$value\") """ +
          s"""\n\t\tprove(p)""" +
          s"""\n\t\tp""" +
          s"""\n\t}"""
      )
    }
  }

}
