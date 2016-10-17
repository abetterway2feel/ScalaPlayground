name := "ScalaPlayground"

version := "0.1.0 "

scalaVersion := "2.11.7"

val akkaVersion = "2.4.2"

libraryDependencies ++= Seq(
  //predictor
//  "org.apache.poi" % "poi-ooxml" % "3.9",
  "org.apache.poi" % "poi" % "3.14",
  "org.apache.poi" % "poi-examples" % "3.14",

  //OCR
  "net.sourceforge.tess4j" % "tess4j" % "3.2.1",
  "com.asprise.ocr" % "java-ocr-api" % "[15,)",

  //HTTP
  "org.scalaj" %% "scalaj-http" % "2.3.0",

  //JSON
  "org.json4s" %% "json4s-core" % "3.3.0",
  "org.json4s" %% "json4s-jackson" % "3.3.0",

  //Akka
  "com.typesafe.akka" %% "akka-actor"               % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster"             % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j"               % akkaVersion,
  "com.typesafe.akka" %% "akka-agent"               % akkaVersion,
  "com.typesafe.akka" %% "akka-stream"              % akkaVersion,
  "org.scalatest"     %% "scalatest"                % "2.2.6"     % Test,

  //Guice
  "com.google.inject" % "guice" % "3.0"

)

updateOptions := updateOptions.value.withCachedResolution(true)

scalacOptions ++= Seq("-feature")