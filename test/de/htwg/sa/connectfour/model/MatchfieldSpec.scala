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
    "a Player has horizontal won" should{
      val matchfield = new Matchfield(numberOfRows,numberOfColumns)
      for(column <- 0 to 3){
        matchfield.matrix(0).update(column, "player")
      }
      "the return value" in {
        matchfield.areFourConnected("player") should be(true)
      }
    }
    "a Player has vertical won" should{
      val matchfield = new Matchfield(numberOfRows,numberOfColumns)
      for(row <- 0 to 3){
        matchfield.matrix(row).update(0, "player")
      }
      "the return value" in {
        matchfield.areFourConnected("player") should be(true)
      }
    }
    "a Player set a value" should{
      val matchfield = new Matchfield(numberOfRows,numberOfColumns)
      val result_true = matchfield.setElementinMatchfield(5,0, "1")
      val result_false = matchfield.setElementinMatchfield(5,0, "1")
      val result_true_inner = matchfield.setElementinMatchfield(4,0, "1")
      val result_false_inner = matchfield.setElementinMatchfield(1,0, "1")
      matchfield.setElementinMatchfield(5,2, "1")
      matchfield.setElementinMatchfield(5,1, "2")
      "the value" in {
        matchfield.matrix(5)(2) should be("1")
        matchfield.matrix(5)(1) should be("2")
        matchfield.matrix(5)(4) should be("empty")
      }
      "the boolean" in {
        result_true should be(true)
        result_false should be(false)
        result_true_inner should be(true)
        result_false_inner should be(false)
      }

    }
  }
}