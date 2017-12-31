package controllers

import java.util
import javax.inject._

import play.api._
import play.api.mvc._
import java.util.{HashMap, Map}

import com.fasterxml.jackson.databind.JsonNode
import de.htwg.sa.connectfour.Start.controller
import de.htwg.sa.connectfour.model.{Matchfield, Player}
import play.api.libs.json.{JsObject, JsValue, Json}

import scala.collection.mutable
import scala.util.parsing.json.JSONObject
//import play.libs.Json
import collection.mutable.HashMap



@Singleton
class HomeController @Inject() extends Controller {

  val (rows, columns) = (6,7)
  val player1 = Player(1, "player1")
  val player2 = Player(2, "player2")
  val controller = new de.htwg.sa.connectfour.controller.Controller(new Matchfield(rows, columns), player1, player2)


  def matrixToHashMap:mutable.HashMap[String, String] = {
    val matrixList = new mutable.HashMap[String, String]()
    for(row <- 0 to 5)
      for(column <- 0 to 6){
        matrixList.put(s"$row$column", controller.matchfield.matrix(row)(column))
      }
    matrixList
  }

  def matrixToJson:JsValue = {
    Json.toJson(matrixToHashMap)
  }


  def index = Action {
    controller.set(5, 0)
    val test: String = controller.currentPlayer.name
    val test1 = matrixToJson
    controller.notifyObservers()
    Ok(views.html.index(s"Current Player: $test  $test1"))
  }



    //var test:Map[String, String] = new HashMap()
    //test.put("id", "Daniel")
    /*val json: JsValue = play.api.libs.json.Json.parse("""
  {
    "name" : "Watership Down",
    ""
  }
  """)
    Ok(Json.toJson(json))

  }*/

}