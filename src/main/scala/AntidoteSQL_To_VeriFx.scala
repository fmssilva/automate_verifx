import java.io.{FileOutputStream, ObjectOutputStream}
import java.nio.file.{Files, Paths}
import scala.collection.mutable
import scala.io.Source

/**
 * Convert Antidote SQL Commands to VeriFx
 *
 * @Pre:
 *    1. The correct syntax of the commands was checked previously,
 *       e.g. the correct open and closing of parenthesis
 *       We only check the words for each command
 *       2. Each instruction is in a different line,
 *       so the first word of the instruction is always the token(0)
 *       and e.g. the closing parenthesis of a CREATE TABLE command is in a new line after the last attribute
 *       3.  All user names are correct: don't use key words...
 *
 *       EXAMPLE OF ANTIDOTE SQL COMMAND TO PROCCESS:
 *       CREATE UPDATE-WINS TABLE Albums (
 *       title VARCHAR PRIMARY KEY,
 *       artist VARCHAR,
 *       year LWW INT
 *       )
 */
object AntidoteSQL_To_VeriFx {
  /**
   * Files and Paths Constants
   */
  private val ANTIDOTE_SQL_DATA_FOLDER = Paths.get("AntidoteSQL_Data")
  private val INPUT_COMMANDS_FILE_NAME = "input.txt"
  private val SYS_TABLES_MAP_FILE_NAME = "sysTablesMap.ser"
  private val SYS_TABLES_FILE_PATH = Paths.get(s"${ANTIDOTE_SQL_DATA_FOLDER}/${SYS_TABLES_MAP_FILE_NAME}")


  /**
   * main method
   *
   * @param args - we might add some args, for example receiving the path for the input file with the Antidote SQL commands
   */
  def main(args: Array[String]): Unit = {
    try {
      // get the AntidoteSQL commands, tokenized by lines and keeping the lines numbers for error report
      val cmdTokens = getCommandsTokens() // List[(Line_Of_Code: Int, tokens: Array[String])]
      //print for debug
      cmdTokens.foreach {
        case (lineNumber, tokens) =>
          println(s"Line $lineNumber: ${tokens.mkString("[", ", ", "]")}")
      }

      // Load existing map of tables in the system
      val sysTablesMap: mutable.Map[String, Table] = loadSysTablesMap()
      printSysTablesMap(sysTablesMap)

      // process commands
      processCommands(cmdTokens, sysTablesMap)
      printSysTablesMap(sysTablesMap)

      // Save the updated map of tables in the system
      saveSysTablesMap(sysTablesMap)
    } catch {
      case e: IllegalArgumentException =>
        println(s"\nError: ${e.getMessage}")
    }
  }


  private def printSysTablesMap(sysTablesMap: mutable.Map[String, Table]): Unit = {
    //print for debug
    println("Initial Map of Tables in the System:")
    for ((key, value) <- sysTablesMap)
      println(s"k-${key}: ${value}")
  }

  /**
   *
   * @param cmdTokens
   */
  private def processCommands(cmdTokens: List[(Int, Array[String])], sysTablesMap: mutable.Map[String, Table]): Unit = {
    var line = 0
    while (line < cmdTokens.length) {
      println("\nNEXT COMMAND:")
      val (l, tokens) = cmdTokens(line)
      tokens.headOption match {
        case Some("CREATE") =>
          if (tokens.lift(2).contains("TABLE")) {
            // se update policy No concurrency poder ser não se escrever nada temos que rever aqui e no create table
            //todo: ver no tablesMap se ja existe
            val newTable = new Table(cmdTokens, line, sysTablesMap)
            //update next line of tokens to read
            line = newTable.line
            //add table to system tables map
            sysTablesMap.put(newTable.tableName, newTable)
          } else if (tokens.lift(2).contains("INDEX"))
            println("INDEX")
        case Some("UPDATE") =>
          println("Processing UPDATE command")
        case Some("DELETE") =>
          println("Processing DELETE command")
        case _ =>
          println(s"Error: Unrecognized command at line $line")
      }
    }
  }

  private def getCommandsTokens(): List[(Int, Array[String])] = {
    // Input file
    val fileName = ANTIDOTE_SQL_DATA_FOLDER.resolve(INPUT_COMMANDS_FILE_NAME)
    val fileSource = Source.fromFile(fileName.toFile)
    try {
      fileSource.getLines().toList.zipWithIndex
        .map {
          case (line, lineNumber) =>
            val tokens = tokenizeLine(line)
            (lineNumber, tokens)
        }.filter { //delete the empty lines
          case (_, tokens) => tokens.nonEmpty
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
    val demarcatedSymbols = symbols.foldLeft(line) { (result, symbol) =>
      result.replaceAll(s"""\\Q$symbol\\E""", s" $symbol ")
    }

    // Use simple or multiple spaces or tabs to divide the line into tokens
    val regex = """('[^']*'|"[^"]*"|\S+)""".r
    val init_tokens = regex.findAllIn(demarcatedSymbols)

    // Consider words between " " or ' ' as a simple token
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
      //always delete the orignal (for testing)
      Files.delete(SYS_TABLES_FILE_PATH)
      mutable.Map[String, Table]()
      //after testing uncomment this to load the original if exists
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
}
