package antidote.tests

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class CRDTsTests extends FlatSpec with Prover {

  /////////////////////////////////////
  //      Bounded Counter GEQ       //
  ///////////////////////////////////

  //accepted
  "BCounterGeq" should "is commutative" in {
    val ctr = ("BCounterGeqProofs", "mergeCommutative")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterGeq" should "is idempotent" in {
    val ctr = ("BCounterGeqProofs", "mergeIdempotent")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterGeq" should "is associative" in {
    val ctr = ("BCounterGeqProofs", "mergeAssociative")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterGeq" should "compatible commutes" in {
    val ctr = ("BCounterGeqProofs", "compatibleCommutes")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterGeq" should "hold the invariant under merge" in {
    val ctr = ("BCounterGeqProofs", "BCounterGeq_merge_holds_invariant")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterGeq" should "hold the invariant under concurrent increments" in {
    val ctr = ("BCounterGeqProofs", "BCounterGeq_concurrent_incs")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterGeq" should "hold the invariant under concurrent decrements" in {
    val ctr = ("BCounterGeqProofs", "BCounterGeq_concurrent_decs")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterGeq" should "hold the invariant under concurrent decrements and increments" in {
    val ctr = ("BCounterGeqProofs", "BCounterGeq_concurrent_decs_incs")
    val res = prove(ctr)
    res
  }

  /////////////////////////////////////
  //      Bounded Counter LEQ       //
  ///////////////////////////////////

  //accepted
  "BCounterLeq" should "is commutative" in {
    val ctr = ("BCounterLeqProofs", "mergeCommutative")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterLeq" should "is idempotent" in {
    val ctr = ("BCounterLeqProofs", "mergeIdempotent")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterLeq" should "is associative" in {
    val ctr = ("BCounterLeqProofs", "mergeAssociative")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterLeq" should "compatible commutes" in {
    val ctr = ("BCounterLeqProofs", "compatibleCommutes")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterLeq" should "hold the invariant under merge" in {
    val ctr = ("BCounterLeqProofs", "BCounterLeq_holds_invariant")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterLeq" should "hold the invariant under concurrent increments" in {
    val ctr = ("BCounterLeqProofs", "BCounterLeq_concurrent_incs")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterLeq" should "hold the invariant under concurrent decrements" in {
    val ctr = ("BCounterLeqProofs", "BCounterLeq_concurrent_decs")
    val res = prove(ctr)
    res
  }

  //accepted
  "BCounterLeq" should "hold the invariant under concurrent decrements and increments" in {
    val ctr = ("BCounterLeqProofs", "BCounterLeq_concurrent_decs_incs")
    val res = prove(ctr)
    res
  }


  /////////////////////////////////////////
  // State-based increment-only Counter //
  ///////////////////////////////////////

  //accepted
  "GCounter" should "be a CvRDT" in {
    val ctr = ("GCounter", "is_a_CvRDT")
    val res = prove(ctr)
    res
  }

  //accepted
  "GCounter" should "compatible commutes" in {
    val proof = ("GCounter", "compatibleCommutes")
    prove(proof)
    proof
  }

  /////////////////////////////////////
  // Positive-Negative Counter CRDT //
  ///////////////////////////////////

  //accepted
  "PNCounter" should "be a CvRDT" in {
    val proof = ("PNCounter", "is_a_CvRDT")
    prove(proof)
    proof
  }

  //accepted
  "PNCounter" should "compatible commutes" in {
    val proof = ("PNCounter", "compatibleCommutes")
    prove(proof)
    proof
  }

  //accepted
  "PNCounter2" should "be a CvRDT" in {
    val pnCounter = ("PNCounter2", "is_a_CvRDT")
    prove(pnCounter)
  }


  //////////////////////////////
  //         EWFlag          //
  ////////////////////////////

  //accepted
  "EWFlag" should "be a CvRDT" in {
    val ewFlag = ("EWFlag", "is_a_CvRDT")
    prove(ewFlag)
    ewFlag
  }

  //accepted
  "EWFlag" should "compatible commutes" in {
    val ewFlag = ("EWFlag", "compatibleCommutes")
    prove(ewFlag)
    ewFlag
  }

  //accepted
  "EWFlag" should "work" in {
    val ewFlag = ("EWFlag", "EWFlag_works")
    prove(ewFlag)
    ewFlag
  }

  //////////////////////////////////////
  // First-Writer-Wins Register CRDT //
  ////////////////////////////////////

  //accepted
  "FWWRegister" should "be a CvRDT" in {
    val fwwReg = ("FWWRegister", "is_a_CvRDT")
    prove(fwwReg)
    fwwReg
  }

  //accepted
  "FWWRegister" should "compare correct" in {
    val ewFlag = ("FWWRegister", "compareCorrect")
    prove(ewFlag)
    ewFlag
  }

  //accepted
  "FWWRegister" should "compatible commutes" in {
    val ewFlag = ("FWWRegister", "compatibleCommutes")
    prove(ewFlag)
    ewFlag
  }

  /////////////////////////////////////
  // Last-Writer-Wins Register CRDT //
  ///////////////////////////////////

  //accepted
  "LWWRegister" should "be a CvRDT" in {
    val lwwReg = ("LWWRegister", "is_a_CvRDT")
    prove(lwwReg)
    lwwReg
  }

  //accepted
  "LWWRegister" should "compare correct" in {
    val lwwReg = ("LWWRegister", "compareCorrect")
    prove(lwwReg)
    lwwReg
  }

  //accepted
  "LWWRegister" should "compatible commutes" in {
    val lwwReg = ("LWWRegister", "compatibleCommutes")
    prove(lwwReg)
    lwwReg
  }

  ////////////////////////////////
  // Multi-Value Register CRDT //
  //////////////////////////////

  //accepted
  "MVRegister" should "be a CvRDT" in {
    val reg = ("MVRegister", "is_a_CvRDT")
    prove(reg)
    reg
  }

  //accepted
  "MVRegister" should "compatible commutes" in {
    val reg = ("MVRegister", "compatibleCommutes")
    prove(reg)
    reg
  }

}
