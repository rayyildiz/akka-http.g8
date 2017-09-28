scalaVersion := "$scala_version$"

lazy val $name;format="norm,word"$Analysis = (project in file(".")).
  settings(Settings.settings: _*).
  settings(Settings.publishSettings: _*).
  settings(Settings.testSettings: _*).
  settings(Settings.$name;format="norm,word"$Settings: _*).
  settings(libraryDependencies ++= Dependencies.akkaDependencies).
  settings(libraryDependencies ++= Dependencies.utilDependencies).
  settings(libraryDependencies ++= Dependencies.testDependencies)
