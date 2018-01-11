package de.htwg.sa.connectfour.persistence.PostgreSQL
import de.htwg.sa.connectfour.persistence.ISaveGameDao
import slick.jdbc.PostgresProfile.api._

import slick.jdbc.meta.MTable
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class PostgreSQL extends ISaveGameDao{
  // Definition of the SAVEGAME table
  val savegame = TableQuery[PersistentEntitySaveGame]
  //val db = Database.forConfig("h2mem1")
  val db = Database.forConfig("postgresDB")

  def getSaveGame(gameName:String):String ={
    var saveGameData = "noDataAvailable"

    try {
      val resultFuture = {
        //println("Savegames:")
        db.run(savegame.result).map(_.foreach {
          case (name, matchfield) =>
            //println("Name: " + name + "\tMatchfield: " + matchfield)
            saveGameData = matchfield
        })
      }
      Await.result(resultFuture, Duration.Inf)
    } finally db.close
    saveGameData
  }

  def saveOrUpdateSaveGame(gameName:String, matchfield: String): Unit ={

    try {
      // Create the tables, including primary & Check the existence of the table
      def createTableIfNotInTables(tables: Vector[MTable]): Future[Unit] = {
        if (!tables.exists(_.name.name == savegame.baseTableRow.tableName)) {
          db.run(savegame.schema.create)
        } else {
          Future()
        }
      }
      val createTableIfNotExist: Future[Unit] = db.run(MTable.getTables).flatMap(createTableIfNotInTables)
      Await.result(createTableIfNotExist, Duration.Inf)

      //Insert or update
      val updated = savegame.insertOrUpdate(gameName, matchfield)
      Await.result(db.run(updated), Duration.Inf)

    } finally db.close
  }

}




object Test extends App{

  val test = new PostgreSQL
  //test.saveOrUpdateSaveGame("save_game_7777", "{player:1}")
  //test.updateCol("save_game_777", "neuer Wert!")
  //test.insertorupdate("test1234", " König!")

  //test.saveOrUpdateSaveGame("Game_001", "{player:1,Testganzgut:1234}")
  println("Matchfield: " + test.getSaveGame("save_game_#1"))

}
