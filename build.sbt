name := "REST API"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Typesafe" at "https://repo.typesafe.com/typesafe/releases/"

val sprayVersion = "1.3.3"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream-experimental" % "1.0",
  "com.typesafe.akka" %% "akka-http-core-experimental" % "1.0",
  "com.typesafe.akka" %% "akka-http-experimental" % "1.0",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "1.0",
  "org.reactivemongo" %% "reactivemongo" % "0.11.7",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "com.typesafe.akka" %% "akka-http-testkit-experimental" % "1.0",
  "io.spray" %% "spray-routing" % sprayVersion,
  "io.spray" %% "spray-client" % sprayVersion,
  "io.spray" %% "spray-testkit" % sprayVersion % "test",
  "io.spray" %%  "spray-json" % "1.3.2"


)

