import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.server.Directives.{complete, path}
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

object Server extends App {
  val host = "0.0.0.0"
  val port = 9000
  implicit val system: ActorSystem = ActorSystem("helloworld")
  implicit val executor: ExecutionContext = system.dispatcher
  val materializer: ActorMaterializer = ActorMaterializer()

  var send = Huffman.createCodeTree("i am the amazing moriya malka".toList)
  var encoding = Huffman.quickEncode(send)("moriya malka".toList)
  var decoding = Huffman.decode(send,encoding)

  val itemRoutes: Route =
    Directives.concat(
      path("encode") {
        Directives.get {
          complete("Hello, moriya! encode " + encoding.toString())
        }
      },
      path("decode") {
        Directives.get {
          complete("Hello, moriya! decode " + decoding.toString())
        }
      }
    )


  Http().bindAndHandle(itemRoutes, host, port)
}
