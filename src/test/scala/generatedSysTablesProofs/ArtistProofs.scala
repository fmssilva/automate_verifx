package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class ArtistProofs extends FlatSpec with Prover { 

	////////////////////////////////////////////////////////////
	// GENERAL PROOFS OF ELEMENT:  Artist  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	"Artist" should "be a CvRDT" in {
		val p = ("Artist", "is_a_CvRDT") 
		prove(p)
		p
	}

	"Artist" should "compatible commutes" in {
		val p = ("Artist", "compatibleCommutes") 
		prove(p)
		p
	}

	"Artist" should "compare correct" in {
		val p = ("Artist", "compareCorrect") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// PROOFS OF ELEMENT FOR UPDATABLE ATTRIBUTES - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	////////////////////////////////////////////////////////////
	// GENERAL PROOFS OF TABLE:  ArtistsTable - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	"ArtistsTable" should "be commutative" in {
		val p = ("ArtistsTable", "mergeCommutative") 
		prove(p)
		p
	}

	"ArtistsTable" should "be idempotent" in {
		val p = ("ArtistsTable", "mergeIdempotent") 
		prove(p)
		p
	}

	"ArtistsTable" should "be associative" in {
		val p = ("ArtistsTable", "mergeAssociative") 
		prove(p)
		p
	}

	"ArtistsTable" should "be associative2" in {
		val p = ("ArtistsTable", "mergeAssociative2") 
		prove(p)
		p
	}

	"ArtistsTable" should "compatible commutes" in {
		val p = ("ArtistsTable", "compatibleCommutes") 
		prove(p)
		p
	}

}
