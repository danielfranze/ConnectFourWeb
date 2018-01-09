package controllers

import java.util
import javax.inject._

import play.api._
import play.api.mvc._
import java.util.{HashMap, Map}

import com.fasterxml.jackson.databind.JsonNode
import de.htwg.sa.connectfour.Start.{controller, tui}
import de.htwg.sa.connectfour.model.{Matchfield, Player}
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

  object MyWebSocketActor {
    def props(out: ActorRef) = Props(new MyWebSocketActor(out))
  }

  class MyWebSocketActor(out: ActorRef) extends Actor {
    def receive = {
      case msg:String => {
        if(msg == "start_new_game")
          {controller.createEmptyMatchfield()
        } else if(msg == ""){

        } else{
          controller.set(msg.charAt(0).toInt - 48, msg.charAt(1).toInt - 48)
          //controller.notifyObservers()
        }
        out ! (matrixToJson.toString())
      }
    }
  }
  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef( out => MyWebSocketActor.props(out))
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
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }


}