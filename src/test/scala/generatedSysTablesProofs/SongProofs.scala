package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class SongProofs extends FlatSpec with Prover { 

	////////////////////////////////////////////////////////////
	// GENERAL PROOFS OF ELEMENT:  Song  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	"Song" should "be a CvRDT" in {
		val p = ("Song", "is_a_CvRDT") 
		prove(p)
		p
	}

	"Song" should "compatible commutes" in {
		val p = ("Song", "compatibleCommutes") 
		prove(p)
		p
	}

	"Song" should "compare correct" in {
		val p = ("Song", "compareCorrect") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// PROOFS OF ELEMENT FOR UPDATABLE ATTRIBUTES - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	"Song" should "updateDuration works" in {
		val p = ("Song", "Song_updateDuration_works") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// GENERAL PROOFS OF TABLE:  SongsTable - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	"SongsTable" should "be commutative" in {
		val p = ("SongsTable", "mergeCommutative") 
		prove(p)
		p
	}

	"SongsTable" should "be idempotent" in {
		val p = ("SongsTable", "mergeIdempotent") 
		prove(p)
		p
	}

	"SongsTable" should "be associative" in {
		val p = ("SongsTable", "mergeAssociative") 
		prove(p)
		p
	}

	"SongsTable" should "be associative2" in {
		val p = ("SongsTable", "mergeAssociative2") 
		prove(p)
		p
	}

	"SongsTable" should "compatible commutes" in {
		val p = ("SongsTable", "compatibleCommutes") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

	"Songs_FK_References" should "maintain referential integrity generic proof" in {
		val p = ("Songs_FK_References", "genericReferentialIntegrity") 
		prove(p)
		p
	}

	"Songs_FK_References" should "be commutative" in {
		val p = ("Songs_FK_References", "mergeCommutative") 
		prove(p)
		p
	}

	"Songs_FK_References" should "be idempotent" in {
		val p = ("Songs_FK_References", "mergeIdempotent") 
		prove(p)
		p
	}

	"Songs_FK_References" should "be associative" in {
		val p = ("Songs_FK_References", "mergeAssociative") 
		prove(p)
		p
	}

	"Songs_FK_References" should "be associative (with assumptions)" in {
		val p = ("Songs_FK_References", "mergeIsAssociative") 
		prove(p)
		p
	}

	"Songs_FK_References" should "be associative2" in {
		val p = ("Songs_FK_References", "mergeAssociative2") 
		prove(p)
		p
	}

	"Songs_FK_References" should "compatible commutes" in {
		val p = ("Songs_FK_References", "compatibleCommutes") 
		prove(p)
		p
	}

}
