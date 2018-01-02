package controllers

import java.util
import javax.inject._

import play.api._
import play.api.mvc._
import java.util.{HashMap, Map}

import com.fasterxml.jackson.databind.JsonNode
import de.htwg.sa.connectfour.Start.controller
import de.htwg.sa.connectfour.model.{Matchfield, Player}
import play.api.libs.EventSource
import play.api.libs.iteratee.Concurrent
import play.api.libs.json.{JsObject, JsValue, Json}

import scala.collection.mutable
import scala.util.parsing.json.JSONObject
//import play.libs.Json
import collection.mutable.HashMap



import play.api.libs.streams._
import akka.stream._
import akka.actor._



object MyWebSocketActor {
  def props(out: ActorRef) = Props(new MyWebSocketActor(out))
}

class MyWebSocketActor(out: ActorRef) extends Actor {
  def receive = {
    case msg: String =>
      out ! ("I received your message: " + msg)
  }
}


@Singleton
class HomeController @Inject()(implicit system: ActorSystem, materializer: Materializer) extends Controller {

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
    Json.toJson(matrixToHashMap.toMap)
  }

  def index = Action {
    controller.set(5, 0)
    controller.set(5, 1)
    val current_player: String = controller.currentPlayer.name
    val current_matrix = matrixToJson.toString()
    controller.notifyObservers()
    //Ok(views.html.index(s"Current Player: $test  $test1"))

    Ok(views.html.index(s"Current Player: $current_player", current_player, s"$current_matrix"))

  }

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef(out => MyWebSocketActor.props(out))

  }





}