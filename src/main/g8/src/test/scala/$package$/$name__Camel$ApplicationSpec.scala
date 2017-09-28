package $package$

import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, RequestEntity, StatusCodes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.scaladsl.{Sink, Source}
import $package$.models._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Seconds, Span}
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class $name__Camel$ApplicationSpec
  extends FlatSpec
    with ScalaFutures
    with BeforeAndAfterAll
    with Matchers
    with JsonSerialization {
  BootApplication.main(Array())

  implicit val system = $name__Camel$Application.system

  import system.dispatcher

  implicit val materializer = $name__Camel$Application.materializer
  // scalastyle:off magic.number
  implicit val defaultPatience = PatienceConfig(timeout = Span(10, Seconds), interval = Span(1000, Millis))
  // scalastyle:on magic.number

  override def afterAll(): Unit = system.terminate()

  private def sendRequest(request: HttpRequest) =
    Source
      .single(request)
      .via(
        Http().outgoingConnection(host = $name__Camel$Application.hostAddress, port = $name__Camel$Application.port)
      )
      .runWith(Sink.head)

  "Application" should "return Pong on POST /ping" in {
    val ping = Ping("Hello")
    val requestEntity = Await.result(Marshal(ping).to[RequestEntity], Duration.Inf)
    val responseFuture = sendRequest(HttpRequest(uri = "/ping", method = HttpMethods.POST, entity = requestEntity))
    whenReady(responseFuture) { response =>
      val pongFuture = Unmarshal(response.entity).to[Pong]
      whenReady(pongFuture) { pong =>
        pong.message shouldBe ping.message
      }
    }
  }

}
