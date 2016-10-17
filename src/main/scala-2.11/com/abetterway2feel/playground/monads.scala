package com.abetterway2feel.playground

object Monads extends App {


  val x = Set(Set(X("1")), Set(X("2")))

  println(x.flatten[X])

  val y = Map[String, Any]("a" -> 1, "b" -> 2)

  val z = y.toSet[(String, Any)].flatMap {
    case (k, v) =>
      Set(X(k))
  }

  println(z)


  case class X(key:String)
}
