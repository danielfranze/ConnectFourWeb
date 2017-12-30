package controllers

import java.util
import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._
import java.util.{HashMap, Map}

import de.htwg.sa.connectfour.Start.controller
import de.htwg.sa.connectfour.model.{Matchfield, Player}





@Singleton
class HomeController @Inject() extends Controller {

  val (rows, columns) = (6,7)
  val player1 = Player(1, "player1")
  val player2 = Player(2, "player2")
  val controller = new de.htwg.sa.connectfour.controller.Controller(new Matchfield(rows, columns), player1, player2)

  def index = Action {
    controller.set(5,0)
    val test:String = controller.currentPlayer.name

    controller.notifyObservers()
    Ok(views.html.index(s"Current Player: $test"))








    //var test:Map[String, String] = new HashMap()
    //test.put("id", "Daniel")
    /*val json: JsValue = Json.parse("""
  {
    "name" : "Watership Down",
    "location" : {
      "lat" : 51.235685,
      "long" : -1.309197
    },
    "residents" : [ {
      "name" : "Fiver",
      "age" : 4,
      "role" : null
    }, {
      "name" : "Bigwig",
      "age" : 6,
      "role" : "Owsla"
    } ]
  }
  """)
    Ok(Json.toJson(json))*/

  }

}
