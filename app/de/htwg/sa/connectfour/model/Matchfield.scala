package de.htwg.sa.connectfour.model

class Matchfield(row: Int, column: Int){
  val matrix: Array[Array[String]] = Array.fill[String](row,column)("empty")

  def areFourConnected(player: String): Boolean = {
    val winNumber = 4

    def horizontalCheck(): Boolean ={
      var winCounter = 0
      var `Was the game won?` = false

      for(currentRow <- 0 until row) for(currentColumn <- 0 until column){
        if(matrix(currentRow)(currentColumn) == player) winCounter += 1 else winCounter = 0
        if(winCounter == winNumber){`Was the game won?` = true}
      }
      `Was the game won?`
    }

    if(horizontalCheck()) true else false

  }

  override def toString: String = {
    var printMatrix = ""
    for(row <- matrix) {
      printMatrix += "+" + ("---+" * column) + "\n|"
      for(column <- row){
        if(column == "empty") printMatrix += "   |" else printMatrix += (column + " |")
      }
      printMatrix += "\n"
    }
    printMatrix += "+" + ("---+" * column) + "\n"
    printMatrix
  }


}