package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class GenreProofs extends FlatSpec with Prover { 

	////////////////////////////////////////////////////////////
	// ELEMENT - GENERAL PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS
	"Genre" should "be a CvRDT" in {
		val p = ("Genre", "is_a_CvRDT") 
		prove(p)
		p
	}

	// WORKS
	"Genre" should "compatible commutes" in {
		val p = ("Genre", "compatibleCommutes") 
		prove(p)
		p
	}

	// WORKS
	"Genre" should "compare correct" in {
		val p = ("Genre", "compareCorrect") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// ELEMENT - UPDATABLE ATTRIBUTES PROOFS  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS
	"Genre" should "updateDescription works" in {
		val p = ("Genre", "Genre_updateDescription_works") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// TABLE - GENERAL PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS
	"GenresTable" should "be commutative" in {
		val p = ("GenresTable", "mergeCommutative") 
		prove(p)
		p
	}

	//  !!!   ABORTED  !!!
	"GenresTable" should "be idempotent" in {
		val p = ("GenresTable", "mergeIdempotent") 
		prove(p)
		p
	}

	//  !!!   ABORTED  !!!
	"GenresTable" should "be associative" in {
		val p = ("GenresTable", "mergeAssociative") 
		prove(p)
		p
	}

	// WORKS
	"GenresTable" should "be associative2" in {
		val p = ("GenresTable", "mergeAssociative2") 
		prove(p)
		p
	}

	// WORKS
	"GenresTable" should "compatible commutes" in {
		val p = ("GenresTable", "compatibleCommutes") 
		prove(p)
		p
	}

}
