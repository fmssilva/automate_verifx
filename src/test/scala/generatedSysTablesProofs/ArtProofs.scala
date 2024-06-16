package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class ArtProofs extends FlatSpec with Prover {



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20 seconds
	 val elemCompare = "compare correct (element)"
	"Art" should  elemCompare in {
		val startTime = printStartingTime(elemCompare)
		val p = ("Art", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), elemCompare)
		p
	}

	// WORKS  -  20 seconds
	val elemCvRDT = "is_a_CvRDT (merge correct e compatible commutes)"
	"Art" should  elemCvRDT in {
		val startTime = printStartingTime(elemCvRDT)
		val p = ("Art", "is_a_CvRDT")
		prove(p)
		printProofTime(startTime, System.nanoTime(), elemCvRDT)
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

//	// WORKS  -   20 seconds
//	"Art" should "updateAge works (element)" in {
//		val startTime = printStartingTime("updateAge works (element)")
//		val p = ("Art", "Art_updateAge_works")
//		prove(p)
//		printProofTime(startTime, System.nanoTime(), "updateAge works (element)")
//		p
//	}
//
//	// WORKS  -   20-30 seconds
//	"Art" should "updateRanking works (element)" in {
//		val startTime = printStartingTime("updateRanking works (element)")
//		val p = ("Art", "Art_updateRanking_works")
//		prove(p)
//		printProofTime(startTime, System.nanoTime(), "updateRanking works (element)")
//		p
//	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20 seconds
	val tabCompare = "compare correct (ArtsTable)"
	"ArtsTable" should tabCompare in {
		val startTime = printStartingTime(tabCompare)
		val p = ("ArtsTable", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabCompare)
		p
	}

	// WORKS  -   40 seconds
	val tabCompatible = "compatible commutes (ArtsTable)"
	"ArtsTable" should tabCompatible in {
		val startTime = printStartingTime(tabCompatible)
		val p = ("ArtsTable", "compatibleCommutes")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabCompatible)
		p
	}

	// WORKS  -   20 seconds
	val tabMergIdemp = "be merge idempotent (ArtsTable)"
	"ArtsTable" should tabMergIdemp in {
		val startTime = printStartingTime(tabMergIdemp)
		val p = ("ArtsTable", "mergeIdempotent")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabMergIdemp)
		p
	}

	// WORKS  -   20 seconds
	val tabMergComute = "be merge commutative (ArtsTable)"
	"ArtsTable" should tabMergComute in {
		val startTime = printStartingTime(tabMergComute)
		val p = ("ArtsTable", "mergeCommutative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabMergComute)
		p
	}


	// WORKS  -   90 seconds
	val tabMergAssoc = "be merge associative (ArtsTable)"
	"ArtsTable" should tabMergAssoc in {
		val startTime = printStartingTime(tabMergAssoc)
		val p = ("ArtsTable", "mergeAssociative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabMergAssoc)
		p
	}

	// WORKS  -   30 seconds
	val tabMergReach = "be merge Reachable (ArtsTable)"
	"ArtsTable" should tabMergReach in {
		val startTime = printStartingTime(tabMergReach)
		val p = ("ArtsTable", "mergeReachable")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabMergReach)
		p
	}

	// WORKS  -   40 seconds
	val tabMergCompat = "be merge Compatible (ArtsTable)"
	"ArtsTable" should tabMergCompat in {
		val startTime = printStartingTime(tabMergCompat)
		val p = ("ArtsTable", "mergeCompatible")
		prove(p)
		printProofTime(startTime, System.nanoTime(), tabMergCompat)
		p
	}



	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

}
