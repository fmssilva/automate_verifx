package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class ArtProofs extends FlatSpec with Prover {


	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20 seconds
	"Art" should "compare correct (element)" in {
		val startTime = printStartingTime("compare correct (element)")
		val p = ("Art", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct (element)")
		p
	}

	// WORKS  -  20 seconds
	"Art" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatible commutes (element)")
		val p = ("Art", "compatibleCommutes")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (element)")
		p
	}


	// WORKS  -  20 seconds
	"Art" should "merge Correct (element)" in {
		val startTime = printStartingTime("merge Correct (element)")
		val p = ("Art", "mergeCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "merge Correct (element)")
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
	"ArtsTable" should "compare Correct (table)" in {
		val startTime = printStartingTime("compare Correct (table)")
		val p = ("ArtsTable", "compareCorrect")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare Correct (table)")
		p
	}

	// WORKS  -   40 seconds
	"ArtsTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatible commutes (table)")
		val p = ("ArtsTable", "compatibleCommutes")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (table)")
		p
	}

	// WORKS  -   20 seconds
	"ArtsTable" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("ArtsTable", "mergeIdempotent")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	// WORKS  -   20 seconds
	"ArtsTable" should "be merge commutative (table)" in {
		val startTime = printStartingTime("be merge commutative (table)")
		val p = ("ArtsTable", "mergeCommutative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (table)")
		p
	}

	// WORKS  -   60 seconds
	"ArtsTable" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("ArtsTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}

	// WORKS  -   30 seconds
	"ArtsTable" should "be merge Reachable (table)" in {
		val startTime = printStartingTime("be merge Reachable (table)")
		val p = ("ArtsTable", "mergeReachable")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Reachable (table)")
		p
	}

	// WORKS  -   80 seconds
	"ArtsTable" should "be merge Compatible (table)" in {
		val startTime = printStartingTime("be merge Compatible (table)")
		val p = ("ArtsTable", "mergeCompatible")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Compatible (table)")
		p
	}



	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

}
