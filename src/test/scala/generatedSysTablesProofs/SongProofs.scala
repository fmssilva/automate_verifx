package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class SongProofs extends FlatSpec with Prover { 



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Song" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("is_a_CvRDT")
		val p = ("Song", "is_a_CvRDT") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "is_a_CvRDT")
		p
	}

	// WORKS  -   20-30 seconds
	"Song" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatibleCommutes")
		val p = ("Song", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatibleCommutes")
		p
	}

	// WORKS  -   20-30 seconds
	"Song" should "compare correct (element)" in {
		val startTime = printStartingTime("compareCorrect")
		val p = ("Song", "compareCorrect") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compareCorrect")
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Song" should "updateDuration works (element)" in {
		val startTime = printStartingTime("Song_updateDuration_works")
		val p = ("Song", "Song_updateDuration_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "Song_updateDuration_works")
		p
	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"SongsTable" should "be commutative (table)" in {
		val startTime = printStartingTime("mergeCommutative")
		val p = ("SongsTable", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeCommutative")
		p
	}

	//  !!!   ABORTED  in 20-30 seconds!!!
	"SongsTable" should "be idempotent (table)" in {
		val startTime = printStartingTime("mergeIdempotent")
		val p = ("SongsTable", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeIdempotent")
		p
	}

	//  !!!   DON´T TERMINATE (WAITED 10 MINUTES)  !!!
	"SongsTable" should "be associative (table)" in {
		val startTime = printStartingTime("mergeAssociative")
		val p = ("SongsTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative")
		p
	}

	// WORKS  -   30-50 seconds - with complex cases don't terminate
	"SongsTable" should "be associative2 (table)" in {
		val startTime = printStartingTime("mergeAssociative2")
		val p = ("SongsTable", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative2")
		p
	}

	// WORKS   -   20-40 seconds
	"SongsTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatibleCommutes")
		val p = ("SongsTable", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatibleCommutes")
		p
	}



	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

	//    !!!   DONT TERMINATE   !!!
	"Song_FK_System" should "maintain referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("genericReferentialIntegrity")
		val p = ("Song_FK_System", "genericReferentialIntegrity") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "genericReferentialIntegrity")
		p
	}

	// WORKS   -   40 sec - 1:40 min
	"Song_FK_System" should "be commutative (fk_system)" in {
		val startTime = printStartingTime("mergeCommutative")
		val p = ("Song_FK_System", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeCommutative")
		p
	}

	//    Albs e SongView ABORTED in 1 - 2 minutes;  Song - WORKS - 40 seconds
	"Song_FK_System" should "be idempotent (fk_system)" in {
		val startTime = printStartingTime("mergeIdempotent")
		val p = ("Song_FK_System", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeIdempotent")
		p
	}

	//    !!!   DONT TERMINATE   !!!
	"Song_FK_System" should "be associative (fk_system)" in {
		val startTime = printStartingTime("mergeAssociative")
		val p = ("Song_FK_System", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative")
		p
	}

	// WORKS   -   1:30 - 3 minutes
	"Song_FK_System" should "be associative (with assumptions) (fk_system)" in {
		val startTime = printStartingTime("mergeIsAssociative")
		val p = ("Song_FK_System", "mergeIsAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeIsAssociative")
		p
	}

	// WORKS  /   DON'T TERMINATE IN 4 REF TABLES 
	"Song_FK_System" should "be associative2 (fk_system)" in {
		val startTime = printStartingTime("mergeAssociative2")
		val p = ("Song_FK_System", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative2")
		p
	}

	// Albs/Songs (WORKS: 1-4 minutes); SongView - Dont terminate after 30 minutes
	"Song_FK_System" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatibleCommutes")
		val p = ("Song_FK_System", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatibleCommutes")
		p
	}



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
