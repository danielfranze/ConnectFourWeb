package de.htwg.sa.connectfour.model

class Matchfield(row: Int, column: Int){
  val matrix: Array[Array[String]] = Array.fill[String](row,column)("empty")

  val winNumber = 4
  var lastMoveRow = 0
  var lastMoveColumn = 0

  def horizontalCheck(player: String) ={
    var winCounter = 0
    var `Was the game won?` = false

    for(currentRow <- 0 until row) for(currentColumn <- 0 until column){
      if(matrix(currentRow)(currentColumn) == player) winCounter += 1 else winCounter = 0
      if(winCounter == winNumber){`Was the game won?` = true}
    }
    `Was the game won?`
  }

  def verticalCheck(player: String) ={
    var winCounter = 0
    var `Was the game won?` = false
    for(currentColumn <- 0 until column) for(currentRow <- 0 until row){
      if(matrix(currentRow)(currentColumn) == player) winCounter += 1 else winCounter = 0
      if(winCounter == winNumber){`Was the game won?` = true}
    }
    `Was the game won?`
  }

  def diagonalCheck(player: String) ={
    var winCounterRight = 1
    var winCounterLeft = 1
    var `Was the game won?` = false

    for(i <- 1 to 3){
    scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {

      if (matrix(lastMoveRow + i)(lastMoveColumn + i) == player) {
        winCounterLeft += 1
      }
    }
      scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {
        if(matrix(lastMoveRow - i)(lastMoveColumn - i) == player){
          winCounterLeft += 1
        }}
      scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {
        if(matrix(lastMoveRow - i)(lastMoveColumn + i) == player){
          winCounterRight += 1
        }}
      scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {
        if (matrix(lastMoveRow + i)(lastMoveColumn - i) == player) {
          winCounterRight += 1
        }
      }

    }

    println("Counter left: " + winCounterLeft + " Counter Right: " + winCounterRight)


    if(winCounterRight >= 4 || winCounterLeft >= 4){
      `Was the game won?` = true
    }
    `Was the game won?`
  }

  def areFourConnected(player: String): Boolean = {


    if(horizontalCheck(player) || verticalCheck(player) || diagonalCheck(player)) true else false

  }

  def setElementinMatchfield(row:Int, column:Int, newValue:String): Boolean ={
    lastMoveRow = row
    lastMoveColumn = column
    if(matrix(row)(column) == "empty") {
      try{
        if (matrix(row + 1)(column) != "empty") {
          matrix(row).update(column, newValue)
          true
        } else {
          false
        }
      } catch {
        case e: ArrayIndexOutOfBoundsException => matrix(row).update(column, newValue)
          true
      }
    } else {
      false
    }

  }

  override def toString: String = {
    var printMatrix = ""
    for(row <- matrix) {
      printMatrix += "+" + ("---+" * column) + "\n|"
      for(column <- row){
        if(column == "empty") printMatrix += "   |" else printMatrix += (" " + column + " |")
      }
      printMatrix += "\n"
    }
    printMatrix += "+" + ("---+" * column) + "\n"
    printMatrix
  }


}