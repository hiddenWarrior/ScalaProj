name := "Twitter"

version := "1.0"

scalaVersion := "2.11.8"



resolvers += "spray repo" at "http://repo.spray.io"
resolvers += "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/" 

val sprayVersion = "1.3.3"

libraryDependencies ++= Seq(
 
  "com.typesafe.akka" %% "akka-http-experimental" % "0.7",
  "io.spray" %% "spray-routing" % sprayVersion,
  "io.spray" %% "spray-client" % sprayVersion,
  "io.spray" %% "spray-testkit" % sprayVersion % "test",
  "io.spray" %%  "spray-json" % "1.3.2",
  "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2",
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "org.scalatest" %% "scalatest" % "2.2.2" % "test",
  "org.mockito" % "mockito-all" % "1.9.5" % "test",
  "org.json4s" %% "json4s-native" % "3.3.0"

)

libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor"      % "2.4.2",
    "com.typesafe.akka" %% "akka-slf4j"      % "2.4.2",
  "org.reactivemongo" %% "reactivemongo" % "0.11.7")
