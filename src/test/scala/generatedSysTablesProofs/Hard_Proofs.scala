package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec
import generatedSysTablesProofs.ProverUtils._

class Hard_Proofs extends FlatSpec with Prover { 



	////////////////////////////////////////////////////////////
	// be merge idempotent (table)   !!!   ABORTED  in 20-30 seconds!!! 
	////////////////////////////////////////////////////////////

	// Art
	"Art" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("Art", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	// Genre
	"Genre" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("Genre", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	// Song
	"Song" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("Song", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	// Alb
	"Alb" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("Alb", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}

	// Song_view
	"Song_view" should "be merge idempotent (table)" in {
		val startTime = printStartingTime("be merge idempotent (table)")
		val p = ("Song_view", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (table)")
		p
	}



	////////////////////////////////////////////////////////////
	// be merge associative (table)   !!!   ABORTED   in  25 minutes(song_view)   !!! 
	////////////////////////////////////////////////////////////

	// Art
	"Art" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("Art", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}

	// Genre
	"Genre" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("Genre", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}

	// Song
	"Song" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("Song", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}

	// Alb
	"Alb" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("Alb", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}

	// Song_view
	"Song_view" should "be merge associative (table)" in {
		val startTime = printStartingTime("be merge associative (table)")
		val p = ("Song_view", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (table)")
		p
	}



	////////////////////////////////////////////////////////////
	// be merge associative2 (table)  WORKS  -   30 seconds   -  1:30 minutes 
	////////////////////////////////////////////////////////////

	// Art
	"Art" should "be merge associative2 (table)" in {
		val startTime = printStartingTime("be merge associative2 (table)")
		val p = ("Art", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (table)")
		p
	}

	// Genre
	"Genre" should "be merge associative2 (table)" in {
		val startTime = printStartingTime("be merge associative2 (table)")
		val p = ("Genre", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (table)")
		p
	}

	// Song
	"Song" should "be merge associative2 (table)" in {
		val startTime = printStartingTime("be merge associative2 (table)")
		val p = ("Song", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (table)")
		p
	}

	// Alb
	"Alb" should "be merge associative2 (table)" in {
		val startTime = printStartingTime("be merge associative2 (table)")
		val p = ("Alb", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (table)")
		p
	}

	// Song_view
	"Song_view" should "be merge associative2 (table)" in {
		val startTime = printStartingTime("be merge associative2 (table)")
		val p = ("Song_view", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (table)")
		p
	}



	////////////////////////////////////////////////////////////
	// keep referential integrity generic proof (fk_system)     !!!   DONT TERMINATE   !!! 
	////////////////////////////////////////////////////////////

	// Art
	"Art" should "keep referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("keep referential integrity generic proof (fk_system)")
		val p = ("Art", "genericReferentialIntegrity") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "keep referential integrity generic proof (fk_system)")
		p
	}

	// Genre
	"Genre" should "keep referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("keep referential integrity generic proof (fk_system)")
		val p = ("Genre", "genericReferentialIntegrity") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "keep referential integrity generic proof (fk_system)")
		p
	}

	// Song
	"Song" should "keep referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("keep referential integrity generic proof (fk_system)")
		val p = ("Song", "genericReferentialIntegrity") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "keep referential integrity generic proof (fk_system)")
		p
	}

	// Alb
	"Alb" should "keep referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("keep referential integrity generic proof (fk_system)")
		val p = ("Alb", "genericReferentialIntegrity") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "keep referential integrity generic proof (fk_system)")
		p
	}

	// Song_view
	"Song_view" should "keep referential integrity generic proof (fk_system)" in {
		val startTime = printStartingTime("keep referential integrity generic proof (fk_system)")
		val p = ("Song_view", "genericReferentialIntegrity") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "keep referential integrity generic proof (fk_system)")
		p
	}



	////////////////////////////////////////////////////////////
	// be merge commutative (fk_system)  WORKS   -   40 sec - 1:40 min -> with generic PK 11 minutes 
	////////////////////////////////////////////////////////////

	// Art
	"Art" should "be merge commutative (fk_system)" in {
		val startTime = printStartingTime("be merge commutative (fk_system)")
		val p = ("Art", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (fk_system)")
		p
	}

	// Genre
	"Genre" should "be merge commutative (fk_system)" in {
		val startTime = printStartingTime("be merge commutative (fk_system)")
		val p = ("Genre", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (fk_system)")
		p
	}

	// Song
	"Song" should "be merge commutative (fk_system)" in {
		val startTime = printStartingTime("be merge commutative (fk_system)")
		val p = ("Song", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (fk_system)")
		p
	}

	// Alb
	"Alb" should "be merge commutative (fk_system)" in {
		val startTime = printStartingTime("be merge commutative (fk_system)")
		val p = ("Alb", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (fk_system)")
		p
	}

	// Song_view
	"Song_view" should "be merge commutative (fk_system)" in {
		val startTime = printStartingTime("be merge commutative (fk_system)")
		val p = ("Song_view", "mergeCommutative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge commutative (fk_system)")
		p
	}



	////////////////////////////////////////////////////////////
	// be merge idempotent (fk_system)     Albs e SongView ABORTED in 1 - 2 minutes;  Song - WORKS - 40 seconds 
	////////////////////////////////////////////////////////////

	// Art
	"Art" should "be merge idempotent (fk_system)" in {
		val startTime = printStartingTime("be merge idempotent (fk_system)")
		val p = ("Art", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (fk_system)")
		p
	}

	// Genre
	"Genre" should "be merge idempotent (fk_system)" in {
		val startTime = printStartingTime("be merge idempotent (fk_system)")
		val p = ("Genre", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (fk_system)")
		p
	}

	// Song
	"Song" should "be merge idempotent (fk_system)" in {
		val startTime = printStartingTime("be merge idempotent (fk_system)")
		val p = ("Song", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (fk_system)")
		p
	}

	// Alb
	"Alb" should "be merge idempotent (fk_system)" in {
		val startTime = printStartingTime("be merge idempotent (fk_system)")
		val p = ("Alb", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (fk_system)")
		p
	}

	// Song_view
	"Song_view" should "be merge idempotent (fk_system)" in {
		val startTime = printStartingTime("be merge idempotent (fk_system)")
		val p = ("Song_view", "mergeIdempotent") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge idempotent (fk_system)")
		p
	}



	////////////////////////////////////////////////////////////
	// be merge associative (fk_system)  ABORTED: SongView in 1hour:30min, Albs in 1hour:20min 
	////////////////////////////////////////////////////////////

	// Art
	"Art" should "be merge associative (fk_system)" in {
		val startTime = printStartingTime("be merge associative (fk_system)")
		val p = ("Art", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (fk_system)")
		p
	}

	// Genre
	"Genre" should "be merge associative (fk_system)" in {
		val startTime = printStartingTime("be merge associative (fk_system)")
		val p = ("Genre", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (fk_system)")
		p
	}

	// Song
	"Song" should "be merge associative (fk_system)" in {
		val startTime = printStartingTime("be merge associative (fk_system)")
		val p = ("Song", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (fk_system)")
		p
	}

	// Alb
	"Alb" should "be merge associative (fk_system)" in {
		val startTime = printStartingTime("be merge associative (fk_system)")
		val p = ("Alb", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (fk_system)")
		p
	}

	// Song_view
	"Song_view" should "be merge associative (fk_system)" in {
		val startTime = printStartingTime("be merge associative (fk_system)")
		val p = ("Song_view", "mergeAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative (fk_system)")
		p
	}



	////////////////////////////////////////////////////////////
	// be merge Is associative (with assumptions) (fk_system)  WORKS   -   1:30 - 3 minutes 
	////////////////////////////////////////////////////////////

	// Art
	"Art" should "be merge Is associative (with assumptions) (fk_system)" in {
		val startTime = printStartingTime("be merge Is associative (with assumptions) (fk_system)")
		val p = ("Art", "mergeIsAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Is associative (with assumptions) (fk_system)")
		p
	}

	// Genre
	"Genre" should "be merge Is associative (with assumptions) (fk_system)" in {
		val startTime = printStartingTime("be merge Is associative (with assumptions) (fk_system)")
		val p = ("Genre", "mergeIsAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Is associative (with assumptions) (fk_system)")
		p
	}

	// Song
	"Song" should "be merge Is associative (with assumptions) (fk_system)" in {
		val startTime = printStartingTime("be merge Is associative (with assumptions) (fk_system)")
		val p = ("Song", "mergeIsAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Is associative (with assumptions) (fk_system)")
		p
	}

	// Alb
	"Alb" should "be merge Is associative (with assumptions) (fk_system)" in {
		val startTime = printStartingTime("be merge Is associative (with assumptions) (fk_system)")
		val p = ("Alb", "mergeIsAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Is associative (with assumptions) (fk_system)")
		p
	}

	// Song_view
	"Song_view" should "be merge Is associative (with assumptions) (fk_system)" in {
		val startTime = printStartingTime("be merge Is associative (with assumptions) (fk_system)")
		val p = ("Song_view", "mergeIsAssociative") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge Is associative (with assumptions) (fk_system)")
		p
	}



	////////////////////////////////////////////////////////////
	// be merge associative2 (fk_system)  WORKS  /   In 4 ref tables takes 10 minutes  
	////////////////////////////////////////////////////////////

	// Art
	"Art" should "be merge associative2 (fk_system)" in {
		val startTime = printStartingTime("be merge associative2 (fk_system)")
		val p = ("Art", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (fk_system)")
		p
	}

	// Genre
	"Genre" should "be merge associative2 (fk_system)" in {
		val startTime = printStartingTime("be merge associative2 (fk_system)")
		val p = ("Genre", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (fk_system)")
		p
	}

	// Song
	"Song" should "be merge associative2 (fk_system)" in {
		val startTime = printStartingTime("be merge associative2 (fk_system)")
		val p = ("Song", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (fk_system)")
		p
	}

	// Alb
	"Alb" should "be merge associative2 (fk_system)" in {
		val startTime = printStartingTime("be merge associative2 (fk_system)")
		val p = ("Alb", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (fk_system)")
		p
	}

	// Song_view
	"Song_view" should "be merge associative2 (fk_system)" in {
		val startTime = printStartingTime("be merge associative2 (fk_system)")
		val p = ("Song_view", "mergeAssociative2") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "be merge associative2 (fk_system)")
		p
	}



	////////////////////////////////////////////////////////////
	// compatible commutes (fk_system)  WORKS: 1-4 minutes; 
	////////////////////////////////////////////////////////////

	// Art
	"Art" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatible commutes (fk_system)")
		val p = ("Art", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (fk_system)")
		p
	}

	// Genre
	"Genre" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatible commutes (fk_system)")
		val p = ("Genre", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (fk_system)")
		p
	}

	// Song
	"Song" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatible commutes (fk_system)")
		val p = ("Song", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (fk_system)")
		p
	}

	// Alb
	"Alb" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatible commutes (fk_system)")
		val p = ("Alb", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (fk_system)")
		p
	}

	// Song_view
	"Song_view" should "compatible commutes (fk_system)" in {
		val startTime = printStartingTime("compatible commutes (fk_system)")
		val p = ("Song_view", "compatibleCommutes") 
		prove(p)
		printProofTime(startTime, System.nanoTime(), "compatible commutes (fk_system)")
		p
	}

}
