package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class ArtProofs extends FlatSpec with Prover {


	// ELEMENT
	// WORKS  -  20-30 seconds
	"Art" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("be a CvRDT (element)")
		val p = ("Art", "is_a_CvRDT")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be a CvRDT (element)")
		p
	}


	//TABLE
	// WORKS  -   20-30 seconds
	"ArtsTable" should "be merge commutative (table)" in {
		val startTime = printStartingTime("be merge commutative (table)")
		val p = ("ArtsTable", "mergeCommutative")
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (table)")
		p
	}


	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

//	// WORKS  -  20-30 seconds
//	"Art" should "be a CvRDT (element)" in {
//		val startTime = printStartingTime("be a CvRDT (element)")
//		val p = ("Art", "is_a_CvRDT")
//		prove(p)
//		printProofTime(startTime, System.nanoTime(), "be a CvRDT (element)")
//		p
//	}

	// WORKS  -  20-40 seconds
	"Art" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatible commutes (element)")
		val p = ("Art", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Art" should "compare correct (element)" in {
		val startTime = printStartingTime("compare correct (element)")
		val p = ("Art", "compareCorrect") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Art" should "updateAge works (element)" in {
		val startTime = printStartingTime("updateAge works (element)")
		val p = ("Art", "Art_updateAge_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "updateAge works (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Art" should "updateRanking works (element)" in {
		val startTime = printStartingTime("updateRanking works (element)")
		val p = ("Art", "Art_updateRanking_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "updateRanking works (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

//	// WORKS  -   20-30 seconds
//	"ArtsTable" should "be merge commutative (table)" in {
//		val startTime = printStartingTime("be merge commutative (table)")
//		val p = ("ArtsTable", "mergeCommutative")
//		prove(p)
//		printProofTime(startTime, System.nanoTime(), "be merge commutative (table)")
//		p
//	}

	//  !!!   ABORTED  in 20-30 seconds!!!
	"ArtsTable" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("ArtsTable", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	//  !!!   ABORTED   in  25 minutes(song_view)   !!!
	"ArtsTable" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("ArtsTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}

	// WORKS  -   30 seconds   -  1:30 minutes
	"ArtsTable" should "be merge associative2 (table)" in {
		val startTime = printStartingTime("be merge associative2 (table)")
		val p = ("ArtsTable", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (table)")
		p
	}

	// WORKS  -   20-30 seconds
	"ArtsTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatible commutes (table)")
		val p = ("ArtsTable", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (table)")
		p
	}



	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

}
