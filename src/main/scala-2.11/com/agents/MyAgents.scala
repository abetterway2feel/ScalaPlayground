package com.agents

import java.util.{Timer, UUID, TimerTask}

import akka.agent.Agent
import com.agents.ConfigHolder.Config

import scala.concurrent.ExecutionContext

final class ConfigHolder(implicit ec: ExecutionContext) extends TimerTask {
  protected[agents] val config = Agent(Config())

  private val timer = new Timer()
  timer.scheduleAtFixedRate(this, 10, 1000)

  override def run(): Unit = {
    val uuid = UUID.randomUUID()
    println(s"updating -> $uuid")
    config send Config(Map("prop" -> uuid))
  }
}

object ConfigHolder {
  implicit def ec: ExecutionContext =  scala.concurrent.ExecutionContext.Implicits.global

  private val globalConfigHolder = new ConfigHolder()

  def apply(): ConfigHolder = globalConfigHolder

  case class Config(properties:Map[String, Any] = Map())
}


object Something{

  def main(args: Array[String]) {

    while(true) {
      println(ConfigHolder().config.get())
      Thread.sleep(5000)
    }
  }
}
