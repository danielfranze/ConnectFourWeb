package de.htwg.sa.connectfour.controller
import de.htwg.sa.connectfour.model.{Matchfield, Player}
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {
  val (rows, columns) = (6,7)
  val player1 = Player(1, "player1")
  val player2 = Player(2, "player2")
  val test_controller = new Controller(new Matchfield(rows, columns), player1, player2)
  "The Controller" when {
    "create a empty Matchfield" should {
      test_controller.createEmptyMatchfield()
      "the value" in{
        test_controller.gameIsWon should be(false)
      }
      "the columns" in {
        var columns_counter = test_controller.matchfield.matrix(rows-1)
        var sum = 0
        for (element <- columns_counter){
          sum += 1
        }
        sum should be(columns)
      }
      "a String who represents the matrix" in {
        test_controller.createEmptyMatchfield()
        val expectedString = (("+" + ("---+" * columns) + "\n|" +
          ("   |"*columns) + "\n") *rows) +
          ("+" + ("---+" * columns) + "\n")
        test_controller.matchfieldToString should be(expectedString)
      }
    }
    "set a cell" should{
      test_controller.createEmptyMatchfield()
      "players" in{
        test_controller.matchfield.matrix(5)(0) should be("empty")
        test_controller.set(5,0)
        test_controller.matchfield.matrix(5)(0) should be("1")
        test_controller.matchfield.matrix(5)(2) should be("empty")
        test_controller.set(5,2)
        test_controller.matchfield.matrix(5)(2) should be("2")
      }
    }

  }
}
