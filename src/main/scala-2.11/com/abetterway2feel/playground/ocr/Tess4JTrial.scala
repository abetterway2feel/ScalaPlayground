package com.abetterway2feel.playground.ocr

import java.io.File

import net.sourceforge.tess4j.{TesseractException, Tesseract}

object Tess4JTrial extends App{

  val image = new File("/Users/philquinn/Development/ScalaPlayground/src/main/resources/hills.png")
  val tesseract = new Tesseract()
  tesseract.setDatapath("/usr/local/Cellar/tesseract/3.04.01_2/share")

  try {

    val result = tesseract.doOCR(image)
    println(result)

  } catch {
    case e: TesseractException =>
      println(e.getMessage)
  }


}
