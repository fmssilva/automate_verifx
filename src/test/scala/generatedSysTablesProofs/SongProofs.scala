package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class SongProofs extends FlatSpec with Prover { 

	////////////////////////////////////////////////////////////
	// ELEMENT - GENERAL PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS
	"Song" should "be a CvRDT" in {
		val p = ("Song", "is_a_CvRDT") 
		prove(p)
		p
	}

	// WORKS
	"Song" should "compatible commutes" in {
		val p = ("Song", "compatibleCommutes") 
		prove(p)
		p
	}

	// WORKS
	"Song" should "compare correct" in {
		val p = ("Song", "compareCorrect") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// ELEMENT - UPDATABLE ATTRIBUTES PROOFS  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS
	"Song" should "updateDuration works" in {
		val p = ("Song", "Song_updateDuration_works") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// TABLE - GENERAL PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS
	"SongsTable" should "be commutative" in {
		val p = ("SongsTable", "mergeCommutative") 
		prove(p)
		p
	}

	//  !!!   ABORTED  !!!
	"SongsTable" should "be idempotent" in {
		val p = ("SongsTable", "mergeIdempotent") 
		prove(p)
		p
	}

	//  !!!   ABORTED  !!!
	"SongsTable" should "be associative" in {
		val p = ("SongsTable", "mergeAssociative") 
		prove(p)
		p
	}

	// WORKS
	"SongsTable" should "be associative2" in {
		val p = ("SongsTable", "mergeAssociative2") 
		prove(p)
		p
	}

	// WORKS
	"SongsTable" should "compatible commutes" in {
		val p = ("SongsTable", "compatibleCommutes") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

	//    !!!   ABORTED   !!!
	"Song_FK_System" should "maintain referential integrity generic proof" in {
		val p = ("Song_FK_System", "genericReferentialIntegrity") 
		prove(p)
		p
	}

	// WORKS
	"Song_FK_System" should "be commutative" in {
		val p = ("Song_FK_System", "mergeCommutative") 
		prove(p)
		p
	}

	//    !!!   ABORTED   !!!
	"Song_FK_System" should "be idempotent" in {
		val p = ("Song_FK_System", "mergeIdempotent") 
		prove(p)
		p
	}

	//    !!!   ABORTED   !!!
	"Song_FK_System" should "be associative" in {
		val p = ("Song_FK_System", "mergeAssociative") 
		prove(p)
		p
	}

	// WORKS
	"Song_FK_System" should "be associative (with assumptions)" in {
		val p = ("Song_FK_System", "mergeIsAssociative") 
		prove(p)
		p
	}

	// WORKS
	"Song_FK_System" should "be associative2" in {
		val p = ("Song_FK_System", "mergeAssociative2") 
		prove(p)
		p
	}

	// WORKS
	"Song_FK_System" should "compatible commutes" in {
		val p = ("Song_FK_System", "compatibleCommutes") 
		prove(p)
		p
	}

}
