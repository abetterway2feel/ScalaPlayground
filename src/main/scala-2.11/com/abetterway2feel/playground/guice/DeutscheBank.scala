package com.abetterway2feel.playground.guice

class DeutscheBank extends Bank {
  override def doBankingThing(): Boolean = {
    println("Jetzt mach ich Bank Sachen auf Deutch")
    true
  }
}
