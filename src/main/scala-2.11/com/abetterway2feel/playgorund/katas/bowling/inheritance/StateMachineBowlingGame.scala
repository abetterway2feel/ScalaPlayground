package com.abetterway2feel.playgorund.katas.bowling.inheritance

import com.abetterway2feel.playgorund.katas.bowling.{BowlingGame, GameOverException}

class StateMachineBowlingGame extends BowlingGame {

  private[this] var state: FrameState = NotStarted

  def printState:this.type = {
    println(state)
    this
  }
  override def score: Int = state.score

  override def roll(pins: Int) = {
    if (isGameOver) throw GameOverException("Game over")
    val newScore = state.score + pins

    def getPreviousBonus(current: FrameComplete): Int = {
      val frameScore = current.roll1 + current.roll2

      (current.previous map {
        case strike@Strike(frameNo) if frameNo <= 10 =>
          frameScore + (strike.previous map {
            case previousStrike @ Strike(_) =>
              current.roll1
            case _  => 0
          }).get
        case spare @ Spare(frameNo) if frameNo <= 10 =>
          current.roll1
        case _ => 0
      }).get

    }

    state = state match {
      case FirstThrow(10, score, first, previous) =>
        if (first + pins >= 10)
          BonusShot(newScore)
        else
          GameOver(newScore)

      case BonusShot(_) =>
        GameOver(newScore)

      case gameOver: GameOver =>
        gameOver

      case current@FrameComplete(frameNo, score, roll1, roll2, previous) =>
        getNextState(frameNo + 1,
          newScore + getPreviousBonus(current),
          pins, None, Some(current))

      case FirstThrow(frameNo, score, roll1, previous) =>
        getNextState(frameNo, newScore, roll1, Some(pins), previous)

      case NotStarted =>
        getNextState(1, newScore, pins, None, Some(NotStarted))
    }

    this
  }

  def getNextState(currentFrame: Int, newScore: Int, roll1: Int, roll2: Option[Int] = None, previous: Option[FrameState]): FrameState = {
    roll2.fold({
      roll1 match {
        case 10 =>
          FrameComplete(currentFrame, newScore, 10, 0, previous)
        case _ =>
          FirstThrow(currentFrame, newScore, roll1, previous)
      }
    })(
      r2 => {
        FrameComplete(currentFrame, newScore, roll1, r2, previous)
      }
    )
  }

  def isGameOver: Boolean = {
    state match {
      case GameOver(score) => true
      case _ => false
    }
  }

}


sealed trait FrameState {
  val frameNo: Int
  val score: Int
  val previous: Option[FrameState] = None
}

case class FirstThrow(frameNo: Int,
                      score: Int,
                      roll1: Int,
                      override val previous: Option[FrameState]) extends FrameState

case class FrameComplete(frameNo: Int,
                         score: Int,
                         roll1: Int,
                         roll2: Int,
                         override val previous: Option[FrameState]) extends FrameState {
  def isStrike = roll1 == 10

  def isSpare = !isStrike && roll1 + roll2 == 10
}

case class BonusShot(score: Int) extends FrameState {
  override val frameNo: Int = 10
}

case class GameOver(score: Int) extends FrameState {
  override val frameNo: Int = 10
}

object NotStarted extends FrameState {
  override val frameNo: Int = 0
  override val score: Int = 0
}


object Strike {
  def unapply(frame: FrameComplete): Option[Int] = {
    if(frame.roll1 == 10) {
      Some(frame.frameNo)
    }
    else {
      None
    }
  }
}

object Spare {
  def unapply(frame: FrameComplete): Option[Int] = {
    if(frame.roll1 < 10 & (frame.roll1 + frame.roll2) == 10) {
      Some(frame.frameNo)
    }
    else {
      None
    }
  }
}