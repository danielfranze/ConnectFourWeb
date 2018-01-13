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


  def diagnoalCheckEndLine(player: String):Boolean={
    var winCounterRightDown = 1
    var winCounterLeftDown = 1
    var winCounterRightUp = 1
    var winCounterLeftUp = 1

    for(i <- 1 to 3){
      scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {
        if (matrix(lastMoveRow + i)(lastMoveColumn + i) == player) {
          winCounterLeftDown += 1
        }
      }
      scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {
        if(matrix(lastMoveRow - i)(lastMoveColumn - i) == player){
          winCounterLeftUp += 1
        }
      }
      scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {
        if(matrix(lastMoveRow - i)(lastMoveColumn + i) == player){
          winCounterRightDown += 1
        }
      }
      scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {
        if (matrix(lastMoveRow + i)(lastMoveColumn - i) == player) {
          winCounterRightUp += 1
        }
      }
    }
    winCounterRightDown >= 4 || winCounterLeftDown >= 4 || winCounterRightUp >= 4 || winCounterLeftUp >= 4
  }

  def diagonalCheckMidLine(player: String) ={
    var winCounterRightMid = 1
    var winCounterLeftMid = 1

    for(i <- 1 to 2){
      scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {
        if (matrix(lastMoveRow + i)(lastMoveColumn + i) == player && matrix(lastMoveRow + 1)(lastMoveColumn + 1) == player) {
          winCounterLeftMid += 1
        }
      }
      scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {
        if(matrix(lastMoveRow - i)(lastMoveColumn - i) == player && matrix(lastMoveRow - 1)(lastMoveColumn - 1) == player){
          winCounterLeftMid += 1
        }
      }
      scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {
        if(matrix(lastMoveRow - i)(lastMoveColumn + i) == player && matrix(lastMoveRow - 1)(lastMoveColumn + 1) == player){
          winCounterRightMid += 1
        }
      }
      scala.util.control.Exception.ignoring(classOf[ArrayIndexOutOfBoundsException]) {
        if (matrix(lastMoveRow + i)(lastMoveColumn - i) == player && matrix(lastMoveRow + 1)(lastMoveColumn - 1) == player) {
          winCounterRightMid += 1
        }
      }
    }
    winCounterRightMid >= 4 || winCounterLeftMid >= 4
  }

  def diagonalCheck(player: String) ={
    var `Was the game won?` = false
    //println("Counter left: " + winCounterLeftDown + " Counter Right: " + winCounterRightDown)

    if( diagonalCheckMidLine(player) || diagnoalCheckEndLine(player)){
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
    //println("row: " + row + " column: " + column + " value: " + newValue)
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