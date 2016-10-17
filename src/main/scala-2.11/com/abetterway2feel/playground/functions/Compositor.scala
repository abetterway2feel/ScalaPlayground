package com.abetterway2feel.playground.functions

object Compositor extends App {

  val add10 = (value: Int) => value + 10
  val mult5 = (value: Int) => value * 5

  val add10ThenMult5 = add10 andThen mult5

  println(add10ThenMult5(10))

  val mult5AfterAdd10 = mult5 compose add10

  println(mult5AfterAdd10(10))

  val add = (x: Int, y: Int) => x + y


  val addThenMult5 = (x: Int ) => add(x, 10)
}
