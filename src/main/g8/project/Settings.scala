import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._
import sbtassembly.AssemblyPlugin.autoImport.{MergeStrategy, assemblyMergeStrategy}
import sbtassembly.PathList

object Settings {
  lazy val settings = Seq(
    organization    := "$organization;format="norm"$",
    version         := "$version;format="norm"$",
    scalaVersion    := "$scala_version;format="norm"$"
    publishMavenStyle := true,
    publishArtifact in Test := false,
    assemblyMergeStrategy in assembly := {
      case PathList("META-INF", xs@_ *) => MergeStrategy.discard
      case PathList("reference.conf") => MergeStrategy.concat
      case x => MergeStrategy.first
    }
  )

  lazy val publishSettings = Seq(
    publishTo := Some(
      if (isSnapshot.value)
        Opts.resolver.sonatypeSnapshots
      else
        Opts.resolver.sonatypeStaging
    ),
    publishArtifact in Test := false,
    publishMavenStyle := true,
    licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    homepage := Some(url("https://github.com/$github_user;format="norm"$/$name;format="norm"$")),
    scmInfo := Some(
      ScmInfo(
        url("http://github.com/$github_user;format="norm"$/$name;format="norm"$"),
        "scm:git:git@github.com:$github_user;format="norm"$/$name;format="norm"$.git"
      )
    ),
    developers := List(
      Developer(id = "$github_user;format="norm"$", name = "$github_user;format="norm"$", email = "", url = url(""))
    )
  )

  lazy val testSettings = Seq(
    fork in Test := false,
    parallelExecution in Test := false,
    test in assembly := {}
  )

  lazy val $name;format="norm,word"$Settings = Seq(
    name := "$name;format="norm"$"
  )
}