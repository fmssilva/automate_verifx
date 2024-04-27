package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class ArtProofs extends FlatSpec with Prover { 

	////////////////////////////////////////////////////////////
	// GENERAL PROOFS OF ELEMENT:  Art  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	"Art" should "be a CvRDT" in {
		val p = ("Art", "is_a_CvRDT") 
		prove(p)
		p
	}

	"Art" should "compatible commutes" in {
		val p = ("Art", "compatibleCommutes") 
		prove(p)
		p
	}

	"Art" should "compare correct" in {
		val p = ("Art", "compareCorrect") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// PROOFS OF ELEMENT FOR UPDATABLE ATTRIBUTES - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////
	// GENERAL PROOFS OF TABLE:  ArtsTable - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	"ArtsTable" should "be commutative" in {
		val p = ("ArtsTable", "mergeCommutative") 
		prove(p)
		p
	}

	"ArtsTable" should "be idempotent" in {
		val p = ("ArtsTable", "mergeIdempotent") 
		prove(p)
		p
	}

	"ArtsTable" should "be associative" in {
		val p = ("ArtsTable", "mergeAssociative") 
		prove(p)
		p
	}

	"ArtsTable" should "be associative2" in {
		val p = ("ArtsTable", "mergeAssociative2") 
		prove(p)
		p
	}

	"ArtsTable" should "compatible commutes" in {
		val p = ("ArtsTable", "compatibleCommutes") 
		prove(p)
		p
	}

}
