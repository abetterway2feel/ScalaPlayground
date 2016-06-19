package com.abetterway2feel.predictor.reader.handler

import com.abetterway2feel.predictor.model.{PredictedGameResult, Prediction}
import com.abetterway2feel.predictor.reader.handler.ParseStates.ParseState
import com.abetterway2feel.predictor.reader.handler.ParseStates._
import org.apache.poi.xssf.model.SharedStringsTable
import org.xml.sax.helpers.DefaultHandler
import org.xml.sax.{Attributes, SAXException}


final class GroupStageHandler(private val sst: SharedStringsTable) extends DefaultHandler {
  private[this] var lastContents = ""
  private[this] var currentPrediction = GroupPrediction()
  private[this] var state: ParseState = IgnoreCell
  private[this] var _predictions: List[GroupPrediction] = List.empty[GroupPrediction]


  def predictions(): List[Prediction] = {
    _predictions.map {
      p =>
        Prediction(p.gameNo, PredictedGameResult(p.homeTeamScore, p.awayTeamScore))
    }
  }

  private[this] case class GroupPrediction(
    gameNo: Int = -1,
    homeTeamScore: Int = 0,
    awayTeamScore: Int = 0
  )

  @throws(classOf[SAXException])
  override def startElement(uri: String, localName: String, name: String, attributes: Attributes) {
    if (name == "c") {
      val cellReference = attributes.getValue("r")
      val row = cellReference.filter(_.isDigit).toInt
      val column = cellReference.filterNot(_.isDigit)
      if(row > 9 && row < 46){
        state = column match {
          case "A" =>
            GameNo
          case "F" =>
            HomeTeamScore
          case "G" =>
            AwayTeamScore
          case _ =>
            IgnoreCell
        }
      }
    }
    lastContents = ""
  }

  @throws(classOf[SAXException])
  override def endElement(uri: String, localName: String, name: String) {
    if (name == "v") {
      state match {
        case GameNo =>
          currentPrediction = currentPrediction.copy(gameNo = lastContents.toInt)
        case HomeTeamScore =>
          currentPrediction = currentPrediction.copy(homeTeamScore = lastContents.toInt)
        case AwayTeamScore =>
          currentPrediction = currentPrediction.copy(awayTeamScore = lastContents.toInt)
          _predictions ++= List(currentPrediction)
          currentPrediction = GroupPrediction()
        case _ =>
      }
    }
  }

  @throws(classOf[SAXException])
  override def characters(ch: Array[Char], start: Int, length: Int) = {
    if(state != IgnoreCell) {
      lastContents += new String(ch, start, length)
    }
  }
}