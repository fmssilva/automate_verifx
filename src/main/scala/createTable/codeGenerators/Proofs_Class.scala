package createTable.codeGenerators

import createTable.Table

import scala.collection.mutable

/**
 * object to generate the code of the Class with the Proofs for each Element, createTable.Table, and FK system if there are FK
 */
object Proofs_Class {

  // Global Variables
  private var classContent: StringBuilder = _
  //(Element, ElementsTable, Elements_FK_System)
  private var tableNames: (String, String, String) = _

  /**
   * Generate the code of the Class with the Proofs for each Element, Table, and FK_System (if there are FKs)
   *
   * @param table                  - table to generate the code for
   * @return -  the code for the class
   */
  def generate_Proofs_ClassCode(table: Table): StringBuilder = {

    println("generating file code at generate_Proofs_ClassCode() at CreateTable class")

    // Global Variables
    this.classContent = new StringBuilder
    this.tableNames = table.tableNames


    // PACKAGE
    classContent.append(
      s"package ${table.systemTablesFolderName}"
    )

    // IMPORTS
    classContent.append(
      s"\n\nimport be.vub.kdeporre.crdtproofs.Prover" +
        s"\nimport org.scalatest.FlatSpec"
    )

    // CLASS HEADER
    classContent.append(
      s"\n\nclass ${tableNames._1}Proofs extends FlatSpec with Prover { "
    )

    // ELEMENT - GENERAL PROOFS
    var proofs = mutable.LinkedHashMap(
      "be a CvRDT" -> ("is_a_CvRDT", "WORKS"),
      "compatible commutes" -> ("compatibleCommutes", "WORKS"),
      "compare correct" -> ("compareCorrect", "WORKS")
    )
    generateProofFunctionsComments(s"ELEMENT - GENERAL PROOFS  - specified in CvRDTProof trait")
    generateProofFunction(proofs, tableNames._1)

    // ELEMENT - UPDATABLE ATTRIBUTES PROOFS
    proofs = mutable.LinkedHashMap()
    table.attributesList.foreach(
      at => if (at.allowConcurrentUpdates)
        proofs += s"update${at.attribName.capitalize} works" -> (s"${tableNames._1}_update${at.attribName.capitalize}_works", "WORKS")
    )
    generateProofFunctionsComments(s"ELEMENT - UPDATABLE ATTRIBUTES PROOFS  - specified in object TableName extends CvRDTProof")
    generateProofFunction(proofs, tableNames._1)

    // TABLE - GENERAL PROOFS
    proofs = mutable.LinkedHashMap(
      "be commutative" -> ("mergeCommutative", "WORKS"),
      "be idempotent" -> ("mergeIdempotent", " !!!   ABORTED  !!!"),
      "be associative" -> ("mergeAssociative", " !!!   ABORTED  !!!"),
      "be associative2" -> ("mergeAssociative2", "WORKS"),
      "compatible commutes" -> ("compatibleCommutes", "WORKS")
    )
    generateProofFunctionsComments(s"TABLE - GENERAL PROOFS  - specified in CvRDTProof1 trait")
    generateProofFunction(proofs, s"${tableNames._2}")

    // FK REFERENTIAL PROOFS
    if (table.fk_attributes.nonEmpty) {
      proofs = mutable.LinkedHashMap(
        "maintain referential integrity generic proof" -> ("genericReferentialIntegrity", "   !!!   ABORTED   !!!"),
        "be commutative" -> ("mergeCommutative", "WORKS"),
        "be idempotent" -> ("mergeIdempotent", "   !!!   ABORTED   !!!"),
        "be associative" -> ("mergeAssociative", "   !!!   ABORTED   !!!"),
        "be associative (with assumptions)" -> ("mergeIsAssociative", "WORKS"),
        "be associative2" -> ("mergeAssociative2", "WORKS"),
        "compatible commutes" -> ("compatibleCommutes", "WORKS")
      )
      generateProofFunctionsComments(s"FK REFERENTIAL PROOFS    ")
      generateProofFunction(proofs, tableNames._3)
    }

    //CLASS CLOSE
    classContent.append(s"\n\n}")

    classContent

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
   * Helper method to generate given proofs for a given Element or createTable.Table name
   *
   * @param proofs          - map of proofs description and title
   * @param elemNameToProve - name of the element or table to prove upon
   */
  private def generateProofFunction(proofs: mutable.LinkedHashMap[String, (String, String)], elemNameToProve: String): Unit = {
    proofs.foreach { case (key, (value, state)) =>
      classContent.append(
        s"\n\n\t// $state" +
          s"""\n\t\"$elemNameToProve\" should \"$key\" in {""" +
          s"""\n\t\tval p = (\"$elemNameToProve\", \"$value\") """ +
          s"""\n\t\tprove(p)""" +
          s"""\n\t\tp""" +
          s"""\n\t}"""
      )
    }
  }

}
