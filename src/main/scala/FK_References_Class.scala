import scala.collection.mutable

/**
 * object to generate the code for a Class to manage the FKs
 */
object FK_References_Class {

  // Global variables
  private var classContent: StringBuilder = _
  private var table: Table = _
  private var tableName: String = _
  private var tableNameLowCase: String = _
  private var fk_attributes: mutable.Seq[TabAttribute] = _


  def generate_FK_References_ClassCode(table: Table, systemTablesFolderName: String, fk_system_name: String): StringBuilder = {

    println("generating file code at generate_FK_References_ClassCode() at CreateTable class")

    //Global Variables
    classContent = new StringBuilder
    this.table = table
    tableName = table.tableName
    tableNameLowCase = tableName.toLowerCase()
    fk_attributes = table.fk_attributes

    val referencedTableNames = table.fk_attributes.map(_.attribInvariant.fk_options.map(_.referencedTable.tableName).get).toSet.toList
    val at_name = table.attributesList.head.attribName
    val fk_tab_name = referencedTableNames.head.toLowerCase()




    //IMPORTS
    classContent.append(
      s"import antidote.crdts.lemmas.CvRDT" +
        s"\nimport antidote.crdts.LamportClock" +
        s"\nimport antidote.crdts.VersionVector" +
        s"\nimport antidote.crdts.lemmas.CvRDTProof1" +
        s"\nimport $systemTablesFolderName.${tableNameLowCase}s.${tableName}sTable" +
        referencedTableNames.map(t_Name =>
          s"\nimport $systemTablesFolderName.${t_Name.toLowerCase()}s.${t_Name}sTable"
        ).mkString
    )

    // CLASS HEADER
    classContent.append(
      s"\n\nclass $fk_system_name[Time](" +
        s"${tableNameLowCase}s: ${tableName}sTable[Time]," +
        referencedTableNames.map(
          t_Name =>
            s" ${t_Name.toLowerCase()}s: ${t_Name}sTable[Time]"
        ).mkString(", ")
        + s") \n\t\t\t extends CvRDT[$fk_system_name[Time]] {"
    )


    //override default implementation of methods in CvRDT trait
    classContent.append("\n\n\t//override default implementation of methods in CvRDT trait")

    //REACHABLE
    classContent.append(
      s"\n\n\toverride def reachable(): Boolean =" +
        s"\n\t\tthis.${tableNameLowCase}s.reachable() && this.${fk_tab_name}s.reachable()"
    )

    //COMPATIBLE
    classContent.append(
      s"\n\n\toverride def compatible(that: $fk_system_name[Time]): Boolean =" +
        s"\n\t\tthis.${tableNameLowCase}s.compatible(that.${tableNameLowCase}s) &&" +
        s" this.${fk_tab_name}s.compatible(that.${fk_tab_name}s)"
    )

    //EQUALS
    classContent.append(
      s"\n\n\toverride def equals(that: $fk_system_name[Time]) =" +
        s"\n\t\tthis == that"
    )


    //implement declared methods in CvRDT trait
    classContent.append("\n\n\t//implement declared methods in CvRDT trait")

    //MERGE
    classContent.append(
      s"\n\n\tdef merge(that: $fk_system_name[Time]) =" +
        s"\n\t\tnew $fk_system_name(this.${tableNameLowCase}s.merge(that.${tableNameLowCase}s), " +
        s"this.${fk_tab_name}s.merge(that.${fk_tab_name}s))"
    )

    //COMPARE
    classContent.append(
      s"\n\n\tdef compare(that: $fk_system_name[Time]) = //ignore" +
        s"\n\t\ttrue"
    )


    //methods to use in the Ref Integrity Proof
    classContent.append("\n\n\t//methods to use in the Ref Integrity Proof")

    //DEF REF_INTEGRITY_PROOF
    classContent.append(
      s"\n\n\tdef refIntegrityHolds($at_name: ${table.fk_attributes.head.attribDataType}) = {" +
        s"\n\t\t(this.${tableNameLowCase}s.isVisible($at_name) " +
        s"\n\t\t) =>: {" +
        s"\n\t\t\tval $tableNameLowCase = this.${tableNameLowCase}s.get($at_name)" +
        s"\n\t\t\tthis.${fk_tab_name}s.isVisible($tableNameLowCase.fst.$at_name)" +
        s"\n\t\t}" +
        s"\n\t}"
    )




    //other methods??
    classContent.append("\n\n\t//other methods??")

    classContent.append(
      s"\n\n\tdef reachableWithAssociativityAssumptions(): Boolean = {" +
        s"\n\t\tthis.${tableNameLowCase}s.reachable() && this.${fk_tab_name}s.reachable() &&" +
        s"\n\t\t\tthis.mergeValuesAssumptions()" +
        s"\n\t}"
    )

    classContent.append(
      s"\n\n\tprivate def mergeValuesAssumptions(): Boolean = {" +
        s"\n\t\tforall(v1: ${tableName}sTable[Time], v2: ${tableName}sTable[Time], v3: ${tableName}sTable[Time]) {" +
        s"\n\t\t  v1.merge(v2).merge(v3) == v1.merge(v2.merge(v3)) //merge is associative" +
        s"\n\t\t} &&" +
        s"\n\t\tforall(v1: ${fk_tab_name.capitalize}sTable[Time], v2: ${fk_tab_name.capitalize}sTable[Time], v3: ${fk_tab_name.capitalize}sTable[Time]) {" +
        s"\n\t\t   v1.merge(v2).merge(v3) == v1.merge(v2.merge(v3)) //merge is associative" +
        s"\n\t\t}" +
        s"\n\t}"
    )

    // CLASS END
    classContent.append("}")


    gen_ObjectExtraProofs(fk_system_name)

  }

