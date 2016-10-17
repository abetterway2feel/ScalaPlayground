package com.abetterway2feel.playground.guice

class InMemoryBank extends Bank {
  override def doBankingThing(): Boolean = {
    println("doing banking things in memory...mmmm")
  }
}
