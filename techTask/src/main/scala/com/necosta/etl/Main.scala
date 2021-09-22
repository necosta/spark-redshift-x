package com.necosta.etl

import org.apache.spark.SparkException
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success, Try}

object Main {
    private val logger = LoggerFactory.getLogger(getClass.getName)

    def main(args: Array[String]): Unit = {
        logger.info("Data import started")

        val pipelineResult = Try {
            new Runner().runPipeline()
        }

        pipelineResult match {
            case Success(_) => logger.info("Data import completed")
            case Failure(err) => logger.error(
                s"Data import failed\n" +
                  s"Error msg: ${err.getMessage}\n" +
                  s"Stack trace: ${err.getStackTrace.mkString(System.lineSeparator())}")
            throw new SparkException(err.getMessage, err)
        }
    }
}
