package de.htwg.sa.connectfour.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MatchfieldSpec extends WordSpec with Matchers {

  "The Matchfield" when {
    "starting" should {
      val matchfield = new Matchfield(5,5)
      "empty" in {

        for(row <- matchfield.matrix) {
          for(column <- row) {
            column should be("empty")
          }
        }

      }
    }
  }

}
