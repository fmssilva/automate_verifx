package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class Art_cpkProofs extends FlatSpec with Prover { 



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Art_cpk" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("be a CvRDT (element)")
		val p = ("Art_cpk", "is_a_CvRDT") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be a CvRDT (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Art_cpk" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatible commutes (element)")
		val p = ("Art_cpk", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Art_cpk" should "compare correct (element)" in {
		val startTime = printStartingTime("compare correct (element)")
		val p = ("Art_cpk", "compareCorrect") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Art_cpk" should "updateAge works (element)" in {
		val startTime = printStartingTime("updateAge works (element)")
		val p = ("Art_cpk", "Art_cpk_updateAge_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "updateAge works (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Art_cpk" should "updateRanking works (element)" in {
		val startTime = printStartingTime("updateRanking works (element)")
		val p = ("Art_cpk", "Art_cpk_updateRanking_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "updateRanking works (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Art_cpksTable" should "be merge commutative (table)" in {
		val startTime = printStartingTime("be merge commutative (table)")
		val p = ("Art_cpksTable", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (table)")
		p
	}

	//  !!!   ABORTED  in 20-30 seconds!!!
	"Art_cpksTable" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("Art_cpksTable", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	//  !!!   ABORTED   in  25 minutes(song_view)   !!!
	"Art_cpksTable" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("Art_cpksTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}

	// WORKS  -   30 seconds   -  1:30 minutes
	"Art_cpksTable" should "be merge associative2 (table)" in {
		val startTime = printStartingTime("be merge associative2 (table)")
		val p = ("Art_cpksTable", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (table)")
		p
	}

	// WORKS  -   20-30 seconds
	"Art_cpksTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatible commutes (table)")
		val p = ("Art_cpksTable", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (table)")
		p
	}



	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

}
