package de.htwg.sa.connectfour.model

import scala.util.Try

class Matchfield(row: Int, column: Int){

  val matrix = Array.fill[String](row,column)("empty")


  def areFourConnected(player: String): Boolean = {
    def horizontalCheck(): Boolean ={
      var winCounter = 0
      for(currentRow <- 0 until row) for(currentColumn <- 0 until column){
        if(matrix(currentRow)(currentColumn) == player) winCounter += 1 else winCounter = 0
        if(winCounter == 4){return true}
      }
      false
    }
    if(horizontalCheck() == true) true else false

  }

}