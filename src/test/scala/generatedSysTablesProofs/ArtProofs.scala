package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ArtProofs extends FlatSpec with Prover { 



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Art" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("is_a_CvRDT")
		val p = ("Art", "is_a_CvRDT") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "is_a_CvRDT")
		p
	}

	// WORKS  -   20-30 seconds
	"Art" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatibleCommutes")
		val p = ("Art", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatibleCommutes")
		p
	}

	// WORKS  -   20-30 seconds
	"Art" should "compare correct (element)" in {
		val startTime = printStartingTime("compareCorrect")
		val p = ("Art", "compareCorrect") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compareCorrect")
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Art" should "updateAge works (element)" in {
		val startTime = printStartingTime("Art_updateAge_works")
		val p = ("Art", "Art_updateAge_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "Art_updateAge_works")
		p
	}

	// WORKS  -   20-30 seconds
	"Art" should "updateRanking works (element)" in {
		val startTime = printStartingTime("Art_updateRanking_works")
		val p = ("Art", "Art_updateRanking_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "Art_updateRanking_works")
		p
	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"ArtsTable" should "be commutative (table)" in {
		val startTime = printStartingTime("mergeCommutative")
		val p = ("ArtsTable", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeCommutative")
		p
	}

	//  !!!   ABORTED  in 20-30 seconds!!!
	"ArtsTable" should "be idempotent (table)" in {
		val startTime = printStartingTime("mergeIdempotent")
		val p = ("ArtsTable", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeIdempotent")
		p
	}

	//  !!!   DON´T TERMINATE (WAITED 10 MINUTES)  !!!
	"ArtsTable" should "be associative (table)" in {
		val startTime = printStartingTime("mergeAssociative")
		val p = ("ArtsTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative")
		p
	}

	// WORKS  -   30-50 seconds - with complex cases don't terminate
	"ArtsTable" should "be associative2 (table)" in {
		val startTime = printStartingTime("mergeAssociative2")
		val p = ("ArtsTable", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative2")
		p
	}

	// WORKS   -   20-40 seconds
	"ArtsTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatibleCommutes")
		val p = ("ArtsTable", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatibleCommutes")
		p
	}



	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////



	////////////////////////////////////////////////////////////
	// HELPER METHODS 
	////////////////////////////////////////////////////////////

	// print starting time
	private def printStartingTime(proof: String): Long = { 
		val currentTime = LocalTime.now() 
		val formatter = DateTimeFormatter.ofPattern("HH:mm:ss") 
		val formattedTime = currentTime.format(formatter) 
		println("Test  ( " + proof + " ) started at " + formattedTime) 
		System.nanoTime() 
	} 

	// print proof time
	private def printProofTime(startTime: Long, endTime: Long, proof: String):Unit = { 
		val durationInSeconds = (endTime - startTime) / 1e9d // convert nanoseconds to seconds 
		val minutes = (durationInSeconds / 60).toInt 
		val seconds = (durationInSeconds % 60).toInt 
		println("Proof " + proof + " took " +  minutes + " minutes : " + seconds + " seconds.") 
		} 

}
