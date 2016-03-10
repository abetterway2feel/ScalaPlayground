package com.agents

import java.util.{UUID, Properties}

import akka.actor.{ActorRefFactory, Actor, ActorLogging, Props}
import com.agents.ConfigHolder.Config
import com.agents.ConfigHolderActor.{Update, Start}

import scala.concurrent.duration._

object ConfigHolderActor {

  def props() = Props[ConfigHolderActor]

  sealed trait Request
  case object Update extends Request
  case class Config(properties:Map[String, Any] = Map()) extends Request
}

class ConfigHolderActor extends Actor with ActorLogging {

  override def aroundPostRestart(exception: Throwable) = {
    self ! Update
  }

  override def receive: Receive = {
    case Update =>
      log.info("Started pulling config")
      val uuid = UUID.randomUUID()
      println(s"updating -> $uuid")
      context.system.eventStream.publish(Config(Map("prop" -> uuid)))
      context.system.scheduler.scheduleOnce(1000.seconds, self, Update)
  }
}

object ConfigUser {

  def apply(actorRefFactory: ActorRefFactory) = {
    actorRefFactory.actorOf(props())
  }

  def props() = Props[ConfigUser]
}

class ConfigUser extends Actor {
  private val props = List("a", "b", "c")
  private val myConfig = Map()

  override def receive: Actor.Receive = {
    case Config(properties) =>
      props.foreach(value => myConfig ++ properties.get(value))
  }
}