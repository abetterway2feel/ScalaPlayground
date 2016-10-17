package com.abetterway2feel.playground.ocr

import java.io.File

import com.asprise.ocr.Ocr

object OCR extends App {

  Ocr.setUp(); // one time setup
  var ocr = new Ocr(); // create a new OCR engine
  ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English

  var asString = ocr.recognize(
    Array[File](new File("/Users/philquinn/Development/ScalaPlayground/src/main/resources/acca.png")),
    Ocr.RECOGNIZE_TYPE_ALL,
    Ocr.OUTPUT_FORMAT_PLAINTEXT
  )
  System.out.println("Result: " + asString);

  ocr.stopEngine();

}