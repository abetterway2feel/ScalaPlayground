package com.abetterway2feel.playground.wierdexceptions.malformedclassname

trait Fruit

object WorkingFruit {

  class Orange extends Fruit

}

object ScalaClassNamingInvestigation {

  import BrokenFruit._
  import WorkingFruit._

  object BrokenFruit {

    case class BrokenOrange() extends Fruit

  }

  def main(args: Array[String]): Unit = {
    println(new Orange().getClass.getSimpleName)
    val classOfBrokenOrange = BrokenOrange.getClass
    try {
      classOfBrokenOrange.getSimpleName
    } catch {
      case error: Throwable =>
        println(classOfBrokenOrange.getDeclaringClass)
    }

  }
}
