package com.abetterway2feel.predictor.reader

import com.abetterway2feel.predictor.model.Prediction
import com.abetterway2feel.predictor.reader.handler.{GroupStageHandler, KnockOutStageHandler}
import org.apache.poi.openxml4j.opc.{OPCPackage, PackageAccess}
import org.apache.poi.xssf.eventusermodel.XSSFReader
import org.apache.poi.xssf.model.SharedStringsTable
import org.xml.sax.InputSource
import org.xml.sax.helpers.XMLReaderFactory

final class ExcelPredictionReader extends PredictionReader {

  def readFile(filename: String) = {
    val groupPredictions = getGroupPredictions(filename)
    val knockoutPredictions = getKnockOutPredictions(filename)
    val predictions: List[Prediction] = groupPredictions ++ knockoutPredictions
    println(s"Predictions: ${predictions}")
    predictions
  }

  @throws(classOf[Exception])
  def getGroupPredictions(filename: String): List[Prediction] = {
    val pkg: OPCPackage = OPCPackage.open(filename, PackageAccess.READ)
    try {
      val r: XSSFReader = new XSSFReader(pkg)
      val sst: SharedStringsTable = r.getSharedStringsTable
      val handler = new GroupStageHandler(sst)
      val parser = XMLReaderFactory.createXMLReader
      parser.setContentHandler(handler)
      val sheet = r.getSheet("rId4")
      val sheetSource = new InputSource(sheet)
      parser.parse(sheetSource)
      sheet.close()
      handler.predictions()
    } finally {
      pkg.close()
    }
  }

  @throws(classOf[Exception])
  def getKnockOutPredictions(filename: String): List[Prediction] = {
    val pkg: OPCPackage = OPCPackage.open(filename, PackageAccess.READ)
    try {
      val r: XSSFReader = new XSSFReader(pkg)
      val sst: SharedStringsTable = r.getSharedStringsTable
      val handler = new KnockOutStageHandler(sst)
      val parser = XMLReaderFactory.createXMLReader
      parser.setContentHandler(handler)
      val sheet = r.getSheet("rId4")
      val sheetSource = new InputSource(sheet)
      parser.parse(sheetSource)
      sheet.close()
      handler.predictions()
    } finally {
      pkg.close()
    }
  }

}

object firstTest {
  def main(args: Array[String]) {
    new ExcelPredictionReader().readFile("/Users/philquinn/Development/ScalaPlayground/src/main/resources/PhilQuinn_Euro_2016_Predictor.xlsm")

  }
}
