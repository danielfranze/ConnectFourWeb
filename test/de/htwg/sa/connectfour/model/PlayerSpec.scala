package de.htwg.sa.connectfour.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends FlatSpec with Matchers {
  "A Player" should "have a name" in {
    Player(1, "Nickname").name should be("Nickname")
  }
  "The name of a Player" should "be a String" in {
    Player(1, "Nickname").name shouldBe a[String]
  }
  "The Player" should "have a number" in  {
    Player(1, "Nickname").number should be(1)
    Player(2, "Nickname").number should be(2)
  }
  "The number of a Player" should "be a Int" in  {
    Player(1, "Nickname").number shouldBe a[Integer]
  }
}
