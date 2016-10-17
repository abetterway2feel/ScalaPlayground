package com.abetterway2feel.playground.variance

object Variance {

  class Covariant[+A]

  class Contravariant[-A]

  class Invariant[T]

  val co: Covariant[AnyRef] = new Covariant[String]
//  val co: Covariant[String] = new Covariant[Any] //doesn't compile

  val contra: Contravariant[String] = new Contravariant[AnyRef]
//  val contra: Contravariant[AnyRef] = new Contravariant[String] //doesn't compile

  val invariant: Invariant[String] = new Invariant[String]
//  val invariant: Invariant[AnyRef] = new Invariant[String] //doesn't compile
//  val invariant: Invariant[String] = new Invariant[AnyRef] //doesn't compile

  val succ: (Int) => Int = (x: Int) => x + 1


}
