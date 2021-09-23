package com.necosta.etl

import com.necosta.etl.config.RuntimeConfig
import com.necosta.etl.step.{Reader, Writer}
import org.slf4j.LoggerFactory

class Runner(runtimeConf: RuntimeConfig) extends WithSpark {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  def runPipeline(): Unit = {
    import spark.implicits._

    logger.info("Started running workflow...")

    // Set S3 credentials
    sc.hadoopConfiguration.set("fs.s3a.access.key", runtimeConf.s3AccessKey())
    sc.hadoopConfiguration.set("fs.s3a.secret.key", runtimeConf.s3SecretKey())

    // ToDo: Add pipeline steps here...

    // Test Spark Redshift writer
    val dfWriter = List("abc", "def")
      .toDF("name")

    new Writer(runtimeConf).write(dfWriter)

    logger.info(s"Wrote ${dfWriter.count()} rows.")

    // Test Spark Redshift reader
    val dfReader = new Reader(runtimeConf).read()
    logger.info(s"Read ${dfReader.count()} rows.")

    logger.info("Finished running workflow")
  }
}
