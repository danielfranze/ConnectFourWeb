package de.htwg.sa.connectfour

import de.htwg.sa.connectfour.model.{Matchfield, Player}

object Hello extends App{

  val player = Player(1, "Your Name")
  println("Hello, " + player.name)

  val matrix = new Matchfield(6,7)
  println(matrix.toString())

}
