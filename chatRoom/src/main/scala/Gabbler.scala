import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps
import akka.actor.typed.{ ActorRef, ActorSystem, Behavior }
//#imports
//#fiddle_code

import akka.NotUsed
import akka.Done
import akka.actor.typed.{ DispatcherSelector, Terminated }
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

object Gabbler {
  import ChatRoom._

  def apply(): Behavior[SessionEvent] =
    Behaviors.setup { context =>
      Behaviors.receiveMessage {
        //#chatroom-gabbler
        // We document that the compiler warns about the missing handler for `SessionDenied`
        case SessionDenied(reason) =>
          context.log.info("cannot start chat room session: {}", reason)
          Behaviors.stopped
        //#chatroom-gabbler
        case SessionGranted(handle) =>
          handle ! PostMessage("Hello World!")
          Behaviors.same
        case MessagePosted(screenName, message) =>
          context.log.info2("message has been posted by '{}': {}", screenName, message)
          Behaviors.stopped
      }
    }
}