package de.htwg.sa.connectfour.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MatchfieldSpec extends WordSpec with Matchers {
  val (numberOfRows,numberOfColumns) = (6,7)
  "The Matchfield" when {
    "starting" should {
      val matchfield = new Matchfield(numberOfRows,numberOfColumns)
      "empty" in {
        for(row <- matchfield.matrix) {
          for(column <- row) {
            column should be("empty")
          }
        }
      }
      "a String who represents the matrix" in {
        val expectedString = (("+" + ("---+" * numberOfColumns) + "\n|" +
                              ("   |"*numberOfColumns) + "\n") *numberOfRows) +
                              ("+" + ("---+" * numberOfColumns) + "\n")
         matchfield.toString should be(expectedString)
      }
    }
    "a Player has vertical won" should{
      val matchfield = new Matchfield(numberOfRows,numberOfColumns)
      for(column <- 0 to 4){
        matchfield.matrix(0).update(column, "player")
      }
      "the return value" in {
        matchfield.areFourConnected("player") should be(true)
      }
    }
  }
}
