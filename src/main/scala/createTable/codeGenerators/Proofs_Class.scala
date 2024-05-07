package createTable.codeGenerators

import antidoteSQL_to_veriFx.System_Constants._
import createTable.Table

import scala.collection.mutable

/**
 * object to generate the code of the Class with the Proofs for each Element, createTable.Table, and FK system if there are FK
 */
object Proofs_Class {

  // Global Variables
  private var table: Table = _
  //(Element, ElementsTable, Elements_FK_System)
  private var tableNames: (String, String, String) = _

  /**
   * Generate the code of the Class with the Proofs for each Element, Table, and FK_System (if there are FKs)
   *
   * @param table - table to generate the code for
   * @return -  the code for the class
   */
  def generate_Proofs_ClassCode(table: Table): StringBuilder = {

    println("generating file code at generate_Proofs_ClassCode() at CreateTable class")

    // Global Variables
    val classContent = new StringBuilder
    this.table = table
    this.tableNames = table.tableNames


    // Generate package, imports and class header
    gen_package_name(classContent)
    gen_imports(classContent)
    gen_Class_Header(classContent, tableNames._1 + "Proofs")

    // ELEMENT PROOFS
    generateProofFunctionsComments(s"ELEMENT PROOFS  - specified in CvRDTProof trait", classContent)
    generateProofFunction(element_proofs, tableNames._1, classContent)

    // ELEMENT - UPDATABLE ATTRIBUTES PROOFS
    generateProofFunctionsComments(s"ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof", classContent)
    val proofs = mutable.LinkedHashMap[String, (String, String)]()
    table.attributesList.filter(_.allowConcurrentUpdates).map(
      at => proofs += s"update${at.attribName.capitalize} works (element)" -> (s"${tableNames._1}_update${at.attribName.capitalize}_works", updateAttributeProof)
    )
    generateProofFunction(proofs, tableNames._1, classContent)


    // TABLE - GENERAL PROOFS
    generateProofFunctionsComments(s"TABLE PROOFS  - specified in CvRDTProof1 trait", classContent)
    generateProofFunction(table_proofs, s"${tableNames._2}", classContent)

    // FK REFERENTIAL PROOFS
    generateProofFunctionsComments(s"FK REFERENTIAL PROOFS    ", classContent)
    if (table.fk_attributes.nonEmpty) {
      classContent.append(s"\n\n\t//Convergence Proofs - with generic PK for faster access and proof")
      generateProofFunction(fk_proofs_genPK, tableNames._3 + GENERIC_PK_FILE_NAME, classContent)
      classContent.append(s"\n\n\t//Referential Integrity Proofs - with specific PK for referential integrity tests")
      generateProofFunction(fk_proofs_specificPK, tableNames._3, classContent)
    }

    //CLASS CLOSE
    classContent.append(s"\n\n}")

    classContent

  }

  ////////////////////////////////////////////////////////////////////
  ////////////---------- HELPER METHODS ---------- /////////////////
  ////////////////////////////////////////////////////////////////////

  /**
   * Generate PACKAGE NAME
   *
   * @return String with package name
   */
  def gen_package_name(classContent: StringBuilder): StringBuilder = {
    classContent.append(s"package ${SYSTEM_PROOFS_FOLDER_NAME}")
    classContent
  }

  /**
   * Generate IMPORTS
   *
   * @return String with imports
   */
  def gen_imports(classContent: StringBuilder): StringBuilder = {
    classContent.append(
      s"\n\nimport be.vub.kdeporre.crdtproofs.Prover" +
        s"\nimport org.scalatest.FlatSpec" +
        s"\nimport generatedSysTablesProofs.ProverUtils._"
    )
    classContent
  }

  /**
   * CLASS HEADER
   *
   * @return String with class header
   */
  def gen_Class_Header(classContent: StringBuilder, className: String): StringBuilder = {
    classContent.append(s"\n\nclass $className extends FlatSpec with Prover { ")
    classContent
  }

  /**
   * Generate given proofs for a given Element or Table name
   *
   * @param proofs          - map of proofs description and title
   * @param elemNameToProve - name of the element or table to prove upon
   */
  def generateProofFunction(proofs: mutable.LinkedHashMap[String, (String, String)], elemNameToProve: String, classContent: StringBuilder): StringBuilder = {
    proofs.foreach { case (key, (proof, state_of_this_proof)) =>
      classContent.append(
        s"""\n\n\t// $state_of_this_proof""" +
          s"""\n\t\"$elemNameToProve\" should \"$key\" in {""" +
          s"""\n\t\tval startTime = printStartingTime(\"$key\")""" +
          s"""\n\t\tval p = (\"$elemNameToProve\", \"$proof\") """ +
          s"""\n\t\tprove(p)""" +
          s"""\n\t\tprintProofTime(startTime, System.nanoTime(), \"$key\")""" +
          s"""\n\t\tp""" +
          s"""\n\t}"""
      )
    }
    classContent
  }


  /**
   * Generate main comments
   *
   * @param comment_str - string to add to comment
   */
  def generateProofFunctionsComments(comment_str: String, classContent: StringBuilder): StringBuilder = {
    classContent.append(
      s"\n\n\n\n\t////////////////////////////////////////////////////////////" +
        s"\n\t// $comment_str " +
        s"\n\t////////////////////////////////////////////////////////////"
    )
    classContent
  }

}
