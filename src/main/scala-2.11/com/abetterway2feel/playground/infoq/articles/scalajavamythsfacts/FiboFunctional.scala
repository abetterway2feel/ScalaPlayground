package com.abetterway2feel.playground.infoq.articles.scalajavamythsfacts

object FiboFunctional extends App {
  val fibs: Stream[BigInt] =
    0 #::
      1 #::
      (fibs zip fibs.tail)
        .map { case (a, b) => a + b }
  println(fibs(6))
}