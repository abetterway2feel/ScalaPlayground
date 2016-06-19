package com.abetterway2feel.predictor.model

sealed trait GameResult

case object NoResult extends GameResult

case class PredictedGameResult(
  homeGoals: Int,
  awayGoals: Int,
  winnerAET: WinnerAET = NoExtraTime
) extends GameResult

case class ActualGameResult(
  homeGoals: Int,
  awayGoals: Int,
  homeGoalsAfterExtraTime: Option[Int] = None,
  awayGoalsAfterExtraTime: Option[Int] = None,
  homePenaltiesScored: Option[Int] = None,
  awayPenaltiesScored: Option[Int] = None,
  winnerAET: WinnerAET = NoExtraTime
) extends GameResult


sealed trait WinnerAET

case object NoExtraTime extends WinnerAET

case object HomeTeam extends WinnerAET

case object AwayTeam extends WinnerAET