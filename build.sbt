lazy val scala211 = "2.11.12"
lazy val scala213 = "2.13.1"
lazy val supportedScalaVersions = Seq(scala211, scala213)

ThisBuild / name := "scala-ms-exchange"
ThisBuild / organization := "com.github.frankivo"
ThisBuild / version := "1.2.0"
ThisBuild / scalaVersion := scala213
ThisBuild / libraryDependencies += "com.microsoft.ews-java-api" % "ews-java-api" % "2.0"

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/frankivo/scala-ms-exchange"),
    "scm:git@github.com:frankivo/scala-ms-exchange.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id = "frankivo",
    name = "Frank Oosterhuis",
    email = "frank@scriptzone.nl",
    url = url("https://frankoosterhuis.nl")
  )
)

ThisBuild / description := "Making working with Exchange easier."
ThisBuild / licenses := List("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt"))
ThisBuild / homepage := Some(url("https://github.com/frankivo/scala-ms-exchange"))

ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true

lazy val root = (project in file("."))
  .aggregate(scala_ms_exchange)
  .settings(
    crossScalaVersions := Nil,
    publish / skip := true
  )

lazy val scala_ms_exchange = (project in file("."))
  .settings(
    crossScalaVersions := supportedScalaVersions,
    name := "scala-ms-exchange"
  )
