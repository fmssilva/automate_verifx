package createTable

import antidoteSQL_to_veriFx.System_Constants._
import scala.collection.mutable

/**
 * Class to process the tokens for declaring a new attribute of a table
 *
 * @param lineTokens - line of the antidote SQL command for creating an attribute
 * @param fileLine       - current line of the tokens in the file
 */
@SerialVersionUID(1594622921505253437L)
class TabAttribute(lineTokens: Array[String], fileLine: Int, sysTablesMap: mutable.Map[String, Table]) extends Serializable {

  println("Line " + fileLine + ": " + lineTokens.mkString(", ") + " \t\t» creating attribute at CreateAttribute class ")

  // ATTRIBUTE NAME
  private var idx_of_token = 0
  val attribName: String = lineTokens(idx_of_token).toLowerCase()

  //UPDATE POLICY
  idx_of_token += 1
  private val ATTRIB_MUST_HAVE_NAME_POLICY_DATA = s"Line: $fileLine - Attribute $attribName must have a NAME, UPDATE POLICY (${ATTRIBUTE_UPDATE_POLICIES.mkString(" | ")} ) and DATA TYPE (${ATTRIBUTE_DATA_TYPE_ANTIDOTE.mkString(" | ")} ) "
  val attribPolicy: String = getAttribUpdatePolicy
  val allowConcurrentUpdates: Boolean = attribPolicy != NO_CONCURRENCY

  //DATA TYPE
  idx_of_token += 1
  val (attribDataType, attribDataType_CRDT): (String, String) = getAttribDataType

  //INVARIANTS
  val attribInvariant: AttribInvariants = AttribInvariants(lineTokens, fileLine, sysTablesMap, this)

  println("Created: " + this.toString)



  ////////////////////////////////////////////////////////////////////
  ////////////---------- HELPER METHODS ---------- /////////////////
  ////////////////////////////////////////////////////////////////////

  /**
   * UPDATE POLICY
   */
  private def getAttribUpdatePolicy: String = {
    lineTokens.lift(idx_of_token) match {
      case Some(policy) if ATTRIBUTE_UPDATE_POLICIES.contains(policy) => policy
      case Some(dataType) if ATTRIBUTE_DATA_TYPE_ANTIDOTE.contains(dataType) =>
        idx_of_token -= 1 //adjust idx because there was no token for "No Concurrency"
        NO_CONCURRENCY
      case _ =>
        throw new IllegalArgumentException("at getAttribUpdatePolicy " + ATTRIB_MUST_HAVE_NAME_POLICY_DATA)
    }
  }

  /**
   * DATA TYPE
   */
  private def getAttribDataType: (String, String) = {
    val dataType = lineTokens.lift(idx_of_token) match {
      case Some(dt) if ATTRIBUTE_DATA_TYPE_ANTIDOTE.contains(dt) =>
        ATTRIBUTE_DATA_TYPE_VERIFX(ATTRIBUTE_DATA_TYPE_ANTIDOTE.indexOf(dt))
      case _ => throw new IllegalArgumentException("at getAttribDataType " + ATTRIB_MUST_HAVE_NAME_POLICY_DATA)
    }
    //TODO: do match with all cases
    attribPolicy match {
      case NO_CONCURRENCY => (dataType, "")
      case LWW => (dataType, s"$LWWRegister[$dataType]")
      case _ => throw new Exception("TODO: Implement getAttribDataType() at CreateAttribute.scala")
    }
  }


  /**
   * toString
   */
  override def toString: String = {
    s"attrib: $attribName, $attribPolicy, $attribDataType, Invariants: ${attribInvariant.toString}"
  }


}



