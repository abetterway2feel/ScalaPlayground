package com.abetterway2feel.playground.katas.bowling

trait BowlingGame {
  def score: Int
  def roll(pins: Int): this.type

}
