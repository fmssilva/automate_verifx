package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class AlbumProofs extends FlatSpec with Prover { 

	////////////////////////////////////////////////////////////
	// GENERAL PROOFS OF ELEMENT:  Album  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	"Album" should "be a CvRDT" in {
		val p = ("Album", "is_a_CvRDT") 
		prove(p)
		p
	}

	"Album" should "compatible commutes" in {
		val p = ("Album", "compatibleCommutes") 
		prove(p)
		p
	}

	"Album" should "compare correct" in {
		val p = ("Album", "compareCorrect") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// PROOFS OF ELEMENT FOR UPDATABLE ATTRIBUTES - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	"Album" should "updateYear works" in {
		val p = ("Album", "Album_updateYear_works") 
		prove(p)
		p
	}

	"Album" should "updatePrice works" in {
		val p = ("Album", "Album_updatePrice_works") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// GENERAL PROOFS OF TABLE:  AlbumsTable - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	"AlbumsTable" should "be commutative" in {
		val p = ("AlbumsTable", "mergeCommutative") 
		prove(p)
		p
	}

	"AlbumsTable" should "be idempotent" in {
		val p = ("AlbumsTable", "mergeIdempotent") 
		prove(p)
		p
	}

	"AlbumsTable" should "be associative" in {
		val p = ("AlbumsTable", "mergeAssociative") 
		prove(p)
		p
	}

	"AlbumsTable" should "be associative2" in {
		val p = ("AlbumsTable", "mergeAssociative2") 
		prove(p)
		p
	}

	"AlbumsTable" should "compatible commutes" in {
		val p = ("AlbumsTable", "compatibleCommutes") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

	"Albums_FK_System" should "maintain referential integrity generic proof" in {
		val p = ("Albums_FK_System", "genericReferentialIntegrity") 
		prove(p)
		p
	}

	"Albums_FK_System" should "be commutative" in {
		val p = ("Albums_FK_System", "mergeCommutative") 
		prove(p)
		p
	}

	"Albums_FK_System" should "be idempotent" in {
		val p = ("Albums_FK_System", "mergeIdempotent") 
		prove(p)
		p
	}

	"Albums_FK_System" should "be associative" in {
		val p = ("Albums_FK_System", "mergeAssociative") 
		prove(p)
		p
	}

	"Albums_FK_System" should "be associative (with assumptions)" in {
		val p = ("Albums_FK_System", "mergeIsAssociative") 
		prove(p)
		p
	}

	"Albums_FK_System" should "be associative2" in {
		val p = ("Albums_FK_System", "mergeAssociative2") 
		prove(p)
		p
	}

	"Albums_FK_System" should "compatible commutes" in {
		val p = ("Albums_FK_System", "compatibleCommutes") 
		prove(p)
		p
	}

}
