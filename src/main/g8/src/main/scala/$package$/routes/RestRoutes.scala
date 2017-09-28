package $package$.routes

import javax.inject.{Inject, Singleton}

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import $package$.controllers.{ApplicationController}
import $package$.models._

@Singleton
class RestRoutes @Inject()(
                            private val applicationController: ApplicationController
                          ) extends JsonSerialization {

  def apply(): Route =
    path("ping") {
      post {
        entity(as[Ping]) { ping =>
          complete(applicationController.ping(ping))
        }
      }
    } /* ~ pathPrefix("api") {
      path("test") {
        post {
          entity(as[TextRequest]) { entity =>
            complete(testController.clean(entity.text))
          }
        }
      }
    } */
}
