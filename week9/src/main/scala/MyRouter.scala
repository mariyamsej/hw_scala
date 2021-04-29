import akka.actor.typed.ActorSystem
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directives, Route}

import scala.concurrent.ExecutionContext

trait  Router {
  def route:Route
}

class MyRouter(todoRepository: TodoRepository)(implicit system: ActorSystem[_],  ex:ExecutionContext) extends Router with  Directives {
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._


  override def route = concat(
    path("/"){
    val MainCalcActor = context.actorOf(Props(new MainCalc), "MainCalculator")
    concat(
      path(String) { expr =>
        MainCalcActor ! SetRequest(expr.toString)
        val result = MainCalcActor ? GetRequest("Result")

      }
    )
  }
  )

  def getRoute : Route = get {
    path("dataSourceByName") {
      parameters('name.as[String]) {
        (name) =>
        ...
      }
    }
  }
  }