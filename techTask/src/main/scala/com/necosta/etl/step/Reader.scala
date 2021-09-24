package com.necosta.etl.step

import com.necosta.etl.WithSpark
import com.necosta.etl.config.RuntimeConfig
import org.apache.spark.SparkFiles
import org.apache.spark.sql.DataFrame

class Reader(runtimeConf: RuntimeConfig) extends Utils with WithSpark {

  def readFromCsv(csvFileName: String): DataFrame = {
    val url = s"$ddlFilePath/$csvFileName"
    spark.sparkContext.addFile(url)

    spark.read
      .option("header", "false")
      .option("inferSchema", "true")
      .option("delimiter", "|")
      .option("charset", "UTF-8")
      .csv(SparkFiles.get(csvFileName))
  }

  def readFromRedshift(): DataFrame = {
    spark.read
      .format(sourceFormat)
      .option("url", runtimeConf.jdbcUrl())
      .option("query", s"select * from $targetTable")
      .option("user", runtimeConf.dbUser())
      .option("password", runtimeConf.dbPassword())
      .option("tempdir", runtimeConf.s3TempDir())
      .option("forward_spark_s3_credentials", "true")
      .load()
  }
}
