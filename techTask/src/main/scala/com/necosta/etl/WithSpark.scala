package com.necosta.etl

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

trait WithSpark {

  private val AppName = "ETL_Workflow"
  private val PackageName = "com.necosta.etl"

  implicit val spark: SparkSession = {
    val spark = SparkSession.builder()
      .appName(AppName)
      .enableHiveSupport()
      .getOrCreate()
    Logger.getLogger(PackageName).setLevel(Level.INFO)
    val sc = spark.sparkContext
    sc.setLogLevel(Level.ERROR.toString)
    spark
  }

  implicit lazy val sc: SparkContext = spark.sparkContext
}
