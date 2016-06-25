package com.abetterway2feel.playground.katas.bowling

import com.abetterway2feel.playground.katas.bowling.inheritance.StateMachineBowlingGame2
import com.abetterway2feel.playground.katas.bowling.linkedList.LinkedListBowlingGame
import com.abetterway2feel.playground.katas.bowling.witharray.ArrayListBowlingGame
import org.scalatest.{FlatSpec, Matchers}

class BowlingGameSpec extends FlatSpec with Matchers {

  def linkedListVersion = new LinkedListBowlingGame

  def arrayListVersion = new ArrayListBowlingGame

  def stateMachineVersion = new StateMachineBowlingGame2

  def systemUnderTest: BowlingGame = stateMachineVersion


  "The game score" should "be 4 after a single roll of 4" in {
    systemUnderTest.roll(4).score should be(4)
  }

  it should "be 9 after a roll of 4 and a roll of 5" in {
    systemUnderTest.roll(4).roll(5).score should be(9)
  }

  it should "be 6 after a single roll of 2 and a roll of 4" in {
    systemUnderTest.roll(2).roll(4).score should be(6)
  }

  it should "be 10 after a single strike" in {
    systemUnderTest.roll(10).score should be(10)
  }

  it should "be 30 after two strikes" in {
    systemUnderTest.roll(10).roll(10).score should be(30)
  }

  it should "be 41 after a single strike, a spare(3,7) and two rolls (5,1)" in {
    systemUnderTest.roll(10).roll(3).roll(7).roll(5).roll(1).score should be(41)
  }

  it should "be 25 after 4 rolls of 5" in {
    systemUnderTest
      .roll(5)
      .roll(5)
      .roll(5)
      .roll(5)
      .score should be(25)
  }

  it should "be 300 after a perfect game" in {
    systemUnderTest
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
    systemUnderTest
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

      .score should be(145)
  }

  it should "135 after Solo's game" in {
    systemUnderTest
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
    systemUnderTest
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
    systemUnderTest
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
      .roll(1)
      .score should be(21)

  }

}
