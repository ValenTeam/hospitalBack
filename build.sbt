name := """sistemaHospital"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "org.avaje" % "ebean" % "2.7.3",
  "org.jongo" % "jongo" % "1.3.0",
  "org.mongodb" % "bson" % "3.4.2",
  "uk.co.panaxiom" %% "play-jongo" % "1.0.1-jongo1.2",
  "org.mongodb" % "mongo-java-driver" % "3.4.2",  
  javaWs
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
