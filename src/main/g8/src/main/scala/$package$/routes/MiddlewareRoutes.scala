package $package$.routes

import javax.inject.{Inject, Singleton}

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import $package$.controllers.MiddlewareController
import $package$.models.JsonSerialization

@Singleton
class MiddlewareRoutes @Inject()(
  private val middlewareController: MiddlewareController
) extends JsonSerialization {

  def apply(): Route =
    path("health") {
      get {
        complete(middlewareController.health)
      }
    } ~
      path("info") {
        get {
          complete(middlewareController.info)
        }
      }
}
