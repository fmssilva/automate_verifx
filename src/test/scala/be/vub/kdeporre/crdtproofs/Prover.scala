package be.vub.kdeporre.crdtproofs

import org.verifx.Analysis.Proofs._
import org.verifx.Compiler.ProjectCompiler
import org.verifx.Utilities.Scanner

import scala.annotation.tailrec

trait Prover {
  val files = Scanner.scan().toSet
  val pc = ProjectCompiler(files)
  //val proofResults = pc.checkProofs()

  def prove(proof: ProofName, maxTries: Int = 5, timeoutInSeconds: Int = 500): ProofResult = {
    val proofRes = pc.checkProof(proof, maxTries, timeoutInSeconds*1000)
    val reason = proofRes match {
      case _: Rejected => "rejected"
      case _: Aborted => "aborted"
      case _ => "proved"
    }
    assert(proofRes.isInstanceOf[Proved], s"Could not prove $proof [$reason]")
    proofRes
  }

  /**
   * Repeats the prove until it is proven or until the maximum number of retries has been reached.
   * @param maxTries Maximum number of retries
   */
  @tailrec
  final def prove(proof: ProofName, maxTries: Int): ProofResult = {
    if (maxTries > 0) {
      val proofRes = pc.checkProof(proof)
      proofRes match {
        case _: Rejected => throw new Error(s"Could not prove $proof because it was rejected.")
        case _: Aborted => prove(proof, maxTries - 1)
        case _ => proofRes
      }
    }
    else {
      throw new Error("Maximum number of retries reached.")
    }
  }

  def reject(proof: ProofName): ProofResult = {
    val proofRes = pc.checkProof(proof)
    val reason = proofRes match {
      case _: Rejected => "rejected"
      case _: Aborted => "aborted"
      case _ => "proved"
    }
    assert(proofRes.isInstanceOf[Rejected], s"Could not reject $proof [$reason]")
    proofRes
  }

  def rejectForModel(proof: ProofName): ProofResult = {
    val proofRes = pc.checkProofForModel(proof)
    val reason = proofRes match {
      case _: Rejected => "rejected"
      case _: Aborted => "aborted"
      case _ => "proved"
    }
    assert(proofRes.isInstanceOf[Rejected], s"Could not reject $proof [$reason]")
    proofRes
  }

  /**
   * Repeats the prove until it is rejected or until the maximum number of retries has been reached.
   * @param maxTries Maximum number of retries
   */
  @tailrec
  final def reject(proof: ProofName, maxTries: Int, timeoutInSeconds: Int = 10): ProofResult = {
    if (maxTries > 0) {
      val proofRes = pc.checkProof(proof, 5, timeoutInSeconds*1000)
      proofRes match {
        case _: Rejected => proofRes
        case _: Aborted => reject(proof, maxTries - 1)
        case _ => throw new Error(s"Could not reject $proof because it was showed correct.")
      }
    }
    else {
      throw new Error("Maximum number of retries reached.")
    }
  }
}