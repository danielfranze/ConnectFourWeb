package de.htwg.sa.connectfour.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import scala.collection.mutable

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
    "a Player has diagonal won" should{
      "the return value of right top victory" in {
        val matchfield = new Matchfield(numberOfRows,numberOfColumns)
        val rightTop = List((5,2,"p1"),(5,3,"p2"),(4,3,"p1"),(5,4,"p2"),(5,5,"p1"),
          (4,4,"p2"),(3,4,"p1"),(4,5,"p2"),(3,5,"p1"),(5,6,"p2"),(2,5,"p1"))
        for(tupleElement <- rightTop){
          matchfield.setElementinMatchfield(tupleElement._1,tupleElement._2, tupleElement._3)
        }
        matchfield.areFourConnected("p1") should be(true)
        matchfield.areFourConnected("p2") should be(false)
      }
      "the return value of left bot victory" in {
        val matchfield = new Matchfield(numberOfRows,numberOfColumns)
        val leftBot = List((5,3,"p1"),(4,3,"p2"),(5,4,"p1"),(5,5,"p2"),(4,4,"p1"),
          (3,4,"p2"),(4,5,"p1"),(5,6,"p2"),(3,5,"p1"),(2,5,"p2"),(4,6,"p1"),(5,2,"p2"))
        for(tupleElement <- leftBot){
          matchfield.setElementinMatchfield(tupleElement._1,tupleElement._2, tupleElement._3)
        }
        matchfield.areFourConnected("p2") should be(true)
        matchfield.areFourConnected("p1") should be(false)
      }
      "the return value of left top victory" in {
        val matchfield = new Matchfield(numberOfRows,numberOfColumns)
        val leftTop = List((5,3,"p1"),(4,3,"p2"),(5,2,"p1"),(5,1,"p2"),(4,2,"p1"),
          (3,2,"p2"),(4,1,"p1"),(5,0,"p2"),(3,1,"p1"),(2,1,"p2"),(4,0,"p1"),(5,4,"p2"))
        for(tupleElement <- leftTop){
          matchfield.setElementinMatchfield(tupleElement._1,tupleElement._2, tupleElement._3)
        }
        matchfield.areFourConnected("p2") should be(true)
        matchfield.areFourConnected("p1") should be(false)
      }
      "the return value of right bot victory" in {
        val matchfield = new Matchfield(numberOfRows,numberOfColumns)
        val matchfield2 = new Matchfield(numberOfRows,numberOfColumns)
        
        val rightBot = List((5,4,"p1"),(5,3,"p2"),(5,2,"p1"),(4,3,"p2"),(4,2,"p1"),
                             (5,5,"p2"),(5,1,"p1"),(4,4,"p2"),(3,2,"p1"),(3,3,"p2"),
                             (5,6,"p1"),(2,2,"p2"))
        for(tupleElement <- rightBot){
          matchfield.setElementinMatchfield(tupleElement._1,tupleElement._2, tupleElement._3)
        }
        matchfield.areFourConnected("p2") should be(true)
        matchfield.areFourConnected("p1") should be(false)
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