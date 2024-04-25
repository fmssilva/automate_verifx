package antidote.tests

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class VersionVectorProperties extends FlatSpec with Prover {

  /////////////////////////////////////////
  //      VersionVector Properties      //
  ///////////////////////////////////////

  //accepted
  "VersionVector" should "sync commutative" in {
     val t = ("VersionVector", "syncCommutative")
     prove(t)
     t
   }

   //accepted
   "VersionVector" should "sync idempotent" in {
     val t = ("VersionVector", "syncIdempotent")
     prove(t)
     t
   }

   //accepted
   "VersionVector" should "sync associative" in {
     val t = ("VersionVector", "syncAssociative")
     prove(t)
     t
   }

   //accepted
   "VersionVector" should "aux1" in {
     val t = ("VersionVector", "aux1")
     prove(t)
     t
   }

   //accepted
   "VersionVector" should "aux2" in {
     val t = ("VersionVector", "aux2")
     prove(t)
     t
   }

   //accepted
   "VersionVector" should "aux3" in {
     val t = ("VersionVector", "aux3")
     prove(t)
     t
   }

   //accepted
   "VersionVector" should "aux4" in {
     val t = ("VersionVector", "aux4")
     prove(t)
     t
   }

   //accepted
   "VersionVector" should "aux5" in {
     val t = ("VersionVector", "aux5")
     prove(t)
     t
   }

}
