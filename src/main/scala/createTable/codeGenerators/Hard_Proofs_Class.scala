package createTable.codeGenerators

import antidoteSQL_to_veriFx.System_Constants._
import createTable.Table
import createTable.codeGenerators.Proofs_Class._

import scala.collection.mutable

object Hard_Proofs_Class {

  def gen_hard_proofs(sysTablesMap: mutable.Map[String, Table]): StringBuilder = {

    println("generating file code at Hard_Proofs Class")

    val classContent = new StringBuilder

    // PACKAGE, IMPORTS CLASS HEADER
    gen_package_name(classContent)
    gen_imports(classContent)
    gen_Class_Header(classContent, "Hard_Proofs")

    // TABLE HARD PROOFS
    table_hard_proofs.map { proof =>
      generateProofFunctionsComments(proof._1 + "  " + proof._2._2, classContent)
      val sortedTablesSeq = sysTablesMap.toSeq.sortBy(_._2.fk_attributes.size)
      sortedTablesSeq.map { case (t_name, table) =>
        generateProofFunction(mutable.LinkedHashMap(proof._1 -> (proof._2._1, proof._2._2)), table.tableNames._2, classContent)
      }
    }

    // FK HARD PROOFS - TODO: factorizar este codigo
    fk_hard_proofs.map { proof =>
      generateProofFunctionsComments(proof._1 + "  " + proof._2._2, classContent)
      val sortedTablesSeq = sysTablesMap.toSeq.filter(_._2.fk_attributes.nonEmpty).sortBy(_._2.fk_attributes.size)
      sortedTablesSeq.map { case (t_name, table) =>
        generateProofFunction(mutable.LinkedHashMap(proof._1 -> (proof._2._1, proof._2._2)), table.tableNames._3, classContent)
      }
    }


    //CLASS CLOSE
    classContent.append(s"\n\n}")

    classContent

  }

}
