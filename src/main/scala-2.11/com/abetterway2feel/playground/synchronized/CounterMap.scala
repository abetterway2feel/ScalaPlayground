package com.abetterway2feel.playground.synchronized

import java.util.concurrent.atomic.AtomicInteger

import scala.collection.concurrent.TrieMap
import scala.concurrent.{Await, Future}

class CounterMap {

  private[this] val map = new TrieMap[String, AtomicInteger]()

  def increment(key: String) = map.synchronized {
    map.get(key) match {
      case None => map.put(key, new AtomicInteger(1))
      case Some(x) =>  x.getAndIncrement()
    }
  }

  def get(key: String) = map.get(key)

}

object CounterMap extends App {

  val map = new CounterMap()

  import scala.concurrent.ExecutionContext.Implicits.global

  val futures = List.newBuilder[Future[Unit]]


  for (x <- 1 to 10) {
    futures.+=(Future {
      for (x <- 1 to 100) {
        map.increment("key-1")
      }
    })
  }

  import scala.concurrent.duration._

  futures.result().foreach {
    Await.result(_, 10.seconds)
  }
  println(map.get("key-1"))

  println(Integer.MAX_VALUE)
}
