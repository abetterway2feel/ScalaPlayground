package com.abetterway2feel.playground.katas.bowling

import com.abetterway2feel.playgorund.katas.bowling.inheritance.StateMachineBowlingGame
import com.abetterway2feel.playgorund.katas.bowling.linkedList.LinkedListBowlingGame
import com.abetterway2feel.playgorund.katas.bowling.witharray.ArrayListBowlingGame
import com.abetterway2feel.playgorund.katas.bowling.{BowlingGame, GameOverException}
import org.scalatest.{FlatSpec, Matchers}

class StateMachineBowlingGameSpec extends FlatSpec with Matchers {

  def stateMachineVersion =  new StateMachineBowlingGame

  def newGame: StateMachineBowlingGame = {
    stateMachineVersion
  }

  "The game score" should "be 4 after a single roll of 4" in {
    newGame.roll(4).score should be(4)
  }

  it should "be 9 after a roll of 4 and a roll of 5" in {
    newGame.roll(4).roll(5).score should be(9)
  }

  it should "be 6 after a single roll of 2 and a roll of 4" in {
    newGame.roll(2).roll(4).score should be(6)
  }

  it should "be 10 after a single strike" in {
    newGame.roll(10).score should be(10)
  }

  it should "be 60 after three strikes and 2 gutters" in {
    newGame.roll(10).roll(10).roll(10).roll(0).roll(0).score should be(60)
  }

  it should "be 29 after a single strike and two rolls" in {
    newGame.roll(10).roll(3).roll(7).roll(5).roll(1).score should be(36)
  }

  it should "be 25 after 4 rolls of 5" in {
    newGame
      .roll(5)
      .roll(5)
      .roll(5)
      .roll(5)
      .roll(0)
      .roll(0)
      .score should be(25)
  }

  it should "be 300 after a perfect game" in {
    newGame
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

  it should "150 after a half perfect game" in {
    newGame
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

  it should "135 after Solo's game" in {
    newGame
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
  it should "141 after Phil's game" in {
    newGame
      .roll(10) //1
      .roll(10) //2
      .roll(10) //3
      .roll(1)
      .roll(7) //4
      .roll(6)
      .roll(3) //5
      .roll(2)
      .roll(1) //6
      .roll(7)
      .roll(2) //7
      .roll(10) // 8
      .roll(9)
      .roll(0) // 9
      .roll(9)
      .roll(1) // 10
      .roll(5) // bonus
      .score should be(141)
  }

  "The game " should "be over after 10 rolls" in {
    intercept[GameOverException] {
      newGame
        .roll(1)
        .roll(1) //1
        .roll(1)
        .roll(1) //2
        .roll(1)
        .roll(1) //3
        .roll(1)
        .roll(1) //4
        .roll(1)
        .roll(1) //5
        .roll(1)
        .roll(1) //6
        .roll(1)
        .roll(1) //7
        .roll(1)
        .roll(1) //8
        .roll(1)
        .roll(1) //9
        .roll(1)
        .roll(1) //10
        .roll(1) //Game Over
    }
  }


}
