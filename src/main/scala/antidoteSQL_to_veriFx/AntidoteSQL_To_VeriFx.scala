package antidoteSQL_to_veriFx


import antidoteSQL_to_veriFx.System_Constants._
import antidoteSQL_to_veriFx.System_Utils.createClassFile
import createTable.Table
import createTable.codeGenerators.Hard_Proofs

import java.io.{FileOutputStream, ObjectOutputStream}
import java.nio.file.Files
import scala.collection.mutable
import scala.io.Source


/**
 * Convert Antidote SQL Commands to VeriFx
 *
 * EXAMPLE OF ANTIDOTE SQL COMMAND TO PROCESS:
 * CREATE UPDATE-WINS TABLE Albums (
 * title VARCHAR PRIMARY KEY,
 * artist VARCHAR,
 * year LWW INT
 * )
 */
object AntidoteSQL_To_VeriFx {

  /**
   * main method
   *
   * @param args - we might add some args, for example receiving the path for the input file with the Antidote SQL commands
   */
  def main(args: Array[String]): Unit = {
    try {
      // GET COMMANDS - AntidoteSQL command' tokens, by lines
      val cmdTokens: List[(Int, Array[String])] = getFilteredCommandsTokens
      print_cmdTokens(cmdTokens)

      // LOAD SYSTEM CURRENT TABLES
      val sysTablesMap: mutable.Map[String, Table] = loadSysTablesMap()
      printSysTablesMap(sysTablesMap) //current tables in the system

      // PROCESS COMMANDS
      processAllCommands(cmdTokens, sysTablesMap)
      printSysTablesMap(sysTablesMap) //final tables in the system

      // GENERATE HARDER PROOFS OF DIFFERENT TABLES IN 1 DOCUMENT
      val filePath = s"$SYSTEM_PROOFS_FOLDER_PATH/Hard_Proofs.scala"
      val classContent = Hard_Proofs.gen_hard_proofs(sysTablesMap)
      createClassFile(classContent, filePath)

      // SAVE SYSTEM CURRENT TABLES
      saveSysTablesMap(sysTablesMap)
    } catch {
      case e: IllegalArgumentException =>
        println(s"\nError: ${e.getMessage}")
    }
  }


  ////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////  HELPER METHODS /////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Process all the AntidoteSQL commands given in the input file
   *
   * @param cmdTokens - tokens of all the commands given in the input file
   */
  private def processAllCommands(cmdTokens: List[(Int, Array[String])], sysTablesMap: mutable.Map[String, Table]): Unit = {
    var line = 0
    while (line < cmdTokens.length) {
      println("\nPROCESSING NEXT COMMAND:")
      val (fileLine, tokens) = cmdTokens(line)
      tokens.headOption match {
        case Some("CREATE") =>
          if (tokens.lift(2).contains("TABLE")) {
            // if update policy No concurrency can it not be written? if so we need to check here and in the create table
            //todo: check in tablesMap if already exists
            val newTable = new Table(cmdTokens, line, sysTablesMap)
            line = newTable.line //update next line after processing the create table command
            sysTablesMap.put(newTable.tableNames._1, newTable) //add table to system tables map
          } else if (tokens.lift(2).contains("INDEX"))
            throw new IllegalArgumentException(s"Error: CREATE INDEX command not supported at line $fileLine")
        case Some("UPDATE") =>
          throw new IllegalArgumentException(s"Error: UPDATE command not supported at line $fileLine")
        case Some("DELETE") =>
          throw new IllegalArgumentException(s"Error: DELETE command not supported at line $fileLine")
        case _ =>
          throw new IllegalArgumentException(s"Error: Invalid command at line $fileLine")
      }
    }
  }

