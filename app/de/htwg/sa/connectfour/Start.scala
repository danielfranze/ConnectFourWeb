package de.htwg.sa.connectfour

import de.htwg.sa.connectfour.model.{Matchfield, Player}
import de.htwg.sa.connectfour.controller.Controller
import de.htwg.sa.connectfour.view.Tui

import scala.io.StdIn.readLine

object Start extends App{
  val (rows, columns) = (6,7)
  val player1 = Player(1, "player1")
  val player2 = Player(2, "player2")
  val controller = new Controller(new Matchfield(rows, columns), player1, player2)
  val tui = new Tui(controller)
  controller.notifyObservers()

    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")

}
