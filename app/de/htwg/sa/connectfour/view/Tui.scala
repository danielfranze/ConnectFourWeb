package de.htwg.sa.connectfour.view

import de.htwg.sa.connectfour.controller.Controller
import de.htwg.sa.connectfour.model.{Matchfield, Player}
import de.htwg.sa.connectfour.util.Observer

class Tui(controller: Controller) extends Observer{

  controller.add(this)
  private val winningMessage = "\nGame is won!"

  def processInputLine(input: String):Unit = {
    input match {
      case "q" =>
      case "n"=> controller.createEmptyMatchfield()
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: Nil => controller.set(row, column)
          case _ =>
        }

    }
  }

  override def update: Unit =  {
    if(controller.gameIsWon) println(controller.matchfieldToString + winningMessage) else println(controller.matchfieldToString)
  }
}
