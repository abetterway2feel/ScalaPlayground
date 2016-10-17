//package com.abetterway2feel.playground.algorithms.sorting
//
//import scala.collection.immutable.Stream.Empty
//
//object Sorter {
//
//  def mergeSort[T](array: Array[T]): Array[T] = {
//    if (array.length < 2) {
//      array
//    }
//    else {
//      val pivot = array.length / 2
//      val splitArray: (Array[T], Array[T]) = array.splitAt(pivot)
//      mergeSort(splitArray._1) ++ mergeSort(splitArray._2)
//    }
//  }
//
//  def mergeWithStreams(first: Stream[Int], second: Stream[Int]): Stream[Int] =
//    (first, second) match {
//      case (x #:: xs, ys@(y #:: _)) if x <= y => x #:: mergeWithStreams(xs, ys)
//      case (xs, y #:: ys) => y #:: mergeWithStreams(xs, ys)
//      case (xs, Empty) => xs
//      case (Empty, ys) => ys
//    }
//
//}
