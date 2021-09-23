package com.necosta.etl

import com.necosta.etl.step.{Reader, Writer}
import org.slf4j.LoggerFactory

class Runner extends WithSpark {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  def runPipeline(): Unit = {
    import spark.implicits._

    logger.info("Started running workflow...")

    // ToDo: Add pipeline steps here...

    // Test Spark Redshift writer
    val dfWriter = List("abc", "def")
      .toDF("name")

    new Writer().write(dfWriter)

    logger.info(s"Wrote ${dfWriter.count()} rows.")

    // Test Spark Redshift reader
    val dfReader = new Reader().read()
    logger.info(s"Read ${dfReader.count()} rows.")

    logger.info("Finished running workflow")
  }
}
