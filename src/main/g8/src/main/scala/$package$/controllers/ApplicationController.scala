package $package$.controllers

import javax.inject.Singleton

import $package$.models.{Ping, Pong}

@Singleton
class ApplicationController {
  def ping(ping: Ping): Pong = Pong(ping.message)
}
