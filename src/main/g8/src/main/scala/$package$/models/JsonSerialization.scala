package $package$.models

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

trait JsonSerialization extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val applicationHealthFormat = jsonFormat2(ApplicationStatus)
  implicit val applicationInformationFormat = jsonFormat2(ApplicationInformation)

  implicit val pingFormat = jsonFormat1(Ping)
  implicit val pongFormat = jsonFormat1(Pong)

  implicit val errorMessageFormat = jsonFormat2(ErrorMessage)
}
