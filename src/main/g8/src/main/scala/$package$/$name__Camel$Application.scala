package $package$

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.google.inject.Guice
import $package$.modules.{AkkaModule, ConfigModule}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}


object $name;format="Camel"$Application extends App {
  val hostAddress = "localhost"
  val port = 8080

  val injector = Guice.createInjector(
    new ConfigModule(),
    new AkkaModule()
  )

  implicit val system: ActorSystem = injector.getInstance(classOf[ActorSystem])
  implicit val executionContext: ExecutionContext = injector.getInstance(classOf[ExecutionContext])

  implicit val materializer: ActorMaterializer = injector.getInstance(classOf[ActorMaterializer])
  implicit val dispatcher = system.dispatcher

  val routes: Route = {
    val routes = injector.getInstance(classOf[Routes])
    routes()
  }
  val logger = Logging(system, getClass)

  val bindingFuture = Http().bindAndHandle(routes, hostAddress, port)

  bindingFuture.onComplete {
    case Success(_) =>
      logger.info(s"Server online at http://\$hostAddress:\$port/")
    case Failure(e) =>
      logger.error(e, s"Failed to open akak-http on http://\$hostAddress:\$port/")
      system.terminate()
  }
  // restServer.startServer(host, port)

}
