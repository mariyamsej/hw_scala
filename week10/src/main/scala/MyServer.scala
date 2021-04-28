import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.actor._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object  MyServer{
  val host = "0.0.0.0"
  val port = 9000
  def startHttpServer(routes: Route)(implicit system: ActorSystem[_],  ex:ExecutionContext): Unit = {
    // Akka HTTP still needs a classic ActorSystem to start
    val futureBinding = Http().newServerAt(host, port).bind(routes)
    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
  }
}