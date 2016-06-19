package com.abetterway2feel.predictor.reader.handler

import com.abetterway2feel.predictor.model._
import com.abetterway2feel.predictor.reader.handler.ParseStates.{ParseState, _}
import org.apache.poi.xssf.model.SharedStringsTable
import org.xml.sax.helpers.DefaultHandler
import org.xml.sax.{Attributes, SAXException}

final class KnockOutStageHandler(private val sst: SharedStringsTable) extends DefaultHandler {
  private[this] var lastContents = ""
  private[this] var cellReference = ""
  private[this] var currentPrediction = KnockOutPrediction()
  private[this] var state: ParseState = IgnoreCell
  private[this] var _predictions = List.empty[KnockOutPrediction]


  def predictions(): List[Prediction] = {
    _predictions.map {
      p =>
        (p.homeTeamGoalsAET, p.awayTeamGoalsAET) match {
          case (homeGoals, awayGoals) if homeGoals > 0 =>
            Prediction(p.gameNo, PredictedGameResult(p.homeTeamScore, p.awayTeamScore, HomeTeam))
          case (homeGoals, awayGoals) if awayGoals > 0 =>
            Prediction(p.gameNo, PredictedGameResult(p.homeTeamScore, p.awayTeamScore, AwayTeam))
          case other =>
            Prediction(p.gameNo, PredictedGameResult(p.homeTeamScore, p.awayTeamScore))
        }
    }.sortBy(_.gameNo)
  }

  private[this] case class KnockOutPrediction(
    gameNo: Int = -1,
    homeTeamScore: Int = -1,
    awayTeamScore: Int = -1,
    homeTeamGoalsAET: Int = -1,
    awayTeamGoalsAET: Int = -1
  )

  @throws(classOf[SAXException])
  override def startElement(uri: String, localName: String, name: String, attributes: Attributes) {
    if (name == "c") {
      cellReference = attributes.getValue("r")
      val row = cellReference.filter(_.isDigit).toInt
      val column = cellReference.filterNot(_.isDigit)

      val gameNoColumns = List("DD", "DJ", "DP", "DV")
      val afterNinetyMinColumns = List("DF", "DL", "DR", "DX")
      val afterExtraTimeColumns = List("DG", "DM", "DO", "DY")

      if (row > 9 && row < 46) {
        state = column match {
          case col if gameNoColumns.contains(col) =>
            GameNo

          case col if afterNinetyMinColumns.contains(col) =>
            currentPrediction.homeTeamScore match {
              case -1 => HomeTeamScore
              case other => AwayTeamScore
            }

          case col if afterExtraTimeColumns.contains(col) =>
            currentPrediction.homeTeamGoalsAET match {
              case -1 => HomeTeamScoreAET
              case other => AwayTeamScoreAET
            }

          case _ =>
            IgnoreCell
        }
      }
      else {
        if(currentPrediction.gameNo > 0) { //get the last one
          _predictions ++= List(currentPrediction)
          currentPrediction = KnockOutPrediction()
        }
      }
    }
    lastContents = ""
  }

  @throws(classOf[SAXException])
  override def endElement(uri: String, localName: String, name: String) {
    if (name == "v") {
      if(lastContents.nonEmpty) {
//        if(!state.equals(IgnoreCell)){
//          println(s"$cellReference: $state: $lastContents")
//        }
        state match {
          case GameNo if lastContents.charAt(0).isDigit =>
            if(currentPrediction.gameNo > 0) _predictions ++= List(currentPrediction)
            currentPrediction = KnockOutPrediction().copy(gameNo = lastContents.toInt)

          case HomeTeamScore if currentPrediction.homeTeamScore < 0 =>
            currentPrediction = currentPrediction.copy(homeTeamScore = lastContents.toInt)

          case AwayTeamScore if currentPrediction.awayTeamScore < 0 =>
            currentPrediction = currentPrediction.copy(awayTeamScore = lastContents.toInt)
          case HomeTeamScoreAET if currentPrediction.homeTeamGoalsAET < 0 =>
            lastContents.charAt(0) match {
              case goals if goals.isDigit =>
                currentPrediction = currentPrediction.copy(homeTeamGoalsAET = lastContents.toInt)
              case other =>
                currentPrediction = currentPrediction.copy(homeTeamGoalsAET = 0)
            }
          case AwayTeamScoreAET if currentPrediction.awayTeamGoalsAET < 0 =>
            lastContents.charAt(0) match {
              case goals if goals.isDigit =>
                currentPrediction = currentPrediction.copy(awayTeamGoalsAET = lastContents.toInt)
              case other =>
                currentPrediction = currentPrediction.copy(awayTeamGoalsAET = 0)
            }
          case _ =>
        }
      }
    }
  }

  @throws(classOf[SAXException])
  override def characters(ch: Array[Char], start: Int, length: Int) = {
    if (state != IgnoreCell) {
      lastContents += new String(ch, start, length)
    }
  }
}