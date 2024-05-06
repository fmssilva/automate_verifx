package antidoteSQL_to_veriFx

import java.io.{File, IOException, PrintWriter}
import java.nio.file.{Files, Paths}
import scala.util.{Failure, Success, Try}

object System_Utils {


  /**
   * Create Directories
   */
  def createDirectory(folderPath: String): Unit = {
    //TODO: do the delete table to clean the packages and table system map...
    if (!Files.exists(Paths.get(folderPath)))
      Files.createDirectories(Paths.get(folderPath))
  }


  /**
   * Create Element Class File
   */
  def createClassFile(classContent: StringBuilder, filePath: String): Unit = {
    var writer: PrintWriter = null
    try {
      writer = new PrintWriter(new File(filePath))
      writer.println(classContent)
    } catch {
      case e: IOException => println(s"Error writing in the file: ${e.getMessage}")
    } finally {
      if (writer != null) {
        Try(writer.close()) match {
          case Success(_) => println(s"Content written to $filePath")
          case Failure(ex) => println(s"Error closing writer: ${ex.getMessage}")
        }
      }
    }
  }
}
