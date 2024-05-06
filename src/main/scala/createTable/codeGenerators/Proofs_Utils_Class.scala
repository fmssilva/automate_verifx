package createTable.codeGenerators

import antidoteSQL_to_veriFx.System_Constants._

/**
 * object to generate the code of the Class with the Proofs_Utils for the Proofs classes
 */
object Proofs_Utils_Class {

  // Global Variables
  private var classContent: StringBuilder = _

  /**
   * Generate the code of the Class with the Proofs_Utils for the Proofs classes
   *
   * @return -  the code for the class
   */
  def generate_ProverUtils_ClassCode(): StringBuilder = {

    // Global Variables
    this.classContent = new StringBuilder

    // PACKAGE
    classContent.append(
      s"package ${SYSTEM_PROOFS_FOLDER_NAME}"
    )

    // IMPORTS
    classContent.append(
      s"\n\nimport java.time.LocalTime" +
        s"\nimport java.time.format.DateTimeFormatter"
    )

    // CLASS COMMENT
    classContent.append(
      s"""\n\n/**""" +
        s"""\n * class with common methods to be used in the Proofs classes""" +
        s"""\n */"""
    )

    // CLASS HEADER
    classContent.append(
      s"\nobject ProverUtils {"
    )

    // GENERATE UTIL METHODS
    gen_printStartingTime()
    gen_printProofTime()

    // END OF CLASS
    classContent.append(
      s"\n\n}"
    )

  }

  ////////////////////////////////////////////////////////////////////
  ////////////---------- HELPER METHODS ---------- /////////////////
  ////////////////////////////////////////////////////////////////////

  /**
   * Generate the code of the method to print the starting time of the proof
   */
  private def gen_printStartingTime(): Unit = {
    classContent.append(
      //comment
      s"""\n\n\t/*\n\t * print starting time\n\t */""" +
        //method
        s"""\n\tdef printStartingTime(proof: String): Long = {""" +
        s"""\n\t\tval currentTime = LocalTime.now()""" +
        s"""\n\t\tval formatter = DateTimeFormatter.ofPattern(\"HH:mm:ss\")""" +
        s"""\n\t\tval formattedTime = currentTime.format(formatter)""" +
        s"""\n\t\tprintln(\"Test  ( \" + proof + \" ) started at \" + formattedTime)""" +
        s"""\n\t\tSystem.nanoTime()""" +
        s"""\n\t}"""
    )
  }

  /**
   * Generate the code of the method to print the proof time
   */
  private def gen_printProofTime(): Unit = {
    // METHOD - Print Proof Time
    classContent.append(
      //comment
      s"""\n\n\t/*\n\t * print proof time""" +
        s"""\n\t * @param startTime - start time of the proof""" +
        s"""\n\t * @param endTime - end time of the proof""" +
        s"""\n\t * @param proof - name of the proof""" +
        s"""\n\t */""" +
        //method
        s"""\n\tdef printProofTime(startTime: Long, endTime: Long, proof: String):Unit = {""" +
        s"""\n\t\tval durationInSeconds = (endTime - startTime) / 1e9d // convert nanoseconds to seconds""" +
        s"""\n\t\tval minutes = (durationInSeconds / 60).toInt""" +
        s"""\n\t\tval seconds = (durationInSeconds % 60).toInt""" +
        s"""\n\t\tprintln(\"Proof \" + proof + \" took \" +  minutes + \" minutes : \" + seconds + \" seconds.\")""" +
        s"""\n\t}"""
    )
  }

}



