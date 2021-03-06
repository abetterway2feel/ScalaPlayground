package com.abetterway2feel.playground.threads.forkjoin


import java.util.concurrent.ForkJoinTask

import scala.concurrent.forkjoin.ForkJoinPool

object ForkJoinThreads extends App {
  private val commonPool: ForkJoinPool = ForkJoinPool.commonPool()


  println(commonPool.getActiveThreadCount)
  println(commonPool.getPoolSize)
  println(commonPool.getStealCount)


}
