package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class AlbProofs extends FlatSpec with Prover {

	//DWFlags:
	// WORKS	-  20 seconds
	"DWFlags" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("be a CvRDT (element)")
		val p = ("DWFlags", "is_a_CvRDT")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be a CvRDT (element)")
		p
	}

	// WORKS	-  20 seconds
	"DWFlags" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatible commutes (element)")
		val p = ("DWFlags", "compatibleCommutes")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (element)")
		p
	}

	// WORKS	-  20 seconds
	"DWFlags" should "compare correct (element)" in {
		val startTime = printStartingTime("compare correct (element)")
		val p = ("DWFlags", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct (element)")
		p
	}




	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -  20 seconds
	"Alb" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("be a CvRDT (element)")
		val p = ("Alb", "is_a_CvRDT")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be a CvRDT (element)")
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
	"Alb" should "compare correct (element)" in {
		val startTime = printStartingTime("compare correct (element)")
		val p = ("Alb", "compareCorrect") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct (element)")
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


	// WORKS  -   30-40 seconds (with assumptions)
	"AlbsTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatible commutes (table)")
		val p = ("AlbsTable", "compatibleCommutes")
    prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (table)")
		p
	}

	// WORKS  -   40 seconds
	"AlbsTable" should "compareCorrect (equals) (fk_system)" in {
		val startTime = printStartingTime("compareCorrect (equals) (fk_system)")
		val p = ("AlbsTable", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compareCorrect (equals) (fk_system)")
		p
	}

	// WORKS  -   30 seconds
	"AlbsTable" should "be merge idempotent (table)" in {
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


	// WORKS  -   50 seconds
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

	// WORKS  -   50 seconds
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

	// WORKS - 60 seconds
	"Alb_FK_System" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatible commutes (fk_system)")
		val p = ("Alb_FK_System", "compatibleCommutes")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (fk_system)")
		p
	}

	// ???
	"Alb_FK_System" should "compareCorrect (equals) (fk_system)" in {
		val startTime = printStartingTime("compareCorrect (equals) (fk_system)")
		val p = ("Alb_FK_System", "compareCorrect")
		prove(p)
//		val res = rejectForModel(p)
//		print(res)
		printProofTime(startTime, System.nanoTime(), "compareCorrect (equals) (fk_system)")
		p
	}

	// WORKS - 40 seconds
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


	// WORKS - 30 seconds
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

	// WORKS - 60 seconds
	"Alb_FK_System" should "be merge Compatible (fk_system)" in {
		val startTime = printStartingTime("be merge Compatible (fk_system)")
		val p = ("Alb_FK_System", "mergeCompatible")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Compatible (fk_system)")
		p
	}

	//Referential Integrity
	// Proofs implemented in FK_System Class

	//    ???
	"Alb_FK_System" should "keep referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("keep referential integrity generic proof (fk_system)")
		val p = ("Alb_FK_System", "genericReferentialIntegrity") 
//		prove(p)
		val res = rejectForModel(p)
		print(res)
		printProofTime(startTime, System.nanoTime(), "keep referential integrity generic proof (fk_system)")
		p
	}

}
