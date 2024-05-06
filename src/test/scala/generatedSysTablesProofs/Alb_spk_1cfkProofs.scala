package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class Alb_spk_1cfkProofs extends FlatSpec with Prover { 



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Alb_spk_1cfk" should "be a CvRDT (element)" in {
		val startTime = printStartingTime("be a CvRDT (element)")
		val p = ("Alb_spk_1cfk", "is_a_CvRDT") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be a CvRDT (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Alb_spk_1cfk" should "compatible commutes (element)" in {
		val startTime = printStartingTime("compatible commutes (element)")
		val p = ("Alb_spk_1cfk", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Alb_spk_1cfk" should "compare correct (element)" in {
		val startTime = printStartingTime("compare correct (element)")
		val p = ("Alb_spk_1cfk", "compareCorrect") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compare correct (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// ELEMENT PROOFS FOR UPDATABLE ATTRIBUTES  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Alb_spk_1cfk" should "updateYear works (element)" in {
		val startTime = printStartingTime("updateYear works (element)")
		val p = ("Alb_spk_1cfk", "Alb_spk_1cfk_updateYear_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "updateYear works (element)")
		p
	}

	// WORKS  -   20-30 seconds
	"Alb_spk_1cfk" should "updatePrice works (element)" in {
		val startTime = printStartingTime("updatePrice works (element)")
		val p = ("Alb_spk_1cfk", "Alb_spk_1cfk_updatePrice_works") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "updatePrice works (element)")
		p
	}



	////////////////////////////////////////////////////////////
	// TABLE PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS  -   20-30 seconds
	"Alb_spk_1cfksTable" should "be merge commutative (table)" in {
		val startTime = printStartingTime("be merge commutative (table)")
		val p = ("Alb_spk_1cfksTable", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (table)")
		p
	}

	//  !!!   ABORTED  in 20-30 seconds!!!
	"Alb_spk_1cfksTable" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("Alb_spk_1cfksTable", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	//  !!!   ABORTED   in  25 minutes(song_view)   !!!
	"Alb_spk_1cfksTable" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("Alb_spk_1cfksTable", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}

	// WORKS  -   30 seconds   -  1:30 minutes
	"Alb_spk_1cfksTable" should "be merge associative2 (table)" in {
		val startTime = printStartingTime("be merge associative2 (table)")
		val p = ("Alb_spk_1cfksTable", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (table)")
		p
	}

	// WORKS  -   20-30 seconds
	"Alb_spk_1cfksTable" should "compatible commutes (table)" in {
		val startTime = printStartingTime("compatible commutes (table)")
		val p = ("Alb_spk_1cfksTable", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (table)")
		p
	}



	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

	//    !!!   DONT TERMINATE   !!!
	"Alb_spk_1cfk_FK_System" should "keep referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("keep referential integrity generic proof (fk_system)")
		val p = ("Alb_spk_1cfk_FK_System", "genericReferentialIntegrity") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "keep referential integrity generic proof (fk_system)")
		p
	}

	// WORKS   -   40 sec - 1:40 min
	"Alb_spk_1cfk_FK_System" should "be merge commutative (fk_system)" in {
		val startTime = printStartingTime("be merge commutative (fk_system)")
		val p = ("Alb_spk_1cfk_FK_System", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (fk_system)")
		p
	}

	//    Albs e SongView ABORTED in 1 - 2 minutes;  Song - WORKS - 40 seconds
	"Alb_spk_1cfk_FK_System" should "be merge idempotent (fk_system)" in {
		val startTime = printStartingTime("be merge idempotent (fk_system)")
		val p = ("Alb_spk_1cfk_FK_System", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (fk_system)")
		p
	}

	// ABORTED: SongView in 1hour:30min, Albs in 1hour:20min
	"Alb_spk_1cfk_FK_System" should "be merge associative (fk_system)" in {
		val startTime = printStartingTime("be merge associative (fk_system)")
		val p = ("Alb_spk_1cfk_FK_System", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (fk_system)")
		p
	}

	// WORKS   -   1:30 - 3 minutes
	"Alb_spk_1cfk_FK_System" should "be merge Is associative (with assumptions) (fk_system)" in {
		val startTime = printStartingTime("be merge Is associative (with assumptions) (fk_system)")
		val p = ("Alb_spk_1cfk_FK_System", "mergeIsAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Is associative (with assumptions) (fk_system)")
		p
	}

	// WORKS  /     In 4 ref tables takes 50 minutes 
	"Alb_spk_1cfk_FK_System" should "be merge associative2 (fk_system)" in {
		val startTime = printStartingTime("be merge associative2 (fk_system)")
		val p = ("Alb_spk_1cfk_FK_System", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (fk_system)")
		p
	}

	// Albs/Songs: WORKS: 1-4 minutes;   SongView: Dont terminate after 30 minutes
	"Alb_spk_1cfk_FK_System" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatible commutes (fk_system)")
		val p = ("Alb_spk_1cfk_FK_System", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (fk_system)")
		p
	}

}