  /**
   * Get the tokens of all the commands given in the input file
   *
   * @return a list with number of line and its tokens
   */
  private def getFilteredCommandsTokens: List[(Int, Array[String])] = {
    // Input file
    val fileName = ANTIDOTE_SQL_DATA_FOLDER.resolve(INPUT_COMMANDS_FILE_NAME)
    val fileSource = Source.fromFile(fileName.toFile)
    try {
      var is_a_multiLine_comment = false
      fileSource.getLines().toList //List[String]
        .zipWithIndex //List[String,Int] - add line number to each line for easy reference
        .map { //List[(Int, Array[String])] - tokenize each line
          case (inputLine, lineNumber) => (lineNumber + 1, tokenizeLine(inputLine))
        }.filter {
          case (_, tokens) =>
            if (tokens.isEmpty) // remove empty lines
              false
            else if (tokens.headOption.contains("//")) // remove single line comments
              false
            else if (is_a_multiLine_comment) // remove multi-line comments
              false
            else if (tokens.headOption.contains("/*")) { // initiate multi-line comment
              is_a_multiLine_comment = true
              false
            } else if (tokens.headOption.contains("*/")) { // end multi-line comment
              is_a_multiLine_comment = false
              false
            } else
              true
        }
    } finally {
      fileSource.close()
    }
  }


  /**
   * Tokenizes a line of text, according to Antidote SQL rules and syntax
   *
   * @param line The input line of text to be tokenized.
   * @return An array of tokens extracted from the input line.
   */
  private def tokenizeLine(line: String): Array[String] = {
    // Add spaces before and after punctuation symbols
    val symbols = List("(", ")", ",", ";")
    val demarcatedSymbols = symbols.foldLeft(line) {
      (result, symbol) => result.replaceAll(s"""\\Q$symbol\\E""", s" $symbol ")
    }

    // Use spaces (simple or multiple) or tabs, to divide the line into tokens
    val regex = """('[^']*'|"[^"]*"|\S+)""".r
    val init_tokens = regex.findAllIn(demarcatedSymbols)

    // Consider words between   " "   or   ' '   as a simple token
    val quoted_tokens = init_tokens.map(_.stripPrefix("'").stripSuffix("'").stripPrefix("\"").stripSuffix("\""))

    // Convert the result to an array and make all letters uppercase.
    quoted_tokens.map(_.toUpperCase).toArray
  }


  /**
   * Loads the system tables map from the specified file.
   * If the file doesn't exist, returns an empty mutable map.
   *
   * @return The loaded system tables map.
   */
  private def loadSysTablesMap(): mutable.Map[String, Table] = {
    if (!Files.exists(SYS_TABLES_FILE_PATH)) {
      mutable.Map[String, Table]()
    } else {
      //TODO: make this persistent when implementation is done
      //always delete the original (for testing)
      Files.delete(SYS_TABLES_FILE_PATH)
      mutable.Map[String, Table]()
      //TODO: after testing uncomment this to load the original if exists
      //val inputStream = new ObjectInputStream(new FileInputStream(SYS_TABLES_FILE_PATH.toString))
      //      try {
      //        inputStream.readObject().asInstanceOf[mutable.Map[String, CreateTable]]
      //      } finally {
      //        inputStream.close()
      //      }
    }
  }

  /**
   * Saves the provided system tables map to the specified file.
   *
   * @param fileMap The system tables map to be saved.
   */
  private def saveSysTablesMap(fileMap: mutable.Map[String, Table]): Unit = {
    val outputStream = new ObjectOutputStream(new FileOutputStream(SYS_TABLES_FILE_PATH.toString))
    try {
      outputStream.writeObject(fileMap)
    } finally {
      outputStream.close()
    }
  }


  /**
   * Print the tokens of all the commands given in the input file
   *
   * @param cmdTokens - tokens of all the commands given in the input file
   */
  private def print_cmdTokens(cmdTokens: List[(Int, Array[String])]): Unit = {
    cmdTokens.foreach {
      case (lineNumber, tokens) =>
        println(s"Line $lineNumber: ${tokens.mkString("[", ", ", "]")}")
    }
  }

  /**
   * Print the map of tables in the system
   *
   * @param sysTablesMap - map of tables in the system
   */
  private def printSysTablesMap(sysTablesMap: mutable.Map[String, Table]): Unit = {
    //print for debug
    println("\nInitial Map of Tables in the System:")
    for ((_, value) <- sysTablesMap)
      println(s"$value")
  }


}
