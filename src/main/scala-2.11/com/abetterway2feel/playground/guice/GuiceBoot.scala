package com.abetterway2feel.playground.guice

import com.google.inject.Guice

object GuiceBoot extends App {

  val injector = Guice.createInjector(new GuiceModule())

  injector.getInstance(classOf[Bank]).doBankingThing()

}
