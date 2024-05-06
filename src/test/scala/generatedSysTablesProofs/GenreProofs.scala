package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class GenreProofs extends FlatSpec with Prover { 



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -  20-30 seconds
	"Genre" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("be a CvRDT (element)")
		val p = ("Genre", "is_a_CvRDT") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be a CvRDT (element)")
		p
	}

	// WORKS  -  20-40 seconds
	"Genre" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatible commutes (element)")
		val p = ("Genre", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Genre" should "compare correct (element)" in {
		val startTime = printStartingTime("compare correct (element)")
		val p = ("Genre", "compareCorrect") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Genre" should "updateDescription works (element)" in {
		val startTime = printStartingTime("updateDescription works (element)")
		val p = ("Genre", "Genre_updateDescription_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "updateDescription works (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"GenresTable" should "be merge commutative (table)" in {
		val startTime = printStartingTime("be merge commutative (table)")
		val p = ("GenresTable", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (table)")
		p
	}

	//  !!!   ABORTED  in 20-30 seconds!!!
	"GenresTable" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("GenresTable", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	//  !!!   ABORTED   in  25 minutes(song_view)   !!!
	"GenresTable" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("GenresTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}

	// WORKS  -   30 seconds   -  1:30 minutes
	"GenresTable" should "be merge associative2 (table)" in {
		val startTime = printStartingTime("be merge associative2 (table)")
		val p = ("GenresTable", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (table)")
		p
	}

	// WORKS  -   20-30 seconds
	"GenresTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatible commutes (table)")
		val p = ("GenresTable", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (table)")
		p
	}



	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

}
