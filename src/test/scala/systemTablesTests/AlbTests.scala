package systemTablesTests

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class AlbTests extends FlatSpec with Prover { 

	////////////////////////////////////////////////////////////
	// GENERAL TESTS OF ELEMENT:  Alb  - specified in CvRDTProof trait 
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
	// TESTS FOR UPDATABLE ATTRIBUTES OF ELEMENT - specified in object TableName extends CvRDTProof 
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
	// GENERAL TESTS OF TABLE:  AlbsTable - specified in CvRDTProof1 trait 
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
	// FK TESTS     
	////////////////////////////////////////////////////////////

	"Albs_FK_System" should "maintain referential integrity generic test" in {
		val p = ("Albs_FK_System", "genericReferentialIntegrity") 
		prove(p)
		p
	}

	"Albs_FK_System" should "be commutative" in {
		val p = ("Albs_FK_System", "mergeCommutative") 
		prove(p)
		p
	}

	"Albs_FK_System" should "be idempotent" in {
		val p = ("Albs_FK_System", "mergeIdempotent") 
		prove(p)
		p
	}

	"Albs_FK_System" should "be associative" in {
		val p = ("Albs_FK_System", "mergeAssociative") 
		prove(p)
		p
	}

	"Albs_FK_System" should "be associative (with assumptions)" in {
		val p = ("Albs_FK_System", "mergeIsAssociative") 
		prove(p)
		p
	}

	"Albs_FK_System" should "be associative2" in {
		val p = ("Albs_FK_System", "mergeAssociative2") 
		prove(p)
		p
	}



	"Albs_FK_System" should "compatible commutes" in {
		val p = ("Albs_FK_System", "compatibleCommutes") 
		prove(p)
		p
	}

}
