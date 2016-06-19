package com.abetterway2feel.predictor.reader.handler

private object ParseStates {

  sealed trait ParseState

  case object IgnoreCell extends ParseState

  case object GameNo extends ParseState

  case object HomeTeamScore extends ParseState

  case object AwayTeamScore extends ParseState

  case object HomeTeamScoreAET extends ParseState

  case object AwayTeamScoreAET extends ParseState

}
