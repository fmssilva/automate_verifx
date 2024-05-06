package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class SongProofs extends FlatSpec with Prover { 



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -  20-30 seconds
	"Song" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("be a CvRDT (element)")
		val p = ("Song", "is_a_CvRDT") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be a CvRDT (element)")
		p
	}

	// WORKS  -  20-40 seconds
	"Song" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatible commutes (element)")
		val p = ("Song", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Song" should "compare correct (element)" in {
		val startTime = printStartingTime("compare correct (element)")
		val p = ("Song", "compareCorrect") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Song" should "updateDuration works (element)" in {
		val startTime = printStartingTime("updateDuration works (element)")
		val p = ("Song", "Song_updateDuration_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "updateDuration works (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"SongsTable" should "be merge commutative (table)" in {
		val startTime = printStartingTime("be merge commutative (table)")
		val p = ("SongsTable", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (table)")
		p
	}

	//  !!!   ABORTED  in 20-30 seconds!!!
	"SongsTable" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("SongsTable", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	//  !!!   ABORTED   in  25 minutes(song_view)   !!!
	"SongsTable" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("SongsTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}

	// WORKS  -   30 seconds   -  1:30 minutes
	"SongsTable" should "be merge associative2 (table)" in {
		val startTime = printStartingTime("be merge associative2 (table)")
		val p = ("SongsTable", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (table)")
		p
	}

	// WORKS  -   20-30 seconds
	"SongsTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatible commutes (table)")
		val p = ("SongsTable", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (table)")
		p
	}



	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

	//    !!!   DONT TERMINATE   !!!
	"Song_FK_System" should "keep referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("keep referential integrity generic proof (fk_system)")
		val p = ("Song_FK_System", "genericReferentialIntegrity") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "keep referential integrity generic proof (fk_system)")
		p
	}

	// WORKS   -   40 sec - 1:40 min -> with generic PK 11 minutes
	"Song_FK_System" should "be merge commutative (fk_system)" in {
		val startTime = printStartingTime("be merge commutative (fk_system)")
		val p = ("Song_FK_System", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (fk_system)")
		p
	}

	//    Albs e SongView ABORTED in 1 - 2 minutes;  Song - WORKS - 40 seconds
	"Song_FK_System" should "be merge idempotent (fk_system)" in {
		val startTime = printStartingTime("be merge idempotent (fk_system)")
		val p = ("Song_FK_System", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (fk_system)")
		p
	}

	// ABORTED: SongView in 1hour:30min, Albs in 1hour:20min
	"Song_FK_System" should "be merge associative (fk_system)" in {
		val startTime = printStartingTime("be merge associative (fk_system)")
		val p = ("Song_FK_System", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (fk_system)")
		p
	}

	// WORKS   -   1:30 - 3 minutes
	"Song_FK_System" should "be merge Is associative (with assumptions) (fk_system)" in {
		val startTime = printStartingTime("be merge Is associative (with assumptions) (fk_system)")
		val p = ("Song_FK_System", "mergeIsAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Is associative (with assumptions) (fk_system)")
		p
	}

	// WORKS  /   In 4 ref tables takes 10 minutes 
	"Song_FK_System" should "be merge associative2 (fk_system)" in {
		val startTime = printStartingTime("be merge associative2 (fk_system)")
		val p = ("Song_FK_System", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (fk_system)")
		p
	}

	// WORKS: 1-4 minutes;
	"Song_FK_System" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatible commutes (fk_system)")
		val p = ("Song_FK_System", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (fk_system)")
		p
	}

}
