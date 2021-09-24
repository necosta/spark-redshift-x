package com.necosta.etl

import com.necosta.etl.config.{AwsConfig, RuntimeConfig}
import com.necosta.etl.step.{Importer, Reader, Writer}
import org.slf4j.LoggerFactory

class Runner(runtimeConf: RuntimeConfig) {

  private val logger = LoggerFactory.getLogger(getClass.getName)

  def runPipeline(): Unit = {

    logger.info("Started running workflow...")

    new AwsConfig(runtimeConf).setAwsS3Creds

    val importer = new Importer()
    val reader = new Reader(runtimeConf)
    val writer = new Writer(runtimeConf)

    // For each dimension/fact file...
    // ToDo: Fill remaining tables
    Seq("DimAccount.csv").foreach(fileName => {
      logger.info(s"Started importing $fileName")
      val fileNameWithoutExtension = getFileNameWithoutExtension(fileName)

      logger.info(s"Convert $fileName into dataframe")
      val df = reader.readFromCsv(fileName)

      logger.info(s"Dataframe $fileName has ${df.count()} rows.")

      writer.write(df, fileNameWithoutExtension)

      logger.info(s"Finished importing $fileName")
    })

    logger.info("Finished running workflow")
  }

  def getFileNameWithoutExtension(fileName: String): String = {
    val indexOfDot = fileName.indexOf(".")
    if(indexOfDot > 0) fileName.substring(0, indexOfDot)
    else fileName
  }
}
