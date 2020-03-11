ThisBuild / name := "scala-ms-exchange"
ThisBuild / organization := "com.github.frankivo"
ThisBuild / version := "1.2.0"
ThisBuild / scalaVersion := "2.13.1"
ThisBuild / libraryDependencies += "com.microsoft.ews-java-api" % "ews-java-api" % "2.0"

lazy val root = (project in file("."))
  .aggregate(scala_ms_exchange)
  .settings(
    // crossScalaVersions must be set to Nil on the aggregating project
    crossScalaVersions := Nil,
    publish / skip := true
  )

lazy val scala_ms_exchange = (project in file("."))
  .settings(
    crossScalaVersions := Seq("2.11.12", "2.13.1"),
    name := "scala-ms-exchange"
  )
