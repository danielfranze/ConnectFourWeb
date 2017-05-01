package de.htwg.sa.connectfour.controller

import de.htwg.sa.connectfour.model.{Matchfield, Player}
import de.htwg.sa.connectfour.util.Observable

class Controller(var matchfield:Matchfield, var player1:Player, var player2:Player, var currentPlayer:Player) extends Observable{

  def createEmptyMatchfield():Unit = {
    val (rows, columns)  = (6,7)
    matchfield = new Matchfield(rows,columns)
    notifyObservers()
  }

  def createPlayers(nameOfplayer1: String, nameOfplayer2: String): Unit ={
    player1 = Player(1, nameOfplayer1)
    player2 = Player(2, nameOfplayer1)
    currentPlayer = player1
    notifyObservers()
  }

  def areFourConnected(player: String): Unit ={
    matchfield.areFourConnected(player)
    notifyObservers()
  }


  def set(row: Int, column: Int): Unit = {
    matchfield.setElementinMatchfield(row, column, currentPlayer.number.toString)
    if(currentPlayer == player1) currentPlayer = player2 else currentPlayer = player1
    notifyObservers()
  }

  def matchfieldToString: String = matchfield.toString


}
