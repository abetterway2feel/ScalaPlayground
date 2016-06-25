package com.abetterway2feel.playground.utils

import scala.language.postfixOps

object RepeatUntil {

  def REPEAT(command: => Unit, condition: => Boolean): Unit = {
    command
    if (condition) ()
    else REPEAT(command, condition)
  }

  object REPEAT {
    def apply(command: => Unit): REPEAT = {
      new REPEAT(command)
    }
  }
  class REPEAT(command: => Unit){
    def UNTIL(condition:  => Boolean): Unit = {
      if(condition) ()
      else {
        command
        REPEAT(command) UNTIL condition
      }
    }
  }

  def main(args: Array[String]) {
    var count = 0
    REPEAT {
      count += 1
      println(s"phil-$count")
    } UNTIL (count == 5)
  }


}
