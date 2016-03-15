package com.abetterway2feel.playgorund.katas.bowling

trait BowlingGame {
  def score: Int
  def roll(pins: Int): BowlingGame

}
