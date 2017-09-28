import sbt._

object Dependencies {

  lazy val version = new {
    val scalaTest = "3.0.3"
    val akka      = "$akka_version;format="norm"$"
    val akkaHttp  = "$akka_http_version;format="norm"$"
  }

  lazy val library = new {
    val akkaActor   = "com.typesafe.akka" %% "akka-actor"   % version.akka
    val akkaSlf4j   = "com.typesafe.akka" %% "akka-slf4j"   % version.akka
    val akkaStream  = "com.typesafe.akka" %% "akka-stream"  % version.akka

    val akkaHttp = "com.typesafe.akka" %% "akka-http"             % version.akkaHttp
    val akkaJson = "com.typesafe.akka" %% "akka-http-spray-json"  % version.akkaHttp

    val scalaGuice      = "net.codingwell" %% "scala-guice"     % "4.1.0"
    val logbackClassic  = "ch.qos.logback" %  "logback-classic" % "1.2.3"

    val akkaTestKit     = "com.typesafe.akka" %% "akka-testkit"       % version.akka      % Test
    val akkaHttpTestKit = "com.typesafe.akka" %% "akka-http-testkit"  % version.akkaHttp  % Test
    val scalaTest       = "org.scalatest"     %% "scalatest"          % version.scalaTest % Test
  }

  val testDependencies: Seq[ModuleID] = Seq(
    library.akkaTestKit,
    library.akkaHttpTestKit,
    library.scalaTest
  )

  val akkaDependencies : Seq[ModuleID] = Seq(
    library.akkaActor,
    library.akkaJson,
    library.akkaHttp,
    library.akkaSlf4j,
    library.akkaStream
  )

  val utilDependencies : Seq[ModuleID] = Seq(
    library.scalaGuice,
    library.logbackClassic
  )

}
