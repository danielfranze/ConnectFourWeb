package de.htwg.sa.connectfour.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends FlatSpec with Matchers {
  "A Player" should "have a name" in {
    Player("Michael").name should be("Michael")
  }

  "The name of a Player" should "be a String" in {
    Player("Mark").name shouldBe a[String]
  }
}
