package com.abetterway2feel.playground.katas.bowling

import com.abetterway2feel.playgorund.katas.bowling.simple.{GameOverException, Frame, BowlingGame}
import org.scalatest.{Matchers, FlatSpec}

class BowlingGameSpec extends FlatSpec with Matchers {

  "Bowling Game" should "store 4 as roll1 and None as roll2" in {
    new BowlingGame().roll(4).score should be(4)
  }

  it should "store 4 as roll1 and 5 as roll2 and game score as 9" in {
    new BowlingGame().roll(4).roll(5).score should be(9)
  }

  it should "store 2 as roll1 and 4 as roll2 and game score as 6" in {
    new BowlingGame().roll(2).roll(4).score should be(6)
  }

  it should "store 10 as roll1 and None as roll2 when the player gets a strike" in {
    new BowlingGame().roll(10).score should be(10)
  }

  it should "store 10 as roll1 and 20 as roll2" in {
    new BowlingGame().roll(10).roll(10).score should be(30)
  }

  it should "calculate the score for two spares correctly" in {
    new BowlingGame()
      .roll(5)
      .roll(5)
      .roll(5)
      .roll(5)
      .score should be(25)
  }

  it should "too many rolls will result in a game over exception " in {
    intercept[GameOverException] {
      new BowlingGame()
        .roll(10)
        .roll(10)
        .roll(10)
        .roll(10)
        .roll(10)
        .roll(10)
        .roll(10)
        .roll(10)
        .roll(10)
        .roll(1)
        .roll(1)
        .roll(1)

    }
  }

  it should "calculate a perfect game correctly" in {
    new BowlingGame()
      .roll(10)
      .roll(10)
      .roll(10)
      .roll(10)
      .roll(10)
      .roll(10)
      .roll(10)
      .roll(10)
      .roll(10)
      .roll(10)
      .roll(10)
      .roll(10)
      .score should be(300)
  }

  it should "calculate a half perfect game correctly" in {
    new BowlingGame()
      .roll(5)
      .roll(5) //1
      .roll(5)
      .roll(5) //2
      .roll(5)
      .roll(5) //3
      .roll(5)
      .roll(5) //4
      .roll(5)
      .roll(5) //5
      .roll(5)
      .roll(5) //6
      .roll(5)
      .roll(5) //7
      .roll(5)
      .roll(5) //8
      .roll(5)
      .roll(5) //9
      .roll(5)
      .roll(5) //10
      .roll(5)

      .score should be(150)
  }

  it should "calculate Solo's game correctly" in {
    new BowlingGame()
      .roll(7)
      .roll(2) //1
      .roll(7)
      .roll(2) //2
      .roll(5)
      .roll(2) //3
      .roll(4)
      .roll(6) //4
      .roll(9)
      .roll(1) //5
      .roll(7)
      .roll(3) //6
      .roll(5)
      .roll(5) //7
      .roll(2)
      .roll(0) //8
      .roll(9)
      .roll(1) //9
      .roll(10)
      .roll(10) //10
      .roll(5)

      .score should be(135)
  }

}
