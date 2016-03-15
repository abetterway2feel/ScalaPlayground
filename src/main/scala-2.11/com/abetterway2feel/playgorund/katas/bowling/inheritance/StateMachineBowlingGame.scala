package com.abetterway2feel.playgorund.katas.bowling.inheritance

import com.abetterway2feel.playgorund.katas.bowling.{BowlingGame, GameOverException}

class StateMachineBowlingGame extends BowlingGame {

  private[this] var state: Frame = NotStarted

  override def score: Int = state.score

  override def roll(pins: Int): BowlingGame = {
    if (isGameOver) throw GameOverException("Game over")
    val newScore = state.score + pins

    def getBonus(frame: Frame, currentState: Boolean): Int = {
      frame match {
        case NotStarted => 0
        case FrameComplete(frameNo,_, roll1, roll2, _) if currentState & frameNo < 10 =>
          roll1 + roll2 match {
            case 10 => pins
            case other => 0
          }

        case FrameComplete(frameNo,_, 10, 0, _) if !currentState & frameNo < 10 =>
          pins

        case other =>
          0
      }
    }

    state = state match {
      case FirstThrow(10, score, first, previous) =>
        if (first + pins >= 10)
          BonusShot(newScore)
        else
          GameOver(newScore)

      case BonusShot(_)  =>
        GameOver(newScore)
      case gameOver: GameOver =>
        gameOver
      case current @ FrameComplete(frameNo, score, roll1, roll2, previous) =>
        getNextState(frameNo + 1 ,
          newScore + getBonus(previous, currentState = false) + getBonus(current, currentState = true),
          pins, None, current)

      case FirstThrow(frameNo, score, roll1, previous) =>
        getNextState(frameNo, newScore, roll1, Some(pins), previous)

      case NotStarted =>
        getNextState(1, newScore, pins, None, NotStarted)
    }

    this
  }

  def getNextState(currentFrame: Int, newScore: Int, roll1: Int, roll2: Option[Int] = None, previous: Frame): Frame = {
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


sealed trait Frame {
  val frameNo: Int
  val score: Int
}

case class FirstThrow(frameNo: Int,
                      score: Int,
                      roll1: Int,
                      previous: Frame) extends Frame

case class FrameComplete(frameNo: Int,
                         score: Int,
                         roll1: Int,
                         roll2: Int,
                         previous: Frame) extends Frame

case class BonusShot(score: Int) extends Frame {
  override val frameNo: Int = 10
}

case class GameOver(score: Int) extends Frame {
  override val frameNo: Int = 10
}

object NotStarted extends Frame {
  override val frameNo: Int = 0
  override val score: Int = 0
}
