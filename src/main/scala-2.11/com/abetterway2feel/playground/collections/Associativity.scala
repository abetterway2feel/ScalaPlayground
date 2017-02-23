package com.abetterway2feel.playground.collections

object Associativity extends App{

  val listOfIntegers: List[Int] = List(1,2,3)
  println(listOfIntegers.asInstanceOf[::[Int]])

  val builder = List.newBuilder[Int]
  builder += 1
  builder += 2
  builder += 3
  val built = builder.result()

  println(built)
  println(4 +: built)
  println(built :+ 4)

  class Adder(int: Int) {

    def +:(int: Int) = this.int + int
    def +(int: Int) = this.int + int
  }

  val six = new Adder(6)

  println(5 +: six)
  println(six + 5)
}
