package controllers

import com.google.gson.Gson
import java.util.{Map => JMap, LinkedHashMap}
import com.google.common.reflect.TypeToken



import java.util
import javax.inject._

import play.api._
import play.api.mvc._
import java.util.{HashMap, Map}

import com.fasterxml.jackson.databind.JsonNode
import de.htwg.sa.connectfour.Start.{controller, tui}
import de.htwg.sa.connectfour.model.{Matchfield, Player}
import de.htwg.sa.connectfour.persistence.ISaveGameDao
import de.htwg.sa.connectfour.persistence.PostgreSQL.PostgreSQL
import de.htwg.sa.connectfour.view.Tui
import play.api.libs.EventSource

import scala.io.StdIn.readLine
//import play.api.libs.iteratee.Concurrent
import play.api.libs.json.{JsObject, JsValue, Json}

import scala.collection.mutable
import scala.util.parsing.json.JSONObject
//import play.libs.Json
import collection.mutable.HashMap



import play.api.libs.streams._
import akka.stream._
import akka.actor._

@Singleton
class HomeController @Inject()(components: ControllerComponents)
                              (implicit system: ActorSystem, materializer: Materializer) extends AbstractController(components) {


  val (rows, columns) = (6,7)
  val player1 = Player(1, "player1")
  val player2 = Player(2, "player2")
  val controller = new de.htwg.sa.connectfour.controller.Controller(new Matchfield(rows, columns), player1, player2)
  val tui = new Tui(controller)
  var thread:Thread = new Thread
  var threadIsRunning = false
  var saveGameName = "save_game_#1"

  object MyWebSocketActor {
    def props(out: ActorRef) = Props(new MyWebSocketActor(out))
  }

  class MyWebSocketActor(out: ActorRef) extends Actor {
    def receive = {
      case msg:String => {
        msg match{
          case "start_new_game" => controller.createEmptyMatchfield()
          case "save_game" => saveMatrixToDatabase()
          case "load_save_game" => loadMatrixFromDatabaseToCurrentGameInstance()
          case "" =>
          case _ => controller.set(msg.charAt(0).toInt - 48, msg.charAt(1).toInt - 48)
        }
        out ! matrixToJson.toString()
      }
    }
  }

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef( out => MyWebSocketActor.props(out))
  }

  def saveMatrixToDatabase():Unit ={
     val saveGameInstance = new PostgreSQL with ISaveGameDao
    saveGameInstance.saveOrUpdateSaveGame(saveGameName, matrixToJson.toString())
  }

  def loadMatrixFromDatabaseToCurrentGameInstance():Unit ={
    val saveGameInstance = new PostgreSQL with ISaveGameDao
    val jsonString = saveGameInstance.getSaveGame(saveGameName)
    val jsonObject = Json.parse(jsonString)
    //val gson = new Gson()
    //var encoded = ""
    //encoded = gson.fromJson(jsonString, (new LinkedHashMap[String, Object]()).getClass)
    //println("Matrix: " + encoded)

    val gson = new Gson
    val mapType = new TypeToken[util.HashMap[String, String]] {}.getType
    val map = gson.fromJson[util.Map[String, String]](jsonString, mapType)
    //println(jsonString)


    var jsonStringToMap = jsonString.substring(1, jsonString.length - 1)
      .split(",")
      .map(_.split(":"))
      .map { case Array(k, v) => (k.substring(1, k.length-1), v.substring(1, v.length-1))}
      .toMap

    //println(jsonStringToMap)
    //println(jsonStringToMap.get("45").toString.replace("Some(","").replace(")",""))

    for(row <- 0 to 5)
      for(column <- 0 to 6){
        controller.matchfield.matrix(row)(column) = jsonStringToMap.get(s"$row$column").toString.replace("Some(","").replace(")","")
        //matrixList.put(s"$row$column", controller.matchfield.matrix(row)(column))
      }

    if(jsonStringToMap.get("won").toString.replace("Some(","").replace(")","") == "true"){
      controller.gameIsWon = true
    } else if(jsonStringToMap.get("won").toString.replace("Some(","").replace(")","") == "false"){
      controller.gameIsWon = false
    }

    if(jsonStringToMap.get("player").toString.replace("Some(","").replace(")","") == "Yellow"){
      controller.currentPlayer = controller.player1
    } else if(jsonStringToMap.get("player").toString.replace("Some(","").replace(")","") == "Red"){
      controller.currentPlayer = controller.player2
    }

  }

  def matrixToHashMap:mutable.HashMap[String, String] = {
    val matrixList = new mutable.HashMap[String, String]()
    for(row <- 0 to 5)
      for(column <- 0 to 6){
        matrixList.put(s"$row$column", controller.matchfield.matrix(row)(column))
      }

    if(controller.currentPlayer.number == 1){
      matrixList.put("player", "Yellow")
    } else if(controller.currentPlayer.number == 2){
      matrixList.put("player", "Red")
    }

    if(controller.gameIsWon){
      matrixList.put("won", "true")
    } else {
      matrixList.put("won", "false")
    }

    matrixList
  }

  def matrixToJson:JsValue = {
    Json.toJson(matrixToHashMap.toMap)
  }

  def index = Action {
    //controller.set(5, 0)
    //controller.set(5, 1)
    val current_player: String = controller.currentPlayer.name
    val current_matrix = matrixToJson.toString()


    if(!threadIsRunning){
      thread = new Thread {
        override def run {
          startTui()
        }
      }
      thread.start
      threadIsRunning = true
    }

    //Ok(views.html.index(s"Current Player: $test  $test1"))

    Ok(views.html.index(s"Current Player: $current_player", current_player, s"$current_matrix"))

  }

  def startTui(){
    controller.notifyObservers()
    // Input via console
    /*var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")*/
  }


}