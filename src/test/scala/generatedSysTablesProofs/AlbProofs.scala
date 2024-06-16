package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class AlbProofs extends FlatSpec with Prover {



	////////////////////////////////////////////////////////////
	// DWFlag
	////////////////////////////////////////////////////////////

	// WORKS	-  15 seconds
	val DWFlagCompare = "compare correct (DWFlags)"
	"DWFlags" should DWFlagCompare in {
		val startTime = printStartingTime(DWFlagCompare)
		val p = ("DWFlags", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), DWFlagCompare)
		p
	}

	// WORKS	-  20 seconds
	val DWFlagCvRDT = "is_a_CvRDT (merge correct e compatible commutes) (DWFlags)"
	"DWFlags" should DWFlagCvRDT in {
		val startTime = printStartingTime(DWFlagCvRDT)
		val p = ("DWFlags", "is_a_CvRDT")
		prove(p)
		printProofTime(startTime, System.nanoTime(), DWFlagCvRDT)
		p
	}




	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait
	////////////////////////////////////////////////////////////

	// WORKS  -  16 seconds
	val elemCompare = "compare correct (Alb Element)"
	"Alb" should elemCompare in {
		val startTime = printStartingTime(elemCompare)
		val p = ("Alb", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), elemCompare)
		p
	}

	// WORKS  -  16 seconds
	val elemCvRDT = "is_a_CvRDT (merge Correct e compatible commutes) (Alb Element)"
	"Alb" should elemCvRDT in {
		val startTime = printStartingTime(elemCvRDT)
		val p = ("Alb", "is_a_CvRDT")
		prove(p)
		printProofTime(startTime, System.nanoTime(), elemCvRDT)
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

	// WORKS  -   20 seconds
	val tabCompare = "compare correct (AlbsTable)"
	"AlbsTable" should tabCompare in {
		val startTime = printStartingTime(tabCompare)
		val p = ("AlbsTable", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabCompare)
		p
	}

	// WORKS  -   20 seconds (with assumptions)
	val tabCompatible = "compatible commutes (AlbsTable)"
	"AlbsTable" should tabCompatible in {
		val startTime = printStartingTime(tabCompatible)
		val p = ("AlbsTable", "compatibleCommutes")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabCompatible)
		p
	}


	// WORKS  -   20 seconds
	val tabMergIdemp = "be a merge idempotent (AlbsTable)"
	"AlbsTable" should tabMergIdemp in {
		val startTime = printStartingTime(tabMergIdemp)
		val p = ("AlbsTable", "mergeIdempotent")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabMergIdemp)
		p
	}

	// WORKS  -   20 seconds
	val tabMergComute = "be merge commutative (AlbsTable)"
	"AlbsTable" should tabMergComute in {
		val startTime = printStartingTime(tabMergComute)
		val p = ("AlbsTable", "mergeCommutative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabMergComute)
		p
	}


	// WORKS  -   30 seconds
	val tabMergAssoc = "be merge associative (AlbsTable)"
	"AlbsTable" should tabMergAssoc in {
		val startTime = printStartingTime(tabMergAssoc)
		val p = ("AlbsTable", "mergeAssociative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabMergAssoc)
		p
	}

	// ?????
	val tabMergReach  = "be merge Reachable (AlbsTable)"
	"AlbsTable" should tabMergReach in {
		val startTime = printStartingTime(tabMergReach)
		val p = ("AlbsTable", "mergeReachable")
		prove(p)
//		val res = rejectForModel(p)
//		print(res)
		printProofTime(startTime, System.nanoTime(), tabMergReach)
		p
	}

	// WORKS  -  30 seconds
	val tabMergCompat = "be merge Compatible (AlbsTable)"
	"AlbsTable" should tabMergCompat in {
		val startTime = printStartingTime(tabMergCompat)
		val p = ("AlbsTable", "mergeCompatible")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabMergCompat)
		p
	}





	////////////////////////////////////////////////////////////
	// FK SYSTEM CvRDT PROOFS
	////////////////////////////////////////////////////////////

	// WORKS - 40 seconds
	val FKSystCompare = "compare correct (Alb_FK_System)"
	"Alb_FK_System" should FKSystCompare in {
		val startTime = printStartingTime(FKSystCompare)
		val p = ("Alb_FK_System", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), FKSystCompare)
		p
	}


	// WORKS - 40 seconds
	val FKSystCompatible = "compatible commutes (Alb_FK_System)"
	"Alb_FK_System" should FKSystCompatible in {
		val startTime = printStartingTime(FKSystCompatible)
		val p = ("Alb_FK_System", "compatibleCommutes")
		prove(p)
		printProofTime(startTime, System.nanoTime(), FKSystCompatible)
		p
	}

	// WORKS - 30 seconds
	val FKSystMergIdemp = "be merge idempotent (Alb_FK_System)"
	"Alb_FK_System" should FKSystMergIdemp in {
		val startTime = printStartingTime(FKSystMergIdemp)
		val p = ("Alb_FK_System", "mergeIdempotent")
		prove(p)
		printProofTime(startTime, System.nanoTime(), FKSystMergIdemp)
		p
	}


	// WORKS -    1 minute 40 seconds
	val FKSystMergComute = "be merge commutative (Alb_FK_System)"
	"Alb_FK_System" should FKSystMergComute in {
		val startTime = printStartingTime(FKSystMergComute)
		val p = ("Alb_FK_System", "mergeCommutative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), FKSystMergComute)
		p
	}



	// ABORTED -----------------------
	val FKSystMergAssoc = "be merge associative (Alb_FK_System)"
	"Alb_FK_System" should FKSystMergAssoc in {
		val startTime = printStartingTime(FKSystMergAssoc)
		val p = ("Alb_FK_System", "mergeAssociative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), FKSystMergAssoc)
		p
	}



	// ABORTED -----------------------
	val FKSystMergReach = "be merge Reachable (Alb_FK_System)"
	"Alb_FK_System" should FKSystMergReach in {
		val startTime = printStartingTime(FKSystMergReach)
		val p = ("Alb_FK_System", "mergeReachable")
		prove(p)
		printProofTime(startTime, System.nanoTime(), FKSystMergReach)
		p
	}


	// WORKS - 50 seconds
	val FKSystMergCompat = "be merge Compatible (Alb_FK_System)"
	"Alb_FK_System" should FKSystMergCompat in {
		val startTime = printStartingTime(FKSystMergCompat)
		val p = ("Alb_FK_System", "mergeCompatible")
		prove(p)
		printProofTime(startTime, System.nanoTime(), FKSystMergCompat)
		p
	}


	////////////////////////////////////////////////////////////
	// FK REFERENTIAL INTEGRITY PROOFS
	////////////////////////////////////////////////////////////

	// ABORTED -----------------------
	val FKSystGenRefInt = "genericReferentialIntegrity (Alb_FK_System)"
	"Alb_FK_System" should FKSystGenRefInt in {
		val startTime = printStartingTime(FKSystGenRefInt)
		val p = ("Alb_FK_System", "genericReferentialIntegrity")
		prove(p)
		printProofTime(startTime, System.nanoTime(), FKSystGenRefInt)
		p
	}


	// TEST FOR OPERATIONS IN 2 REPLICAS:
	// WORKS -  10 seconds
	val FKSystConcDel = "refIntegrityHolds For Concurrent Deletions (Alb_FK_System)"
	"Alb_FK_System" should FKSystConcDel in {
		val startTime = printStartingTime(FKSystConcDel)
		val p = ("Alb_FK_System", "refIntegrityHolds_For_Concurrent_Deletions")
		prove(p)
		printProofTime(startTime, System.nanoTime(), FKSystConcDel)
		p
	}


}
