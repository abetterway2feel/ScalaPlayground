package com.streams

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream._
import akka.stream.scaladsl._

import scala.language.postfixOps
trait StreamsSystem {
  val systemName: String
  implicit val system = ActorSystem(systemName)
  implicit val materializer = ActorMaterializer()
}

object MyFirstStreams extends StreamsSystem {
  override val systemName = "reactive-tweets"

  /* Domain */
  final case class Author(handle: String)
  final case class Hashtag(name: String)

  final case class Tweet(author: Author, timestamp: Long, body: String) {
    def hashtags: Set[Hashtag] =
      body.split(" ").collect { case t if t.startsWith("#") => Hashtag(t) }.toSet
  }

  val akka = Hashtag("#akka")
  /* --- */

}
