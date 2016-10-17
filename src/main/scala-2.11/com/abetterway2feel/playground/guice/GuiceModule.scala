package com.abetterway2feel.playground.guice

import com.google.inject.AbstractModule

class GuiceModule extends AbstractModule{
  override def configure(): Unit = {

    bind(classOf[Bank]).to(classOf[InMemoryBank])

  }
}
