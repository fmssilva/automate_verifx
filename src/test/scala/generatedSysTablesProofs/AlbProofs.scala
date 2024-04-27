package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class AlbProofs extends FlatSpec with Prover { 

	////////////////////////////////////////////////////////////
	// GENERAL PROOFS OF ELEMENT:  Alb  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	"Alb" should "be a CvRDT" in {
		val p = ("Alb", "is_a_CvRDT") 
		prove(p)
		p
	}

	"Alb" should "compatible commutes" in {
		val p = ("Alb", "compatibleCommutes") 
		prove(p)
		p
	}

	"Alb" should "compare correct" in {
		val p = ("Alb", "compareCorrect") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// PROOFS OF ELEMENT FOR UPDATABLE ATTRIBUTES - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	"Alb" should "updateYear works" in {
		val p = ("Alb", "Alb_updateYear_works") 
		prove(p)
		p
	}

	"Alb" should "updatePrice works" in {
		val p = ("Alb", "Alb_updatePrice_works") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// GENERAL PROOFS OF TABLE:  AlbsTable - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	"AlbsTable" should "be commutative" in {
		val p = ("AlbsTable", "mergeCommutative") 
		prove(p)
		p
	}

	"AlbsTable" should "be idempotent" in {
		val p = ("AlbsTable", "mergeIdempotent") 
		prove(p)
		p
	}

	"AlbsTable" should "be associative" in {
		val p = ("AlbsTable", "mergeAssociative") 
		prove(p)
		p
	}

	"AlbsTable" should "be associative2" in {
		val p = ("AlbsTable", "mergeAssociative2") 
		prove(p)
		p
	}

	"AlbsTable" should "compatible commutes" in {
		val p = ("AlbsTable", "compatibleCommutes") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

	"Albs_FK_References" should "maintain referential integrity generic proof" in {
		val p = ("Albs_FK_References", "genericReferentialIntegrity") 
		prove(p)
		p
	}

	"Albs_FK_References" should "be commutative" in {
		val p = ("Albs_FK_References", "mergeCommutative") 
		prove(p)
		p
	}

	"Albs_FK_References" should "be idempotent" in {
		val p = ("Albs_FK_References", "mergeIdempotent") 
		prove(p)
		p
	}

	"Albs_FK_References" should "be associative" in {
		val p = ("Albs_FK_References", "mergeAssociative") 
		prove(p)
		p
	}

	"Albs_FK_References" should "be associative (with assumptions)" in {
		val p = ("Albs_FK_References", "mergeIsAssociative") 
		prove(p)
		p
	}

	"Albs_FK_References" should "be associative2" in {
		val p = ("Albs_FK_References", "mergeAssociative2") 
		prove(p)
		p
	}

	"Albs_FK_References" should "compatible commutes" in {
		val p = ("Albs_FK_References", "compatibleCommutes") 
		prove(p)
		p
	}

}
