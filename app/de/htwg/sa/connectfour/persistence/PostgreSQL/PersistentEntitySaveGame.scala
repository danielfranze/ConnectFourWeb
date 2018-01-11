package de.htwg.sa.connectfour.persistence.PostgreSQL
import slick.jdbc.PostgresProfile.api._

class PersistentEntitySaveGame(tag: Tag) extends Table[(String, String)](tag, "SAVEGAME") {
  def name = column[String]("GAME_NAME", O.PrimaryKey)
  def matchfield = column[String]("MATCHFIELD")
  def * = (name, matchfield)
}