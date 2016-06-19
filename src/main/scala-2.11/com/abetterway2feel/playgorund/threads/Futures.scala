package com.abetterway2feel.playgorund.threads

import java.util.concurrent.{Executors, ForkJoinPool, ThreadPoolExecutor, Executor}

import scala.concurrent.forkjoin.ForkJoinPool
import scala.concurrent.{Future, ExecutionContext}
import scala.util.Success

object Futures extends App {

//  implicit val currentThreadExecutionContext = ExecutionContext.fromExecutor(
//    new Executor {
//      def execute(runnable: Runnable) { runnable.run() }
//    })

   implicit val currentThreadExecutionContext = ExecutionContext.fromExecutor(
     Executors.newCachedThreadPool()
   )


  val f = Future {
    2 / 1
  }
  for (exc <- f.failed) println(exc)

  f.onSuccess(PartialFunction(println))
}
