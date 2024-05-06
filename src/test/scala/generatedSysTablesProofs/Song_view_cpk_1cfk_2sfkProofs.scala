package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class Song_view_cpk_1cfk_2sfkProofs extends FlatSpec with Prover { 



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Song_view_cpk_1cfk_2sfk" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("be a CvRDT (element)")
		val p = ("Song_view_cpk_1cfk_2sfk", "is_a_CvRDT") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be a CvRDT (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Song_view_cpk_1cfk_2sfk" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatible commutes (element)")
		val p = ("Song_view_cpk_1cfk_2sfk", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Song_view_cpk_1cfk_2sfk" should "compare correct (element)" in {
		val startTime = printStartingTime("compare correct (element)")
		val p = ("Song_view_cpk_1cfk_2sfk", "compareCorrect") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Song_view_cpk_1cfk_2sfk" should "updateDuration works (element)" in {
		val startTime = printStartingTime("updateDuration works (element)")
		val p = ("Song_view_cpk_1cfk_2sfk", "Song_view_cpk_1cfk_2sfk_updateDuration_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "updateDuration works (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Song_view_cpk_1cfk_2sfksTable" should "be merge commutative (table)" in {
		val startTime = printStartingTime("be merge commutative (table)")
		val p = ("Song_view_cpk_1cfk_2sfksTable", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (table)")
		p
	}

	//  !!!   ABORTED  in 20-30 seconds!!!
	"Song_view_cpk_1cfk_2sfksTable" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("Song_view_cpk_1cfk_2sfksTable", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	//  !!!   ABORTED   in  25 minutes(song_view)   !!!
	"Song_view_cpk_1cfk_2sfksTable" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("Song_view_cpk_1cfk_2sfksTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}

	// WORKS  -   30 seconds   -  1:30 minutes
	"Song_view_cpk_1cfk_2sfksTable" should "be merge associative2 (table)" in {
		val startTime = printStartingTime("be merge associative2 (table)")
		val p = ("Song_view_cpk_1cfk_2sfksTable", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (table)")
		p
	}

	// WORKS  -   20-30 seconds
	"Song_view_cpk_1cfk_2sfksTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatible commutes (table)")
		val p = ("Song_view_cpk_1cfk_2sfksTable", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (table)")
		p
	}



	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

	//    !!!   DONT TERMINATE   !!!
	"Song_view_cpk_1cfk_2sfk_FK_System" should "keep referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("keep referential integrity generic proof (fk_system)")
		val p = ("Song_view_cpk_1cfk_2sfk_FK_System", "genericReferentialIntegrity") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "keep referential integrity generic proof (fk_system)")
		p
	}

	// WORKS   -   40 sec - 1:40 min
	"Song_view_cpk_1cfk_2sfk_FK_System" should "be merge commutative (fk_system)" in {
		val startTime = printStartingTime("be merge commutative (fk_system)")
		val p = ("Song_view_cpk_1cfk_2sfk_FK_System", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (fk_system)")
		p
	}

	//    Albs e SongView ABORTED in 1 - 2 minutes;  Song - WORKS - 40 seconds
	"Song_view_cpk_1cfk_2sfk_FK_System" should "be merge idempotent (fk_system)" in {
		val startTime = printStartingTime("be merge idempotent (fk_system)")
		val p = ("Song_view_cpk_1cfk_2sfk_FK_System", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (fk_system)")
		p
	}

	// ABORTED: SongView in 1hour:30min, Albs in 1hour:20min
	"Song_view_cpk_1cfk_2sfk_FK_System" should "be merge associative (fk_system)" in {
		val startTime = printStartingTime("be merge associative (fk_system)")
		val p = ("Song_view_cpk_1cfk_2sfk_FK_System", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (fk_system)")
		p
	}

	// WORKS   -   1:30 - 3 minutes
	"Song_view_cpk_1cfk_2sfk_FK_System" should "be merge Is associative (with assumptions) (fk_system)" in {
		val startTime = printStartingTime("be merge Is associative (with assumptions) (fk_system)")
		val p = ("Song_view_cpk_1cfk_2sfk_FK_System", "mergeIsAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Is associative (with assumptions) (fk_system)")
		p
	}

	// WORKS  /     In 4 ref tables takes 50 minutes 
	"Song_view_cpk_1cfk_2sfk_FK_System" should "be merge associative2 (fk_system)" in {
		val startTime = printStartingTime("be merge associative2 (fk_system)")
		val p = ("Song_view_cpk_1cfk_2sfk_FK_System", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (fk_system)")
		p
	}

	// Albs/Songs: WORKS: 1-4 minutes;   SongView: Dont terminate after 30 minutes
	"Song_view_cpk_1cfk_2sfk_FK_System" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatible commutes (fk_system)")
		val p = ("Song_view_cpk_1cfk_2sfk_FK_System", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (fk_system)")
		p
	}

}
