package antidoteSQL_to_veriFx

import java.nio.file.{Path, Paths}
import scala.collection.mutable

/**
 * System Constants, declared as static object to be used across multiple classes and objects
 */
object System_Constants {
  /**
   * Proofs
   */

  // ELEMENT PROOFS - IT IS MISSING THE UPDATABLE ATTRIBUTES PROOFS
  private val elem_be_a_CvRDT = "be a CvRDT (element)" -> ("is_a_CvRDT", "WORKS  -  20-30 seconds")
  private val elem_compatible_commutes = "compatible commutes (element)" -> ("compatibleCommutes", "WORKS  -  20-40 seconds")
  private val elem_compare_correct = "compare correct (element)" -> ("compareCorrect", "WORKS  -   20-30 seconds")
  val updateAttributeProof = "WORKS  -   20-30 seconds" //here just the message because the proof is generated specific for each concurrently updatable attribute
  val element_proofs: mutable.LinkedHashMap[String, (String, String)] = mutable.LinkedHashMap(
    elem_be_a_CvRDT, elem_compatible_commutes, elem_compare_correct
  )

  // TABLE PROOFS
  private val table_merge_commutative = "be merge commutative (table)" -> ("mergeCommutative", "WORKS  -   20-30 seconds")
  private val table_merge_idempotent = "be merge idempotent (table)" -> ("mergeIdempotent", " !!!   ABORTED  in 20-30 seconds!!!")
  private val table_merge_associative = "be merge associative (table)" -> ("mergeAssociative", " !!!   ABORTED   in  25 minutes(song_view)   !!!")
  private val table_merge_associative2 = "be merge associative2 (table)" -> ("mergeAssociative2", "WORKS  -   30 seconds   -  1:30 minutes")
  private val table_compatible_commutes = "compatible commutes (table)" -> ("compatibleCommutes", "WORKS  -   20-30 seconds")
  val table_proofs: mutable.LinkedHashMap[String, (String, String)] = mutable.LinkedHashMap(
    table_merge_commutative, table_merge_idempotent, table_merge_associative, table_merge_associative2, table_compatible_commutes
  )

  // FK REFERENTIAL PROOFS
  private val fk_generic_referential_integrity = "keep referential integrity generic proof (fk_system)" -> ("genericReferentialIntegrity", "   !!!   DONT TERMINATE   !!!")
  private val fk_merge_commutative = "be merge commutative (fk_system)" -> ("mergeCommutative", "WORKS   -   40 sec - 1:40 min -> with generic PK 11 minutes")
  private val fk_merge_idempotent = "be merge idempotent (fk_system)" -> ("mergeIdempotent", "   Albs e SongView ABORTED in 1 - 2 minutes;  Song - WORKS - 40 seconds")
  private val fk_merge_associative = "be merge associative (fk_system)" -> ("mergeAssociative", "ABORTED: SongView in 1hour:30min, Albs in 1hour:20min")
  private val fk_merge_IS_associative = "be merge Is associative (with assumptions) (fk_system)" -> ("mergeIsAssociative", "WORKS   -   1:30 - 3 minutes")
  private val fk_merge_associative2 = "be merge associative2 (fk_system)" -> ("mergeAssociative2", "WORKS  /   In 4 ref tables takes 10 minutes ")
  private val fk_compatible_commutes = "compatible commutes (fk_system)" -> ("compatibleCommutes", "WORKS: 1-4 minutes;")
  val fk_proofs: mutable.LinkedHashMap[String, (String, String)] = mutable.LinkedHashMap(
    fk_generic_referential_integrity, fk_merge_commutative, fk_merge_idempotent, fk_merge_associative, fk_merge_IS_associative, fk_merge_associative2, fk_compatible_commutes
  )

  // HARD PROOFS
  val hard_proofs: mutable.LinkedHashMap[String, (String, String)] = mutable.LinkedHashMap(
    // ELEMENT PROOFS
    // TABLE PROOFS
    table_merge_idempotent, table_merge_associative, table_merge_associative2,
    // FK REFERENTIAL PROOFS
    fk_generic_referential_integrity, fk_merge_commutative, fk_merge_idempotent, fk_merge_associative, fk_merge_IS_associative, fk_merge_associative2, fk_compatible_commutes
  )


  /**
   * Antidote SQL Files
   */
  val ANTIDOTE_SQL_DATA_FOLDER: Path = Paths.get("AntidoteSQL_Data")
  val INPUT_COMMANDS_FILE_NAME: String = "input.aql"

  /**
   * Map of Tables in the System
   */
  private val SYS_TABLES_MAP_FILE_NAME: String = "sysTablesMap.ser"
  val SYS_TABLES_FILE_PATH: Path = Paths.get(s"$ANTIDOTE_SQL_DATA_FOLDER/$SYS_TABLES_MAP_FILE_NAME")

  /**
   * VeriFx Tables
   */
  val SYSTEM_TABLES_FOLDER_NAME: String = "generatedSysTables"
  val SYSTEM_TABLES_FOLDER_PATH: String = s"./src/main/verifx/$SYSTEM_TABLES_FOLDER_NAME"

  /**
   * VeriFx Proofs
   */
  val SYSTEM_PROOFS_FOLDER_NAME: String = SYSTEM_TABLES_FOLDER_NAME + "Proofs"
  val SYSTEM_PROOFS_FOLDER_PATH: String = s"./src/test/scala/$SYSTEM_PROOFS_FOLDER_NAME"

  /**
   * Antidote SQL Key Words
   */
  // Table Update Policies
  val TABLE_UPDATE_POLICIES: Array[String] = Array("UPDATE-WINS", "DELETE-WINS", "NO_CONCURRENCY")
  val UPDATE_WINS = "UPDATE-WINS"
  val DELETE_WINS = "DELETE-WINS"
  val NO_CONCURRENCY = "NO_CONCURRENCY"


  // Attribute Update Policies
  val ATTRIBUTE_UPDATE_POLICIES: Array[String] = Array("LWW", "MULTI-VALUE", "ADDITIVE", "' ' == NO_CONCURRENCY")
  val LWW = "LWW"
  val MULTI_VALUE = "MULTI-VALUE"
  val ADDITIVE = "ADDITIVE"


  // Attribute Data Types, in AntidoteSQL and corresponding VeriFx data types
  val ATTRIBUTE_DATA_TYPE_ANTIDOTE: Array[String] = Array("VARCHAR", "BOOLEAN", "INT", "COUNTER_INT")
  val ATTRIBUTE_DATA_TYPE_VERIFX: Array[String] = Array("String", "Boolean", "Int", "Counter_Int")

  // CRDTs for the VeriFx data types
  val LWWRegister = "LWWRegister"
  val MVRegister = "MVRegister"
  val GCounter = "GCounter"


  // Attribute Constraints Key Words
  val PRIMARY = "PRIMARY"
  val FOREIGN = "FOREIGN"
  val KEY = "KEY"
  val REFERENCES = "REFERENCES"
  val ON = "ON"
  val DELETE = "DELETE"
  val CASCADE = "CASCADE"
  val CHECK = "CHECK"


}
