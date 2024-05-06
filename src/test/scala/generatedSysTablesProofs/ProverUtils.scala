package generatedSysTablesProofs

import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * class with common methods to be used in the Proofs classes
 */
object ProverUtils {

	/*
	 * print starting time
	 */
	def printStartingTime(proof: String): Long = {
		val currentTime = LocalTime.now()
		val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
		val formattedTime = currentTime.format(formatter)
		println("Test  ( " + proof + " ) started at " + formattedTime)
		System.nanoTime()
	}

	/*
	 * print proof time
	 * @param startTime - start time of the proof
	 * @param endTime - end time of the proof
	 * @param proof - name of the proof
	 */
	def printProofTime(startTime: Long, endTime: Long, proof: String):Unit = {
		val durationInSeconds = (endTime - startTime) / 1e9d // convert nanoseconds to seconds
		val minutes = (durationInSeconds / 60).toInt
		val seconds = (durationInSeconds % 60).toInt
		println("Proof " + proof + " took " +  minutes + " minutes : " + seconds + " seconds.")
	}

}
