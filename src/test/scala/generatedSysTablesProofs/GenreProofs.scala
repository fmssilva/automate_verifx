package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class GenreProofs extends FlatSpec with Prover { 



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Genre" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("is_a_CvRDT")
		val p = ("Genre", "is_a_CvRDT") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "is_a_CvRDT")
		p
	}

	// WORKS  -   20-30 seconds
	"Genre" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatibleCommutes")
		val p = ("Genre", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatibleCommutes")
		p
	}

	// WORKS  -   20-30 seconds
	"Genre" should "compare correct (element)" in {
		val startTime = printStartingTime("compareCorrect")
		val p = ("Genre", "compareCorrect") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compareCorrect")
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Genre" should "updateDescription works (element)" in {
		val startTime = printStartingTime("Genre_updateDescription_works")
		val p = ("Genre", "Genre_updateDescription_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "Genre_updateDescription_works")
		p
	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"GenresTable" should "be commutative (table)" in {
		val startTime = printStartingTime("mergeCommutative")
		val p = ("GenresTable", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeCommutative")
		p
	}

	//  !!!   ABORTED  in 20-30 seconds!!!
	"GenresTable" should "be idempotent (table)" in {
		val startTime = printStartingTime("mergeIdempotent")
		val p = ("GenresTable", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeIdempotent")
		p
	}

	//  !!!   DON´T TERMINATE (WAITED 10 MINUTES)  !!!
	"GenresTable" should "be associative (table)" in {
		val startTime = printStartingTime("mergeAssociative")
		val p = ("GenresTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative")
		p
	}

	// WORKS  -   30-50 seconds - with complex cases don't terminate
	"GenresTable" should "be associative2 (table)" in {
		val startTime = printStartingTime("mergeAssociative2")
		val p = ("GenresTable", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative2")
		p
	}

	// WORKS   -   20-40 seconds
	"GenresTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatibleCommutes")
		val p = ("GenresTable", "compatibleCommutes") 
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
