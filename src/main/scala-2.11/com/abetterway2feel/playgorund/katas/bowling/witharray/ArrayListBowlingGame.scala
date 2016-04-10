package com.abetterway2feel.playgorund.katas.bowling.witharray

import com.abetterway2feel.playgorund.katas.bowling.{GameOverException, BowlingGame}

class ArrayListBowlingGame() extends BowlingGame {

  private[this] val frames: Array[Frame] = Array.fill(12)(Frame())

  private[this] var currentFrameNumber = 0

  override def score: Int = {
    var frameNumber = 0
    def calculateBonus(frame: Frame): Int = {
      if (frame.isStrike) {
        val nextFrameNumber = frameNumber + 1
        val frameNumberAfterThat = frameNumber + 2
        + (if (nextFrameNumber < 10) frames(nextFrameNumber).score else 0) +
          (if (frameNumberAfterThat < 11) frames(frameNumberAfterThat).score else 0)
      }
      else if (frame.isSpare) {
        val nextFrameNumber = frameNumber + 1
        if (nextFrameNumber < 10) frames(nextFrameNumber).roll1.fold(0)(x => x) else 0
      }
      else 0
    }

    frames.foldLeft(0) {
      (agg, frame) =>
        val result = agg + frame.score + calculateBonus(frame)
        frameNumber += 1
        result
    }
  }

  override def roll(pins: Int) = {
    if (isGameOver) throw GameOverException("Game over")

    val currentFrame = frames(currentFrameNumber)

    currentFrame.roll1.fold {
      val frame: Frame = Frame(
        roll1 = Some(pins),
        roll2 = None
      )
      frames(currentFrameNumber) = frame
      if (frame.isStrike) {
        incrementFrameCounter()
      }
    } {
      roll1 =>
        val frame = Frame(
          roll1 = currentFrame.roll1,
          roll2 = Some(pins)
        )
        frames(currentFrameNumber) = frame
        incrementFrameCounter()
    }
    this

  }

  def isGameOver: Boolean = {
    currentFrameNumber > 11 &&
    (currentFrameNumber > 10 && !frames(9).isStrike) || (currentFrameNumber > 9
      && !frames(9).isStrike && !frames(9).isSpare)
  }

  def incrementFrameCounter(): Unit = {
    currentFrameNumber += 1
  }
}

case class Frame(roll1: Option[Int] = None, roll2: Option[Int] = None) {
  val isStrike = roll1.fold(false)(x => x == 10)

  val isSpare: Boolean = !isStrike && score == 10

  def score = {
    roll1.fold(0)(roll1 => roll1 + roll2.fold(0)(roll2 => roll2))
  }
}

