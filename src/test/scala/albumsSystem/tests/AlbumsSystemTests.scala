package albumsSystem.tests

import be.vub.kdeporre.crdtproofs.Prover
import org.scalatest.FlatSpec

class AlbumsSystemTests extends FlatSpec with Prover {

  //////////////////////
  //      Album      //
  ////////////////////

  //accepted
  "Album" should "be a CvRDT" in {
    val p = ("Album", "is_a_CvRDT")
    prove(p)
    p
  }

  //accepted
  "Album" should "compatible commutes" in {
    val a = ("Album", "compatibleCommutes")
    prove(a)
    a
  }

  //accepted
  "Album" should "compare correct" in {
    val a = ("Album", "compareCorrect")
    prove(a)
    a
  }

  //accepted
  "Album" should "updateCountry works" in {
    val p = ("Album", "Album_updateYear_works")
    prove(p)
    p
  }

  ////////////////////////////////
  //        AlbumsTable        //
  //////////////////////////////

  //accepted
  "AlbumsTable" should "be commutative" in {
    val t = ("AlbumsTable", "mergeCommutative")
    prove(t)
    t
  }

  //accepted
  "AlbumsTable" should "be idempotent" in {
    val t = ("AlbumsTable", "mergeIdempotent")
    prove(t)
    t
  }

  //accepted
  "AlbumsTable" should "be associative" in {
    val t = ("AlbumsTable", "mergeAssociative")
    prove(t)
    t
  }

  //accepted
  "AlbumsTable" should "be associative2" in {
    val t = ("AlbumsTable", "mergeAssociative2")
    prove(t)
    t
  }

  //accepted
  "AlbumsTable" should "compatible commutes" in {
    val t = ("AlbumsTable", "compatibleCommutes")
    prove(t)
    t
  }


  ///////////////////////////
  //        Artist        //
  /////////////////////////

  //accepted
  "Artist" should "be a CvRDT" in {
    val a = ("Artist", "is_a_CvRDT")
    prove(a)
    a
  }

  //accepted
  "Artist" should "compatible commutes" in {
    val a = ("Artist", "compatibleCommutes")
    prove(a)
    a
  }

  //accepted
  "Artist" should "compare correct" in {
    val a = ("Artist", "compareCorrect")
    prove(a)
    a
  }

  //accepted
  "Artist" should "updateCountry works" in {
    val a = ("Artist", "Artist_updateCountry_works")
    prove(a)
    a
  }


  /////////////////////////////////
  //        ArtistsTable        //
  ///////////////////////////////

  //accepted
  "ArtistsTable" should "be commutative" in {
    val t = ("ArtistsTable", "mergeCommutative")
    prove(t)
    t
  }

  //accepted
  "ArtistsTable" should "be idempotent" in {
    val t = ("ArtistsTable", "mergeIdempotent")
    prove(t)
    t
  }

  //accepted
  "ArtistsTable" should "be associative" in {
    val t = ("ArtistsTable", "mergeAssociative")
    prove(t)
    t
  }

  //accepted
  "ArtistsTable" should "be associative2" in {
    val t = ("ArtistsTable", "mergeAssociative2")
    prove(t)
    t
  }

  //accepted
  "ArtistsTable" should "compatible commutes" in {
    val t = ("ArtistsTable", "compatibleCommutes")
    prove(t)
    t
  }

  /////////////////////////////////
  //        AlbumsSystem        //
  ///////////////////////////////

  //accepted
  "AlbumsSystem" should "maintain referential integrity generic test" in {
    val t = ("AlbumsSystem", "genericReferentialIntegrity")
    prove(t)
    t
  }

  //accepted
  "AlbumsSystem" should "be commutative" in {
    val t = ("AlbumsSystem", "mergeCommutative")
    prove(t)
    t
  }

  //accepted
  "AlbumsSystem" should "be idempotent" in {
    val t = ("AlbumsSystem", "mergeIdempotent")
    prove(t)
    t
  }

  //aborted
  "AlbumsSystem" should "be associative" in {
    val t = ("AlbumsSystem", "mergeAssociative")
    prove(t)
    t
  }

  //accepted
  "AlbumsSystem" should "be associative (with assumptions)" in {
    val t = ("AlbumsSystem", "mergeIsAssociative")
    prove(t)
    t
  }

  //accepted
  "AlbumsSystem" should "be associative2" in {
    val t = ("AlbumsSystem", "mergeAssociative2")
    prove(t)
    t
  }

  //accepted
  "AlbumsSystem" should "compatible commutes" in {
    val t = ("AlbumsSystem", "compatibleCommutes")
    prove(t)
    t
  }
}
