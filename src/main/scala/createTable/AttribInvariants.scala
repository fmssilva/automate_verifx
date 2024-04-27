package createTable

import scala.collection.mutable

@SerialVersionUID(1594622920505453437L)
case class AttribInvariants(lineTokens: Array[String], var line: Int, sysTablesMap: mutable.Map[String, Table], thisAttrib: TabAttribute) extends Serializable {
  /**
   * Class Constants
   */
  private val FK_UPDATE_POLICIES = Array("UPDATE-WINS", "DELETE-WINS", "NO CONCURRENCY")
  //TODO: other CHECK
  //private val BINARY_OPERATORS = Array("=", "<>", "!=", "<", ">", "<=", ">=", "AND", "OR", "+", "-", "*", "/", "%")
  //private val UNARY_OPERATORS = Array("NOT", "IN", "BETWEEN", "LIKE")
  // IN (Checks if a value matches any value in a list or subquery.)
  // BETWEEN value1 AND value2
  // LIKE (Matches a pattern in a string. It often uses wildcard characters % and _.)
  //private val NO_ARG_OPERATORS = Array("IS NULL", "IS NOT NULL")

  /**
   * Check Attribute invariants
   */
  var isPrimaryKey: Boolean = check_if_isPrimaryKey
  val fk_options: Option[FK_Options] = check_if_isForeignKey()
  val check_options: Option[Array[String]] = check_if_hasCheck()

  /**
   * Auxiliary Methods
   */
  private def check_if_isPrimaryKey: Boolean = {
    lineTokens.indexOf("PRIMARY") < lineTokens.length - 1 &&
      lineTokens(lineTokens.indexOf("PRIMARY") + 1) == "KEY"
  }

  def setAttribPK(): Unit = {
    isPrimaryKey = true
  }

  private def check_if_isForeignKey(): Option[FK_Options] = {
    val fk_idx = lineTokens.indexOf("FOREIGN") // will return -1 if there is no "Foreign"
    if (fk_idx < 0)
      return None // it's not a FK
    lineTokens.slice(fk_idx, fk_idx + 8) match {
      case Array("FOREIGN", "KEY", policy, "REFERENCES", referencedTable, "(", referencedColumn, ")") if FK_UPDATE_POLICIES.contains(policy) =>
        //check if has "ON DELETE CASCADE"
        val hasOnDeleteCascade = fk_idx + 10 < lineTokens.length && (lineTokens.slice(fk_idx + 8, fk_idx + 11) sameElements Array("ON", "DELETE", "CASCADE"))

        //check if that table and column exist in the system,
        val fk_TableOption = sysTablesMap.get(referencedTable.toLowerCase().capitalize)
        fk_TableOption match {
          case None =>
            throw new IllegalArgumentException(s"At Line: $line - The table $referencedTable should be created before being used as FK ")
          case Some(fk_Table) =>
            fk_Table.attributesList.find(_.attribName.equals(referencedColumn.toLowerCase())) match {
              case None =>
                throw new IllegalArgumentException(s"At Line: $line - The column $referencedColumn must exist as PK in referenced table $referencedTable ")
              case Some(at) if !at.attribInvariant.isPrimaryKey =>
                throw new IllegalArgumentException(s"At Line: $line - The column $referencedColumn must exist as PK in referenced table $referencedTable ")
              case Some(at) if !at.attribDataType.equals(thisAttrib.attribDataType) =>
                throw new IllegalArgumentException(s"At Line: $line - The attribute ${thisAttrib.attribName} must have the same data type as in the PK $referencedColumn in referenced table $referencedTable ")
              case Some(at) =>
                Some(FK_Options(policy, fk_Table, at, hasOnDeleteCascade))
            }
        }
      case _ => throw new IllegalArgumentException(s"At Line: $line - If an Attribute is a Foreign Key, it must have a valid update policy: (${FK_UPDATE_POLICIES.mkString(" | ")}) ")
    }
  }


  private def check_if_hasCheck(): Option[Array[String]] = {
    val check_start = lineTokens.indexOf("CHECK")
    if (check_start < 0)
      None
    else {
      var check_end = check_start + 1
      while (lineTokens(check_end) != ")")
        check_end += 1
      Some(lineTokens.slice(check_start + 2, check_end))
    }
  }


  def getReferencedTable: Table = fk_options match {
    case Some(options) => options.referencedTable
    case None => throw new NoSuchElementException("No FK Referenced createTable.Table available")
  }

  def getReferencedPK: TabAttribute = fk_options match {
    case Some(options) => options.referencedColumn
    case None => throw new NoSuchElementException("No FK Referenced PK available")
  }

  /**
   * Other Methods
   */
  override def toString: String = {
    val pkString = if (isPrimaryKey) "PrimaryKey" else "NotPrimaryKey"
    val fkString = fk_options.map(options => s"ForeignKey(${options.update_policy}, ${options.referencedTable.tableName}, ${options.referencedColumn.attribName}, ON DEL CASCADE = ${if (options.hasOnDeleteCascade) "true" else "false"})").getOrElse("NotForeignKey")
    val checkString = check_options.map(tokens => s"CheckOptions(${tokens.mkString("[", ", ", "]")})").getOrElse("NoCheckOptions")
    s"($pkString, $fkString, $checkString)"
  }

}



/**
 * case class for storing Foreign Key options
 */
case class FK_Options(
                       update_policy: String,
                       referencedTable: Table,
                       referencedColumn: TabAttribute,
                       hasOnDeleteCascade: Boolean
                     ) extends Serializable
