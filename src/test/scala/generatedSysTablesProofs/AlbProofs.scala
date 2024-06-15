package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class AlbProofs extends FlatSpec with Prover {

	////////////////////////////////////////////////////////////
	// DWFlag
	////////////////////////////////////////////////////////////

	// WORKS	-  15 seconds
	"DWFlags" should "compare correct" in {
		val startTime = printStartingTime("compare correct")
		val p = ("DWFlags", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct")
		p
	}

	// WORKS	-  25 seconds
	"DWFlags" should "is_a_CvRDT" in {
		val startTime = printStartingTime("is_a_CvRDT")
		val p = ("DWFlags", "is_a_CvRDT")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "is_a_CvRDT")
		p
	}




	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait
	////////////////////////////////////////////////////////////

	// WORKS  -  16 seconds
	"Alb" should "compare correct (element)" in {
		val startTime = printStartingTime("compare correct (element)")
		val p = ("Alb", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct (element)")
		p
	}

	// WORKS  -  16 seconds
	"Alb" should "is_a_CvRDT: merge Correct e compatible commutes" in {
		val startTime = printStartingTime("is_a_CvRDT: merge Correct e compatible commutes")
		val p = ("Alb", "is_a_CvRDT")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "is_a_CvRDT: merge Correct e compatible commutes")
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

	// WORKS  -   25 seconds
	"AlbsTable" should "compareCorrect (equals) (table)" in {
		val startTime = printStartingTime("compareCorrect (equals) (table)")
		val p = ("AlbsTable", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compareCorrect (equals) (table)")
		p
	}

	// WORKS  -   30 seconds (with assumptions)
	"AlbsTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatible commutes (table)")
		val p = ("AlbsTable", "compatibleCommutes")
    prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (table)")
		p
	}


	// WORKS  -   25 seconds
	"AlbsTable" should "be a merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("AlbsTable", "mergeIdempotent")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	// WORKS  -   27 seconds
	"AlbsTable" should "be merge commutative (table)" in {
		val startTime = printStartingTime("be merge commutative (table)")
		val p = ("AlbsTable", "mergeCommutative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (table)")
		p
	}


	// WORKS  -   1 minute 20 seconds
	"AlbsTable" should "be merge Associative (table)" in {
		val startTime = printStartingTime("be merge Associative (table)")
		val p = ("AlbsTable", "mergeAssociative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Associative (table)")
		p
	}

	// WORKS  -  1 minute 20 seconds
	"AlbsTable" should "be merge Reachable (table)" in {
		val startTime = printStartingTime("be merge Reachable (table)")
		val p = ("AlbsTable", "mergeReachable")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Reachable (table)")
		p
	}

	// WORKS  -  30 seconds
	"AlbsTable" should "be merge Compatible (table)" in {
		val startTime = printStartingTime("be merge Compatible (table)")
		val p = ("AlbsTable", "mergeCompatible")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Compatible (table)")
		p
	}





	////////////////////////////////////////////////////////////
	// FK REFERENTIAL CvRDT PROOFS
	////////////////////////////////////////////////////////////

	// WORKS - 40 seconds
	"Alb_FK_System" should "compareCorrect (equals) (fk_system)" in {
		val startTime = printStartingTime("compareCorrect (equals) (fk_system)")
		val p = ("Alb_FK_System", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compareCorrect (equals) (fk_system)")
		p
	}


	// WORKS - 40 seconds
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

	// WORKS -    1 minute 40 seconds
	"Alb_FK_System" should "be merge commutative (fk_system)" in {
		val startTime = printStartingTime("be merge commutative (fk_system)")
		val p = ("Alb_FK_System", "mergeCommutative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (fk_system)")
		p
	}


	// ABORTED -----------------------
	"Alb_FK_System" should "be merge associative (fk_system)" in {
		val startTime = printStartingTime("be merge associative (fk_system)")
		val p = ("Alb_FK_System", "mergeAssociative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (fk_system)")
		p
	}


	// ABORTED -----------------------
	"Alb_FK_System" should "be merge Reachable (fk_system)" in {
		val startTime = printStartingTime("be merge Reachable (fk_system)")
		val p = ("Alb_FK_System", "mergeReachable")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Reachable (fk_system)")
		p
	}

	// WORKS - 50 seconds
	"Alb_FK_System" should "be merge Compatible (fk_system)" in {
		val startTime = printStartingTime("be merge Compatible (fk_system)")
		val p = ("Alb_FK_System", "mergeCompatible")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Compatible (fk_system)")
		p
	}

	////////////////////////////////////////////////////////////
	// FK REFERENTIAL Ref Integrity PROOFS
	////////////////////////////////////////////////////////////

	// ABORTED -----------------------
	"Alb_FK_System" should "genericReferentialIntegrity (fk_system)" in {
		val startTime = printStartingTime("genericReferentialIntegrity (fk_system)")
		val p = ("Alb_FK_System", "genericReferentialIntegrity")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "genericReferentialIntegrity (fk_system)")
		p
	}

	// TEST FOR OPERATIONS IN 2 REPLICAS:
	// ABORTED -----------------------
	"Alb_FK_System" should "refIntegrityHolds For Concurrent Deletions (fk_system)" in {
		val startTime = printStartingTime("refIntegrityHolds For Concurrent Deletions (fk_system)")
		val p = ("Alb_FK_System", "refIntegrityHolds_For_Concurrent_Deletions")
		prove(p)
//		val res = rejectForModel(p)
//		print(res)
		printProofTime(startTime, System.nanoTime(), "refIntegrityHolds For Concurrent Deletions (fk_system)")
		p
	}

}
