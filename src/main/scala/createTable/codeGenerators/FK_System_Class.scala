package createTable.codeGenerators

import createTable.Table

/**
 * object to generate the code for a Class to manage the FKs
 */
object FK_System_Class {

  // Global variables
  private var classContent: StringBuilder = _
  private var table: Table = _
  // (Element, ElementsTable, elementsTable , Elements_FK_System)
  private var pk_table_names: (String, String, String, String) = _
  // (Element, ElementsTable, + elementsTable)
  private var fk_Tables_names: Seq[(String, String, String)] = _

  /**
   * Generate the code for a Class to manage the FK System (Referential Integrity)
   *
   * @param table                  - table to generate the code for
   * @return - the code for the class
   */
  def generate_FK_System_ClassCode(table: Table): StringBuilder = {

    println("generating file code at generate_FK_System_ClassCode() at CreateTable class")

    //Global Variables
    this.classContent = new StringBuilder
    this.table = table
    this.pk_table_names = (table.tableNames._1, table.tableNames._2, table.tableNames._1.toLowerCase() + "sTable", table.tableNames._3)
    val referenced_Elem_names = table.fk_attributes.map(_.attribInvariant.fk_options.map(_.referencedTable.tableNames).get).toSet.toList
    this.fk_Tables_names = referenced_Elem_names.map(t_names => (t_names._1, t_names._2, t_names._1.toLowerCase() + "sTable"))


    // IMPORTS & CLASS HEADER
    gen_imports(table.systemTablesFolderName)
    gen_Class_Header()


    //OVERRIDE DEFAULT IMPLEMENTATION OF METHODS IN CvRDT TRAIT
    classContent.append("\n\n\n  /*\n   * OVERRIDE DEFAULT IMPLEMENTATION OF METHODS IN CvRDT TRAIT\n   */ ")
    gen_Reachable()
    gen_Compatible()
    gen_Equals()


    //IMPLEMENT DECLARED METHODS IN CvRDT TRAIT
    classContent.append("\n\n\n  /*\n   * IMPLEMENT DECLARED METHODS IN CvRDT TRAIT\n   */")
    gen_Merge()
    gen_Compare()

    // EXTRA METHODS FOR PROOFS
    classContent.append("\n\n\n\n  /*\n   * EXTRA METHODS FOR PROOFS\n   */")
    gen_Referential_Integrity()
    gen_Reachable_With_Assumptions()

    // CLASS END
    classContent.append("\n\n}")

    // OBJECT WITH EXTRA PROOFS
    gen_ObjectExtraProofs()

    classContent

  }

  ////////////////////////////////////////////////////////////////////
  ////////////---------- HELPER METHODS ---------- /////////////////
  ////////////////////////////////////////////////////////////////////


  /**
   * IMPORTS
   */
  private def gen_imports(systemTablesFolderName: String): Unit = {
    classContent.append(
      s"\nimport antidote.crdts.lemmas.CvRDT" +
        s"\nimport antidote.crdts.LamportClock" +
        s"\nimport antidote.crdts.VersionVector" +
        s"\nimport antidote.crdts.lemmas.CvRDTProof1" +
        s"\nimport $systemTablesFolderName.${pk_table_names._1.toLowerCase()}s.${pk_table_names._2}" +
        fk_Tables_names.map(
          t_Names => s"\nimport $systemTablesFolderName.${t_Names._1.toLowerCase()}s.${t_Names._2}"
        ).mkString
    )
  }

  /**
   * CLASS HEADER
   */
  private def gen_Class_Header(): Unit = {
    classContent.append(
      s"\n\n/*\n * class implementing the FK - Referential Integrity - Invariant\n */" +
        s"\nclass ${pk_table_names._4}[Time](" +
        s"\n\t\t\t\t\t${pk_table_names._3}: ${pk_table_names._2}[Time]," +
        fk_Tables_names.map(
          t_Names => s"\n\t\t\t\t\t${t_Names._3}: ${t_Names._2}[Time]"
        ).mkString(", ") + s" )" +
        s"\n\t\t\t\t\textends CvRDT[${pk_table_names._4}[Time]] {"
    )
  }

