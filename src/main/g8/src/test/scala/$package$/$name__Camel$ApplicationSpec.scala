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

class $name;format="Camel"$ApplicationSpec
  extends FlatSpec
    with ScalaFutures
    with BeforeAndAfterAll
    with Matchers
    with JsonSerialization {
  $name;format="Camel"$Application.main(Array())

  implicit val system = $name;format="Camel"$Application.system

  import system.dispatcher

  implicit val materializer = $name;format="Camel"$Application.materializer
  // scalastyle:off magic.number
  implicit val defaultPatience = PatienceConfig(timeout = Span(10, Seconds), interval = Span(1000, Millis))
  // scalastyle:on magic.number

  override def afterAll(): Unit = system.terminate()

  private def sendRequest(request: HttpRequest) =
    Source
      .single(request)
      .via(
        Http().outgoingConnection(host = $name;format="Camel"$Application.hostAddress, port = $name;format="Camel"$Application.port)
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

  it should "return 404 on GET /NotDefinedRoute" in {
    val responseFuture = sendRequest(HttpRequest(uri = "/NotDefinedRoute", method = HttpMethods.GET))
    whenReady(responseFuture) { response =>
      response.status shouldBe StatusCodes.OK

      val errorMessageFuture = Unmarshal(response.entity).to[ErrorMessage]
      whenReady(errorMessageFuture) { errorMessage =>
        errorMessage.statusCode shouldBe StatusCodes.NotFound.intValue
      }
    }
  }

  it should "return UP on GET /health" in {
    val responseFuture = sendRequest(HttpRequest(uri = "/health", method = HttpMethods.GET))
    whenReady(responseFuture) { response =>
      val applicationStatusResponseFuture = Unmarshal(response.entity).to[ApplicationStatus]
      whenReady(applicationStatusResponseFuture) { applicationStatus =>
        applicationStatus.status shouldBe true
      }
    }
  }

  it should "return information on GET /info" in {
    val responseFuture = sendRequest(HttpRequest(uri = "/info", method = HttpMethods.GET))
    whenReady(responseFuture) { response =>
      val applicationInfoResponseFuture = Unmarshal(response.entity).to[ApplicationInformation]
      whenReady(applicationInfoResponseFuture) { applicationInfo =>
        applicationInfo.version shouldBe "$version$"
        applicationInfo.name shouldBe "$name;format="normalize"$"
      }
    }
  }

}
