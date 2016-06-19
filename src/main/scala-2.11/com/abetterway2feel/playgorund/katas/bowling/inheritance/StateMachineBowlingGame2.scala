package com.abetterway2feel.playgorund.katas.bowling.inheritance

import com.abetterway2feel.playgorund.katas.bowling.BowlingGame
import com.abetterway2feel.playgorund.katas.bowling.inheritance.FrameStates.{FrameRoll, FrameState, GameNotStarted, GameOver}

final class StateMachineBowlingGame2 extends BowlingGame {

  private[this] var frameState: FrameState = GameNotStarted

  override def score: Int = frameState match {
    case GameNotStarted => 0
    case GameOver(score) => score
    case FrameRoll(_, score, _, _, _) => score
  }

  override def roll(pins: Int) = {

    frameState = frameState match {
      case GameNotStarted =>
        FrameRoll(1, pins, 1, pins, GameNotStarted)

      case FrameRoll(frameNo, score, rollNo, pinsInRoll, previousState) =>
        def bonus() = {
          val bonusForPreviousPrevious = previousState match {
            case FrameRoll(frameNoForPreviousRoll, _, 1, 10, _) if frameNoForPreviousRoll <= 10 => pins
            case other => 0
          }

          val bonusForPrevious = pinsInRoll match {
            case 10 => pins
            case other => 0
          }

          val bonusForSpare = rollNo match {
            case 1 => 0
            case 2 => previousState match {
              case FrameRoll(frameNoForPreviousRoll, _, 1, pinsInPreviousRoll, _)
                if (frameNoForPreviousRoll <= 10) &&(pinsInRoll + pinsInPreviousRoll == 10) => pins
              case other => 0
            }
          }

          bonusForPreviousPrevious + bonusForPrevious + bonusForSpare
        }

        val newScore = score + pins + bonus()
        rollNo match {
          case 1 =>
            pinsInRoll match {
              case 10 =>
                FrameRoll(frameNo + 1, newScore, 1, pins, frameState)
              case other =>
                FrameRoll(frameNo, newScore, 2, pins, frameState)
            }

          case 2 =>
            frameState
        }
      case GameOver(score) =>
        frameState
    }


    this
  }
}


object FrameStates {

  sealed trait FrameState

  case object GameNotStarted extends FrameState

  case class FrameRoll(frameNo: Int, score: Int, rollNo: Int, pins: Int, previousState: FrameState) extends FrameState

  case class GameOver(score: Int) extends FrameState

}
