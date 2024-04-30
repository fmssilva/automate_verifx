package generatedSysTablesProofs

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class AlbProofs extends FlatSpec with Prover { 

	////////////////////////////////////////////////////////////
	// ELEMENT - GENERAL PROOFS  - specified in CvRDTProof trait 
	////////////////////////////////////////////////////////////

	// WORKS
	"Alb" should "be a CvRDT" in {
		val p = ("Alb", "is_a_CvRDT") 
		prove(p)
		p
	}

	// WORKS
	"Alb" should "compatible commutes" in {
		val p = ("Alb", "compatibleCommutes") 
		prove(p)
		p
	}

	// WORKS
	"Alb" should "compare correct" in {
		val p = ("Alb", "compareCorrect") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// ELEMENT - UPDATABLE ATTRIBUTES PROOFS  - specified in object TableName extends CvRDTProof 
	////////////////////////////////////////////////////////////

	// WORKS
	"Alb" should "updateYear works" in {
		val p = ("Alb", "Alb_updateYear_works") 
		prove(p)
		p
	}

	// WORKS
	"Alb" should "updatePrice works" in {
		val p = ("Alb", "Alb_updatePrice_works") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// TABLE - GENERAL PROOFS  - specified in CvRDTProof1 trait 
	////////////////////////////////////////////////////////////

	// WORKS
	"AlbsTable" should "be commutative" in {
		val p = ("AlbsTable", "mergeCommutative") 
		prove(p)
		p
	}

	//  !!!   ABORTED  !!!
	"AlbsTable" should "be idempotent" in {
		val p = ("AlbsTable", "mergeIdempotent") 
		prove(p)
		p
	}

	//  !!!   ABORTED  !!!
	"AlbsTable" should "be associative" in {
		val p = ("AlbsTable", "mergeAssociative") 
		prove(p)
		p
	}

	// WORKS
	"AlbsTable" should "be associative2" in {
		val p = ("AlbsTable", "mergeAssociative2") 
		prove(p)
		p
	}

	// WORKS
	"AlbsTable" should "compatible commutes" in {
		val p = ("AlbsTable", "compatibleCommutes") 
		prove(p)
		p
	}

	////////////////////////////////////////////////////////////
	// FK REFERENTIAL PROOFS     
	////////////////////////////////////////////////////////////

	//    !!!   ABORTED   !!!
	"Alb_FK_System" should "maintain referential integrity generic proof" in {
		val p = ("Alb_FK_System", "genericReferentialIntegrity") 
		prove(p)
		p
	}

	// WORKS
	"Alb_FK_System" should "be commutative" in {
		val p = ("Alb_FK_System", "mergeCommutative") 
		prove(p)
		p
	}

	//    !!!   ABORTED   !!!
	"Alb_FK_System" should "be idempotent" in {
		val p = ("Alb_FK_System", "mergeIdempotent") 
		prove(p)
		p
	}

	//    !!!   ABORTED   !!!
	"Alb_FK_System" should "be associative" in {
		val p = ("Alb_FK_System", "mergeAssociative") 
		prove(p)
		p
	}

	// WORKS
	"Alb_FK_System" should "be associative (with assumptions)" in {
		val p = ("Alb_FK_System", "mergeIsAssociative") 
		prove(p)
		p
	}

	// WORKS
	"Alb_FK_System" should "be associative2" in {
		val p = ("Alb_FK_System", "mergeAssociative2") 
		prove(p)
		p
	}

	// WORKS
	"Alb_FK_System" should "compatible commutes" in {
		val p = ("Alb_FK_System", "compatibleCommutes") 
		prove(p)
		p
	}

}
