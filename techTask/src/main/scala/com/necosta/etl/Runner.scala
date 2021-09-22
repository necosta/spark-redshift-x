package com.necosta.etl

import org.slf4j.LoggerFactory

class Runner extends WithSpark {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  def runPipeline(): Unit = {
    import spark.implicits._

    logger.info("Start running job...")

    // ToDo: Add pipeline steps here...
    // ToDo: Remove this sample code
    val sourceDF = List("abc", "def").toDF("name")
    logger.info("Count: " + sourceDF.count())
  }
}
