scalaVersion := "$scala_version$"

lazy val $name;format="camel"$ = (project in file("."))
  .settings(Settings.settings: _*)
  .settings(Settings.publishSettings: _*)
  .settings(Settings.dockerSettings: _*)
  .settings(Settings.testSettings: _*)
  .settings(Settings.$name;format="camel"$Settings: _*)
  .settings(libraryDependencies ++= Dependencies.akkaDependencies)
  .settings(libraryDependencies ++= Dependencies.utilDependencies)
  .settings(libraryDependencies ++= Dependencies.testDependencies)
  .enablePlugins(DockerPlugin)
