package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class AlbProofs extends FlatSpec with Prover {

	////////////////////////////////////////////////////////////
	// DWFlag
	////////////////////////////////////////////////////////////

	// WORKS	-  20 seconds
	"DWFlags" should "compare correct" in {
		val startTime = printStartingTime("compare correct")
		val p = ("DWFlags", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct")
		p
	}

	// WORKS	-  20 seconds
	"DWFlags" should "compatible commutes" in {
		val startTime = printStartingTime("compatible commutes")
		val p = ("DWFlags", "compatibleCommutes")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes")
		p
	}

	// WORKS	-  20 seconds
	"DWFlags" should "merge Correct" in {
		val startTime = printStartingTime("merge Correct")
		val p = ("DWFlags", "mergeCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "merge Correct")
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -  20 seconds
	"Alb" should "compare correct (element)" in {
		val startTime = printStartingTime("compare correct (element)")
		val p = ("Alb", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct (element)")
		p
	}

	// WORKS  -  20 seconds
	"Alb" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatible commutes (element)")
		val p = ("Alb", "compatibleCommutes")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (element)")
		p
	}

	// WORKS  -  20 seconds
	"Alb" should "merge Correct (element)" in {
		val startTime = printStartingTime("merge Correct (element)")
		val p = ("Alb", "mergeCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "merge Correct (element)")
		p
	}




//
//	////////////////////////////////////////////////////////////
//	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof
//	////////////////////////////////////////////////////////////
//
//	// WORKS  -   20-30 seconds
//	"Alb" should "updateYear works (element)" in {
//		val startTime = printStartingTime("updateYear works (element)")
//		val p = ("Alb", "Alb_updateYear_works")
////		prove(p)
//		val res = rejectForModel(p)
//		print(res)
////		printProofTime(startTime, System.nanoTime(), "updateYear works (element)")
//
//		p
//	}
//
//	// WORKS  -   20-30 seconds
//	"Alb" should "updatePrice works (element)" in {
//		val startTime = printStartingTime("updatePrice works (element)")
//		val p = ("Alb", "Alb_updatePrice_works")
//		prove(p)
//		printProofTime(startTime, System.nanoTime(), "updatePrice works (element)")
//		p
//	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   30 seconds
	"AlbsTable" should "compareCorrect (equals) (table)" in {
		val startTime = printStartingTime("compareCorrect (equals) (table)")
		val p = ("AlbsTable", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compareCorrect (equals) (table)")
		p
	}

	// WORKS  -   50 seconds (with assumptions)
	"AlbsTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatible commutes (table)")
		val p = ("AlbsTable", "compatibleCommutes")
    prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (table)")
		p
	}


	// WORKS  -   30 seconds
	"AlbsTable" should "be a merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("AlbsTable", "mergeIdempotent")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	// WORKS  -   30 seconds
	"AlbsTable" should "be merge commutative (table)" in {
		val startTime = printStartingTime("be merge commutative (table)")
		val p = ("AlbsTable", "mergeCommutative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (table)")
		p
	}


	// WORKS  -   40 seconds
	"AlbsTable" should "be merge Associative (table)" in {
		val startTime = printStartingTime("be merge Associative (table)")
		val p = ("AlbsTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Associative (table)")
		p
	}

	// WORKS  -   20 seconds
	"AlbsTable" should "be merge Reachable (table)" in {
		val startTime = printStartingTime("be merge Reachable (table)")
		val p = ("AlbsTable", "mergeReachable")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Reachable (table)")
		p
	}

	// WORKS  -   50 seconds - 3 minutes
	"AlbsTable" should "be merge Compatible (table)" in {
		val startTime = printStartingTime("be merge Compatible (table)")
		val p = ("AlbsTable", "mergeCompatible")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Compatible (table)")
		p
	}





	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

	// WORKS - 40 seconds
	"Alb_FK_System" should "compareCorrect (equals) (fk_system)" in {
		val startTime = printStartingTime("compareCorrect (equals) (fk_system)")
		val p = ("Alb_FK_System", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compareCorrect (equals) (fk_system)")
		p
	}


	// WORKS - 3 - 4 minutes
	"Alb_FK_System" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatible commutes (fk_system)")
		val p = ("Alb_FK_System", "compatibleCommutes")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (fk_system)")
		p
	}



	// WORKS - 30 seconds
	"Alb_FK_System" should "be merge idempotent (fk_system)" in {
		val startTime = printStartingTime("be merge idempotent (fk_system)")
		val p = ("Alb_FK_System", "mergeIdempotent")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (fk_system)")
		p
	}

	// WORKS - 40 seconds
	"Alb_FK_System" should "be merge commutative (fk_system)" in {
		val startTime = printStartingTime("be merge commutative (fk_system)")
		val p = ("Alb_FK_System", "mergeCommutative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (fk_system)")
		p
	}


	// WORKS - 60 seconds
	"Alb_FK_System" should "be merge associative (fk_system)" in {
		val startTime = printStartingTime("be merge associative (fk_system)")
		val p = ("Alb_FK_System", "mergeAssociative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (fk_system)")
		p
	}


	// WORKS - 30 seconds
	"Alb_FK_System" should "be merge Reachable (fk_system)" in {
		val startTime = printStartingTime("be merge Reachable (fk_system)")
		val p = ("Alb_FK_System", "mergeReachable")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Reachable (fk_system)")
		p
	}

	// ??
	"Alb_FK_System" should "be merge Compatible (fk_system)" in {
		val startTime = printStartingTime("be merge Compatible (fk_system)")
		val p = ("Alb_FK_System", "mergeCompatible")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Compatible (fk_system)")
		p
	}

	//Referential Integrity - Proof implemented in FK_System Class

	//  ???
	"Alb_FK_System" should "keep referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("keep referential integrity generic proof (fk_system)")
		val p = ("Alb_FK_System", "genericReferentialIntegrity") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "keep referential integrity generic proof (fk_system)")
		p
	}

}
