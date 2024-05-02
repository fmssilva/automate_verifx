package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class AlbProofs extends FlatSpec with Prover { 



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Alb" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("is_a_CvRDT")
		val p = ("Alb", "is_a_CvRDT") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "is_a_CvRDT")
		p
	}

	// WORKS  -   20-30 seconds
	"Alb" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatibleCommutes")
		val p = ("Alb", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatibleCommutes")
		p
	}

	// WORKS  -   20-30 seconds
	"Alb" should "compare correct (element)" in {
		val startTime = printStartingTime("compareCorrect")
		val p = ("Alb", "compareCorrect") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compareCorrect")
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Alb" should "updateYear works (element)" in {
		val startTime = printStartingTime("Alb_updateYear_works")
		val p = ("Alb", "Alb_updateYear_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "Alb_updateYear_works")
		p
	}

	// WORKS  -   20-30 seconds
	"Alb" should "updatePrice works (element)" in {
		val startTime = printStartingTime("Alb_updatePrice_works")
		val p = ("Alb", "Alb_updatePrice_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "Alb_updatePrice_works")
		p
	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"AlbsTable" should "be commutative (table)" in {
		val startTime = printStartingTime("mergeCommutative")
		val p = ("AlbsTable", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeCommutative")
		p
	}

	//  !!!   ABORTED  in 20-30 seconds!!!
	"AlbsTable" should "be idempotent (table)" in {
		val startTime = printStartingTime("mergeIdempotent")
		val p = ("AlbsTable", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeIdempotent")
		p
	}

	//  !!!   DON´T TERMINATE (WAITED 10 MINUTES)  !!!
	"AlbsTable" should "be associative (table)" in {
		val startTime = printStartingTime("mergeAssociative")
		val p = ("AlbsTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative")
		p
	}

	// WORKS  -   30-50 seconds - with complex cases don't terminate
	"AlbsTable" should "be associative2 (table)" in {
		val startTime = printStartingTime("mergeAssociative2")
		val p = ("AlbsTable", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative2")
		p
	}

	// WORKS   -   20-40 seconds
	"AlbsTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatibleCommutes")
		val p = ("AlbsTable", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatibleCommutes")
		p
	}



	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

	//    !!!   DONT TERMINATE   !!!
	"Alb_FK_System" should "maintain referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("genericReferentialIntegrity")
		val p = ("Alb_FK_System", "genericReferentialIntegrity") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "genericReferentialIntegrity")
		p
	}

	// WORKS   -   40 sec - 1:40 min
	"Alb_FK_System" should "be commutative (fk_system)" in {
		val startTime = printStartingTime("mergeCommutative")
		val p = ("Alb_FK_System", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeCommutative")
		p
	}

	//    Albs e SongView ABORTED in 1 - 2 minutes;  Song - WORKS - 40 seconds
	"Alb_FK_System" should "be idempotent (fk_system)" in {
		val startTime = printStartingTime("mergeIdempotent")
		val p = ("Alb_FK_System", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeIdempotent")
		p
	}

	//    !!!   DONT TERMINATE   !!!
	"Alb_FK_System" should "be associative (fk_system)" in {
		val startTime = printStartingTime("mergeAssociative")
		val p = ("Alb_FK_System", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative")
		p
	}

	// WORKS   -   1:30 - 3 minutes
	"Alb_FK_System" should "be associative (with assumptions) (fk_system)" in {
		val startTime = printStartingTime("mergeIsAssociative")
		val p = ("Alb_FK_System", "mergeIsAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeIsAssociative")
		p
	}

	// WORKS  /   DON'T TERMINATE IN 4 REF TABLES 
	"Alb_FK_System" should "be associative2 (fk_system)" in {
		val startTime = printStartingTime("mergeAssociative2")
		val p = ("Alb_FK_System", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "mergeAssociative2")
		p
	}

	// Albs/Songs (WORKS: 1-4 minutes); SongView - Dont terminate after 30 minutes
	"Alb_FK_System" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatibleCommutes")
		val p = ("Alb_FK_System", "compatibleCommutes") 
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