  /**
   * REACHABLE
   */
  private def gen_Reachable(): Unit = {
    classContent.append(
      s"\n\n\t//REACHABLE - check if this table and the referenced tables are reachable" +
        s"\n\toverride def reachable(): Boolean =" +
        s"\n\t\t\t\tthis.${pk_table_names._3}.reachable() && " +
        fk_Tables_names.map(
          t_Names => s"\n\t\t\t\tthis.${t_Names._3}.reachable()"
        ).mkString(" && ")
    )
  }

  /**
   * COMPATIBLE
   */
  private def gen_Compatible(): Unit = {
    classContent.append(
      s"\n\n\n\t//COMPATIBLE - check if this ${pk_table_names._4} is compatible with that ${pk_table_names._3}" +
        s"\n\toverride def compatible(that: ${pk_table_names._4}[Time]): Boolean =" +
        s"\n\t\t\t\tthis.${pk_table_names._3}.compatible(that.${pk_table_names._3}) &&" +
        fk_Tables_names.map(
          t_names => s"\n\t\t\t\tthis.${t_names._3}.compatible(that.${t_names._3})"
        ).mkString(" &&")
    )
  }

  /**
   * EQUALS
   */
  private def gen_Equals(): Unit = {
    classContent.append(
      s"\n\n\n\t//EQUALS - check if this ${pk_table_names._4} is equal to that ${pk_table_names._4}" +
        s"\n\toverride def equals(that: ${pk_table_names._4}[Time]) =" +
        s"\n\t\t\t\tthis == that"
    )
  }

  /**
   * MERGE
   */
  private def gen_Merge(): Unit = {
    classContent.append(
      s"\n\n\t//MERGE this ${pk_table_names._4} with that ${pk_table_names._4}" +
        s"\n\tdef merge(that: ${pk_table_names._4}[Time]) =" +
        s"\n\t\tnew ${pk_table_names._4}(" +
        s"\n\t\t\t\tthis.${pk_table_names._3}.merge(that.${pk_table_names._3}), " +
        fk_Tables_names.map(
          t_Names => s"\n\t\t\t\tthis.${t_Names._3}.merge(that.${t_Names._3})"
        ).mkString(",") + s" )"

    )
  }

  /**
   * COMPARE
   */
  private def gen_Compare(): Unit = {
    classContent.append(
      s"\n\n\t//COMPARE this ${pk_table_names._4} with that ${pk_table_names._4}" +
        s"\n\t//ignore" +
        s"\n\tdef compare(that: ${pk_table_names._4}[Time]) = " +
        s"\n\t\t\t\ttrue"
    )
  }

  /**
   * REFERENTIAL INTEGRITY
   */
  private def gen_Referential_Integrity(): Unit = {
    val pk_attrib = table.attributesList.head
    classContent.append(
      s"\n\n\t//REFERENTIAL INTEGRITY - check if for every elem of ${pk_table_names._2}, there is the corresponding FK element in the referenced table" +
        s"\n\tdef refIntegrityHolds(pk: ${pk_attrib.attribDataType}) = {" +
        s"\n\t\t(this.${pk_table_names._3}.isVisible(pk) " +
        s"\n\t\t ) =>: {" +
        s"\n\t\t\t\tval ${pk_table_names._1.toLowerCase()} = this.${pk_table_names._3}.get(pk).fst" +
        table.fk_attributes.map(
          fk_attrib => {
            var fk_tab_name = fk_attrib.attribInvariant.fk_options.map(_.referencedTable.tableNames).get._1
            fk_tab_name = fk_tab_name.toLowerCase() + "sTable"
            s"\n\t\t\t\tthis.$fk_tab_name.isVisible(${pk_table_names._1.toLowerCase()}.${fk_attrib.attribName})"
          }
        ).mkString(" &&") +
        s"\n\t\t\t\t}" +
        s"\n\t}"
    )
  }

