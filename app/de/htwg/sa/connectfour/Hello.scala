package de.htwg.sa.connectfour

import de.htwg.sa.connectfour.model.{Matchfield, Player}

object Hello extends App{

  val player = Player(1, "Your Name")
  println("Hello, " + player.name)

  val matrix1 = new Matchfield(6,7)
  matrix1.matrix(0).update(0, "1")
  matrix1.matrix(0).update(1, "1")
  matrix1.matrix(0).update(2, "1")
  matrix1.matrix(0).update(3, "1")

  matrix1.matrix(1).update(2, "2")
  println(matrix1.toString())
  println(matrix1.areFourConnected("1"))

}
