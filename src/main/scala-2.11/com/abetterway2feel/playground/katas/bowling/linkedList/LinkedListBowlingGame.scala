package com.abetterway2feel.playground.katas.bowling.linkedList

import com.abetterway2feel.playground.katas.bowling.{GameOverException, BowlingGame}

class LinkedListBowlingGame() extends BowlingGame {

  private[this] var firstFrame: Option[Frame] = None
  private[this] var currentFrame: Option[Frame] = None

  override def score: Int = {
    var someFrame = firstFrame
    var score = 0
    while (someFrame.isDefined) {
      val frame = someFrame.get
      score += frame.score
      someFrame = frame.nextFrame
    }
    score
  }

  override def roll(pins: Int) = {
    if (isGameOver) throw GameOverException("Game over")

    currentFrame.fold {
      val frame = Frame(frame = 1, roll1 = pins, roll2 = None)
      currentFrame = Some(frame)
      if (firstFrame.isEmpty) firstFrame = currentFrame
    } {
      current => {
        if (current.isStrike | current.roll2.isDefined) {
          val frame = Frame(frame = current.frame + 1, roll1 = pins, roll2 = None)
          current.nextFrame = Some(frame)
          if(current.frame < 12) currentFrame = Some(frame)
          else currentFrame = None
        }
        else {
          current.roll2 = Some(pins)
          if(current.frame >= 10 && !current.isSpare) currentFrame = None
        }
      }
    }
    this

  }

  def isGameOver: Boolean = firstFrame.isDefined && currentFrame.isEmpty

}

final case class Frame(val frame: Int,
                       val roll1: Int,
                       var roll2: Option[Int] = None,
                       var nextFrame: Option[Frame] = None
                      ) {

  val isStrike = roll1 == 10
  def isSpare: Boolean = !isStrike && rawScore == 10

  def score = rawScore + (if(frame < 10) bonus else 0)

  private[Frame] def rawScore: Int = roll1 + roll2.fold(0)(identity)

  private[Frame] def bonus: Int = {
    if (isStrike)
      nextFrame.fold(0)(
        next =>
          next.rawScore + (if (next.isStrike)
            next.nextFrame.fold(0)(
              oneAfter => oneAfter.roll1
            ) else 0)
      )
    else if (isSpare)
      nextFrame.fold(0){
        next =>
          if (next.frame < 11)
            next.roll1
          else 0
      }
    else
      0
  }

}