  /**
   * REACHABLE WITH ASSUMPTIONS - ASSOCIATIVITY
   */
  private def gen_Reachable_With_Assumptions(): Unit = {
    classContent.append(
      s"\n\n\t// REACHABLE WITH ASSUMPTIONS - ASSOCIATIVITY" +
        s"\n\tdef reachableWithAssociativityAssumptions(): Boolean = {" +
        s"\n\t\t\t\tthis.${pk_table_names._3}.reachable() && " +
        fk_Tables_names.map(
          t_Names => s"\n\t\t\t\tthis.${t_Names._3}.reachable()"
        ).mkString(" &&") + s" && " +
        s"\n\t\t\t\tthis.mergeValuesAssumptions()" +
        s"\n\t}"
    )

    // HELPER METHOD - ASSUMPTIONS FOR MERGE
    classContent.append(
      s"\n\t\t//HELPER METHOD - ASSUMPTIONS FOR MERGE ASSOCIATIVITY - for the tables of this ${pk_table_names._4}" +
        s"\n\t\tprivate def mergeValuesAssumptions(): Boolean = {" +
        s"\n\t\t\tforall(v1: ${pk_table_names._2}[Time], v2: ${pk_table_names._2}[Time], v3: ${pk_table_names._2}[Time])" +
        s"\n\t\t\t\t  { v1.merge(v2).merge(v3) == v1.merge( v2.merge(v3) )" +
        s"\n\t\t\t\t  } &&" +
        fk_Tables_names.map(
          t_Names => {
            s"\n\t\t\tforall(v1: ${t_Names._2}[Time], v2: ${t_Names._2}[Time], v3: ${t_Names._2}[Time])" +
              s"\n\t\t\t\t  { v1.merge(v2).merge(v3) == v1.merge( v2.merge(v3) )" +
              s"\n\t\t\t\t  }"
          }
        ).mkString(" &&") +
        s"\n\t\t}"
    )
  }

  private def gen_ObjectExtraProofs(): Unit = {

    //OBJECT COMMENT & HEADER
    classContent.append(
      s"\n\n\n\n/*\n * OBJECT FOR EXTRA PROOFS\n */" +
        s"\nobject ${pk_table_names._4} extends CvRDTProof1[${pk_table_names._4}] {"
    )

    //REF_INTEGRITY
    classContent.append(
      s"\n\n\t//REFERENTIAL INTEGRITY" +
        s"\n\tproof genericReferentialIntegrity[Time] {" +
        s"\n\t\tforall(" +
        s"s1: ${pk_table_names._4}[Time], s2: ${pk_table_names._4}[Time], " +
        s"pk: ${table.attributesList.head.attribDataType}" +
        s") {" +
        s"\n\t\t\t( s1.reachable() && s2.reachable() && " +
        s"\n\t\t\t  s1.compatible(s2) &&" +
        s"\n\t\t\t  s1.refIntegrityHolds(pk) && s2.refIntegrityHolds(pk) " +
        s"\n\t\t\t) =>: {" +
        s"\n\t\t\t\ts1.merge(s2).refIntegrityHolds(pk)" +
        s"\n\t\t\t}" +
        s"\n\t\t}" +
        s"\n\t}"
    )

    //MERGE_ASSOCIATIVE
    classContent.append(
      s"\n\n\t//MERGE ASSOCIATIVE" +
        s"\n\tproof mergeIsAssociative[Time] {" +
        s"\n\t\tforall(s1: ${pk_table_names._4}[Time], s2: ${pk_table_names._4}[Time], s3: ${pk_table_names._4}[Time]) {" +
        s"\n\t\t\t(s1.reachable() && s2.reachable() && s3.reachable() &&" +
        s"\n\t\t\t  s1.compatible(s2) && s1.compatible(s3) && s2.compatible(s3)" +
        s"\n\t\t\t) =>: {" +
        s"\n\t\t\t\tval aux = s1.merge(s2).merge(s3)" +
        s"\n\t\t\t\taux.equals(s1.merge( s2.merge(s3) ))" +
        s"\n\t\t\t\taux.reachable()" +
        s"\n\t\t\t\t}" +
        s"\n\t\t}" +
        s"\n\t}"
    )

    // OBJECT END
    classContent.append("}")
  }
}