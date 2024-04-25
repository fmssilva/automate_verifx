name := "crdt-library-for-antidote-sql"
organization := "d.borrego"
version := "0.1-SNAPSHOT"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "org.verifx" %% "verifx" % "1.0.2",
  "org.scalatest" %% "scalatest" % "3.0.8" % "test"
)
