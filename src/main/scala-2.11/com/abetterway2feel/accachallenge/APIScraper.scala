package com.abetterway2feel.accachallenge

import org.json4s.DefaultFormats
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods._

import scalaj.http.Http

object APIScraper extends App {

  new SofaScoreFixtureRetriever().get

}


trait FixtureRetriever {

  def get: Set[Fixture]

}

trait Fixture

case class SofaScoresFixture(homeTeam: String, awayTeam: String) extends Fixture


final class SofaScoreFixtureRetriever extends FixtureRetriever {

  implicit val formats = DefaultFormats

  override def get: Set[Fixture] = {
    val json = Http("http://www.sofascore.com/football//2016-07-06/json").asString.body
    val parsed = parse(json) \ "sportItem" \ "tournaments"

    val fixtures = parsed.children.filter {
      trn => (trn \ "tournament" \ "name").extract[String] == "Euro Cup"
    } map {
      trn =>
        val event = (trn \ "events") (0)
        val homeTeam = (event \ "homeTeam" \ "name").extract[String]
        val awayTeam = (event \ "awayTeam" \ "name").extract[String]
        SofaScoresFixture(homeTeam, awayTeam)
    }

    println(fixtures)

    fixtures.toSet
  }
}