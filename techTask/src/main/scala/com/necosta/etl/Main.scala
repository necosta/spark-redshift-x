package com.necosta.etl

import com.necosta.etl.config.RuntimeConfig
import org.apache.spark.SparkException
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success, Try}

object Main {
    private val logger = LoggerFactory.getLogger(getClass.getName)

    def main(args: Array[String]): Unit = {
        logger.info("Data import started")

        args.foreach(a => logger.info(a))

        val runtimeConf = new RuntimeConfig(args)
        val pipelineResult = Try {
            new Runner(runtimeConf).runPipeline()
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
