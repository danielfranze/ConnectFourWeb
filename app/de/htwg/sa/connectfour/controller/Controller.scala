package de.htwg.sa.connectfour.controller

import de.htwg.sa.connectfour.model.{Matchfield, Player}
import de.htwg.sa.connectfour.util.Observable

class Controller(var matchfield:Matchfield, val player1:Player, val player2:Player) extends Observable{

  var gameIsWon:Boolean = false
  var currentPlayer:Player = player1

  def createEmptyMatchfield():Unit = {
    gameIsWon = false
    val (rows, columns)  = (6,7)
    matchfield = new Matchfield(rows,columns)
    notifyObservers()
  }

  def set(row: Int, column: Int): Unit = {
    if(!gameIsWon){
      if(matchfield.setElementinMatchfield(row, column, currentPlayer.number.toString)){
        gameIsWon = matchfield.areFourConnected(currentPlayer.number.toString)
        if(currentPlayer == player1 && !gameIsWon) currentPlayer = player2 else currentPlayer = player1
      }
    }
    notifyObservers()
  }

  def matchfieldToString: String = matchfield.toString


}