  private def gen_ObjectExtraProofs(fk_system_name: String) = {

    // TODO: CHECK ALL FKs
    val at_name = table.attributesList.head.attribName

    //OBJECT
    classContent.append("\n\n\t//OBJECT FOR OTHER PROOFS")

    //OBJECT HEADER
    classContent.append(
      s"\n\nobject $fk_system_name extends CvRDTProof1[$fk_system_name] {"
    )

    //REF_INTEGRITY
    classContent.append(
      s"\n\n\tproof genericReferentialIntegrity[Time] {" +
        s"\n\t\tforall(" +
        s"s1: $fk_system_name[Time], s2: $fk_system_name[Time], " +
        //TODO: CHECK ALL FK OR PK ??
        s"$at_name: ${fk_attributes.head.attribDataType}" +
        s") {" +
        s"\n\t\t\t( s1.reachable() && s2.reachable() && " +
        s"\n\t\t\t  s1.compatible(s2) &&" +
        s"\n\t\t\t  s1.refIntegrityHolds($at_name) && s2.refIntegrityHolds($at_name) " +
        s"\n\t\t\t) =>: {" +
        s"\n\t\t\t\ts1.merge(s2).refIntegrityHolds($at_name)" +
        s"\n\t\t\t}" +
        s"\n\t\t}" +
        s"\n\t}"
    )

    //MERGE_ASSOCIATIVE
    classContent.append(
      s"\n\n\tproof mergeIsAssociative[Time] {" +
        s"\n\t\tforall(s1: $fk_system_name[Time], s2: $fk_system_name[Time], s3: $fk_system_name[Time]) {" +
        s"\n\t\t\t(s1.reachable() && s2.reachable() && s3.reachable() &&" +
        s"\n\t\t\t  s1.compatible(s2) && s1.compatible(s3) && s2.compatible(s3)" +
        s"\n\t\t\t) =>: {" +
        s"\n\t\t\t\tval aux = s1.merge(s2).merge(s3)" +
        s"\n\t\t\t\taux.equals(s1.merge(s2.merge(s3)))" +
        s"\n\t\t\t\taux.reachable()" +
        s"\n\t\t\t\t}" +
        s"\n\t\t}" +
        s"\n\t}"
    )

    // OBJECT END
    classContent.append("}")
  }
}
