name := "ScalaPlayground"

version := "0.1.0 "

scalaVersion := "2.11.7"

val akkaVersion = "2.4.2"

libraryDependencies ++= Seq(
  //predictor
//  "org.apache.poi" % "poi-ooxml" % "3.9",
  "org.apache.poi" % "poi" % "3.14",
  "org.apache.poi" % "poi-examples" % "3.14",

  "com.typesafe.akka" %% "akka-actor"               % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster"             % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j"               % akkaVersion,
  "com.typesafe.akka" %% "akka-agent"               % akkaVersion,
  "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
  "org.scalatest"     %% "scalatest"                % "2.2.6"     % Test

)

updateOptions := updateOptions.value.withCachedResolution(true)

scalacOptions ++= Seq("-feature")