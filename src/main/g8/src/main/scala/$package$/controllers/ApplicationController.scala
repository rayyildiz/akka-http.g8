package $package$.controllers

import javax.inject.{Inject, Singleton}

import $package$.models.{Ping, Pong}

import scala.concurrent.ExecutionContext

@Singleton
class ApplicationController @Inject()()(implicit val executionContext: ExecutionContext) {

  def ping(ping: Ping): Pong = Pong(ping.message)
  
}
