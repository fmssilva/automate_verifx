package createTable.codeGenerators

import createTable.Table

import scala.collection.mutable

/**
 * object to generate the code of the Class with the Proofs for each Element, createTable.Table, and FK system if there are FK
 */
object Proofs_Class {

  // Global Variables
  private var classContent: StringBuilder = _
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
    this.classContent = new StringBuilder
    this.table = table
    this.tableNames = table.tableNames


    // PACKAGE
    classContent.append(
      s"package ${Table.SYSTEM_PROOFS_FOLDER_NAME}"
    )

    // IMPORTS
    classContent.append(
      s"\n\nimport be.vub.kdeporre.crdtproofs.Prover" +
        s"\nimport org.scalatest.FlatSpec" +
        s"\nimport java.time.LocalTime" +
        s"\nimport java.time.format.DateTimeFormatter"
    )

    // CLASS HEADER
    classContent.append(
      s"\n\nclass ${tableNames._1}Proofs extends FlatSpec with Prover { "
    )

    // ELEMENT PROOFS
    generateProofFunctionsComments(s"ELEMENT PROOFS  - specified in CvRDTProof trait")
    var proofs = mutable.LinkedHashMap(
      "be a CvRDT (element)" -> ("is_a_CvRDT", "WORKS  -   20-30 seconds"),
      "compatible commutes (element)" -> ("compatibleCommutes", "WORKS  -   20-30 seconds"),
      "compare correct (element)" -> ("compareCorrect", "WORKS  -   20-30 seconds")
    )
    generateProofFunction(proofs, tableNames._1)

    // ELEMENT - UPDATABLE ATTRIBUTES PROOFS
    generateProofFunctionsComments(s"ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof")
    proofs = mutable.LinkedHashMap()
    table.attributesList.foreach(
      at => if (at.allowConcurrentUpdates)
        proofs += s"update${at.attribName.capitalize} works (element)" -> (s"${tableNames._1}_update${at.attribName.capitalize}_works", "WORKS  -   20-30 seconds")
    )
    generateProofFunction(proofs, tableNames._1)

    // TABLE - GENERAL PROOFS
    generateProofFunctionsComments(s"TABLE PROOFS  - specified in CvRDTProof1 trait")
    proofs = mutable.LinkedHashMap(
      "be commutative (table)" -> ("mergeCommutative", "WORKS  -   20-30 seconds"),
      "be idempotent (table)" -> ("mergeIdempotent", " !!!   ABORTED  in 20-30 seconds!!!"),
      "be associative (table)" -> ("mergeAssociative", " !!!   DON´T TERMINATE (WAITED 10 MINUTES)  !!!"),
      "be associative2 (table)" -> ("mergeAssociative2", "WORKS  -   30-50 seconds - with complex cases don't terminate"),
      "compatible commutes (table)" -> ("compatibleCommutes", "WORKS   -   20-40 seconds")
    )
    generateProofFunction(proofs, s"${tableNames._2}")

    // FK REFERENTIAL PROOFS
    generateProofFunctionsComments(s"FK REFERENTIAL PROOFS    ")
    if (table.fk_attributes.nonEmpty) {
      proofs = mutable.LinkedHashMap(
        "maintain referential integrity generic proof (fk_system)" -> ("genericReferentialIntegrity", "   !!!   DONT TERMINATE   !!!"),
        "be commutative (fk_system)" -> ("mergeCommutative", "WORKS   -   40 sec - 1:40 min"),
        "be idempotent (fk_system)" -> ("mergeIdempotent", "   Albs e SongView ABORTED in 1 - 2 minutes;  Song - WORKS - 40 seconds"),
        "be associative (fk_system)" -> ("mergeAssociative", "   !!!   DONT TERMINATE   !!!"),
        "be associative (with assumptions) (fk_system)" -> ("mergeIsAssociative", "WORKS   -   1:30 - 3 minutes"),
        "be associative2 (fk_system)" -> ("mergeAssociative2", "WORKS  /   DON'T TERMINATE IN 4 REF TABLES "),
        "compatible commutes (fk_system)" -> ("compatibleCommutes", "Albs/Songs (WORKS: 1-4 minutes); SongView - Dont terminate after 30 minutes")
      )
      generateProofFunction(proofs, tableNames._3)
    }

    // PRINT TIME METHODS
    generateProofFunctionsComments(s"HELPER METHODS")
    gen_printStartingTime()
    gen_PrintProofTime()

    //CLASS CLOSE
    classContent.append(s"\n\n}")

    classContent

  }

  ////////////////////////////////////////////////////////////////////
  ////////////---------- HELPER METHODS ---------- /////////////////
  ////////////////////////////////////////////////////////////////////


  /**
   * Generate given proofs for a given Element or Table name
   *
   * @param proofs          - map of proofs description and title
   * @param elemNameToProve - name of the element or table to prove upon
   */
  private def generateProofFunction(proofs: mutable.LinkedHashMap[String, (String, String)], elemNameToProve: String): Unit = {
    proofs.foreach { case (key, (proof, state_of_this_proof)) =>
      classContent.append(
        s"""\n\n\t// $state_of_this_proof""" +
          s"""\n\t\"$elemNameToProve\" should \"$key\" in {""" +
          s"""\n\t\tval startTime = printStartingTime(\"$proof\")""" +
          s"""\n\t\tval p = (\"$elemNameToProve\", \"$proof\") """ +
          s"""\n\t\tprove(p)""" +
          s"""\n\t\tprintProofTime(startTime, System.nanoTime(), \"$proof\")""" +
          s"""\n\t\tp""" +
          s"""\n\t}"""
      )
    }
  }


  /**
   * Generate main comments
   *
   * @param comment_str - string to add to comment
   */
  private def generateProofFunctionsComments(comment_str: String): Unit = {
    classContent.append(
      s"\n\n\n\n\t////////////////////////////////////////////////////////////" +
        s"\n\t// $comment_str " +
        s"\n\t////////////////////////////////////////////////////////////")
  }

  private def gen_printStartingTime(): Unit = {
    classContent.append(
      s"\n\n\t// print starting time" +
        s"""\n\tprivate def printStartingTime(proof: String): Long = { """ +
        s"""\n\t\tval currentTime = LocalTime.now() """ +
        s"""\n\t\tval formatter = DateTimeFormatter.ofPattern(\"HH:mm:ss\") """ +
        s"""\n\t\tval formattedTime = currentTime.format(formatter) """ +
        s"""\n\t\tprintln(\"Test  ( \" + proof + \" ) started at \" + formattedTime) """ +
        s"""\n\t\tSystem.nanoTime() """ +
        s"""\n\t} """
    )
  }


  /**
   * Generate the code for the printProofTime method
   */
  private def gen_PrintProofTime(): Unit = {
    classContent.append(
      s"\n\n\t// print proof time" +
        s"""\n\tprivate def printProofTime(startTime: Long, endTime: Long, proof: String):Unit = { """ +
        s"""\n\t\tval durationInSeconds = (endTime - startTime) / 1e9d // convert nanoseconds to seconds """ +
        s"""\n\t\tval minutes = (durationInSeconds / 60).toInt """ +
        s"""\n\t\tval seconds = (durationInSeconds % 60).toInt """ +
        s"""\n\t\tprintln(\"Proof \" + proof + \" took \" +  minutes + \" minutes : \" + seconds + \" seconds.\") """ +
        s"""\n\t\t} """
    )
  }

}
