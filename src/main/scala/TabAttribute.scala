import scala.collection.mutable

@SerialVersionUID(1594622921505253437L)
class TabAttribute(lineTokens: Array[String], var line : Int, sysTablesMap : mutable.Map[String, Table]) extends Serializable {

  println("Line " +  line + ": "+ lineTokens.mkString(", ") + " \t\t» creating attribbute at CreateAttribute class ")
  /**
   * Class constants
   */
  private val ATTRIBUTE_UPDATE_POLICIES = Array("LWW", "MULTI-VALUE", "ADDITIVE", "' ' == NO_CONCURRENCY")
  private val ATTRIBUTE_DATA_TYPE_ANTIDOTE = Array("VARCHAR", "BOOLEAN", "INT","COUNTER_INT")
  private val ATTRIBUTE_DATA_TYPE_VERIFX = Array("String", "Boolean", "Int","Counter_Int")


  /**
   *   Attribute fields
   */
  private var idx_of_token = 0
  val attribName: String = lineTokens(idx_of_token).toLowerCase()

  // error msg to be used in the next methods
  private val ATTRIB_MUST_HAVE_NAME_POLICY_DATA = s"Line: $line - Attribute $attribName must have a NAME, UPDATE POLICY (${ATTRIBUTE_UPDATE_POLICIES.mkString(" | ")} ) and DATA TYPE (${ATTRIBUTE_DATA_TYPE_ANTIDOTE.mkString(" | ")} ) "

  idx_of_token += 1
  val attribPolicy: String = getAttribUpdatePolicy()
  val allowConcurrentUpdates: Boolean = attribPolicy != "NO_CONCURRENCY"

  idx_of_token += 1
  val (attribDataType,attribDataType_CRDT): (String, String) = getAttribDataType()

  val attribInvariant: AttribInvariants = new AttribInvariants(lineTokens, line, sysTablesMap, this)

  println("Created: " + this.toString)

  /**
   *  CONSTRUCTOR AUXILIARY METHODS
   */

  private def getAttribUpdatePolicy(): String = {
    lineTokens.lift(idx_of_token) match {
      case Some(policy) if ATTRIBUTE_UPDATE_POLICIES.contains(policy) => policy
      case Some(dataType) if ATTRIBUTE_DATA_TYPE_ANTIDOTE.contains(dataType) =>
        idx_of_token -= 1 //adjust idx because there was no token for "No Concurrency"
        "NO_CONCURRENCY"
      case _ =>
        println("entrou "+ lineTokens.lift(idx_of_token))
        throw new IllegalArgumentException("at getAttribUpdatePolicy " + ATTRIB_MUST_HAVE_NAME_POLICY_DATA)
    }
  }

  private def getAttribDataType(): (String, String) = {
    val dataType = lineTokens.lift(idx_of_token) match {
      case Some(dt) if ATTRIBUTE_DATA_TYPE_ANTIDOTE.contains(dt) =>
        ATTRIBUTE_DATA_TYPE_VERIFX (ATTRIBUTE_DATA_TYPE_ANTIDOTE.indexOf(dt))
      case _ => throw new IllegalArgumentException("at getAttribDataType " + ATTRIB_MUST_HAVE_NAME_POLICY_DATA)
    }
    //TODO: fazer match com todos os casos
    attribPolicy match {
      case "NO_CONCURRENCY" =>     (dataType, "")
      case "LWW" => (dataType, s"LWWRegister[$dataType]")
      case _ => throw new Exception ("TODO: Implement getAttribDataType() at CreateAttribute.scala")
    }
  }

  def setAttribPK(): Unit = attribInvariant.setAttribPK()

  /**
   *  Other Methods
   */

  override def toString: String = {
    s"ATTRIBUTE: ${attribName.toUpperCase()}, $attribPolicy, $attribDataType, Invariants: ${attribInvariant.toString}"
  }


}



