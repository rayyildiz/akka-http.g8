package $package$.models

case class Ping(message: String)

case class Pong(message: String)

// Error Message
case class ErrorMessage(statusCode: Int, error: String)
