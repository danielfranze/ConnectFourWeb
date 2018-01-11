package de.htwg.sa.connectfour.persistence


trait ISaveGameDao {

  def getSaveGame(gameName:String):String

  def saveOrUpdateSaveGame(gameName:String, matchfield: String)

}
