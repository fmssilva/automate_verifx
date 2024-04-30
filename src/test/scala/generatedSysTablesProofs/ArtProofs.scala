package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class ArtProofs extends FlatSpec with Prover { 

	////////////////////////////////////////////////////////////
	// ELEMENT - GENERAL PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS
	"Art" should "be a CvRDT" in {
		val p = ("Art", "is_a_CvRDT") 
		prove(p)
		p
	}

	// WORKS
	"Art" should "compatible commutes" in {
		val p = ("Art", "compatibleCommutes") 
		prove(p)
		p
	}

	// WORKS
	"Art" should "compare correct" in {
		val p = ("Art", "compareCorrect") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// ELEMENT - UPDATABLE ATTRIBUTES PROOFS  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS
	"Art" should "updateAge works" in {
		val p = ("Art", "Art_updateAge_works") 
		prove(p)
		p
	}

	// WORKS
	"Art" should "updateRanking works" in {
		val p = ("Art", "Art_updateRanking_works") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// TABLE - GENERAL PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS
	"ArtsTable" should "be commutative" in {
		val p = ("ArtsTable", "mergeCommutative") 
		prove(p)
		p
	}

	//  !!!   ABORTED  !!!
	"ArtsTable" should "be idempotent" in {
		val p = ("ArtsTable", "mergeIdempotent") 
		prove(p)
		p
	}

	//  !!!   ABORTED  !!!
	"ArtsTable" should "be associative" in {
		val p = ("ArtsTable", "mergeAssociative") 
		prove(p)
		p
	}

	// WORKS
	"ArtsTable" should "be associative2" in {
		val p = ("ArtsTable", "mergeAssociative2") 
		prove(p)
		p
	}

	// WORKS
	"ArtsTable" should "compatible commutes" in {
		val p = ("ArtsTable", "compatibleCommutes") 
		prove(p)
		p
	}

}
