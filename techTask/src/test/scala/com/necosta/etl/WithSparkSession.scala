package com.necosta.etl

import org.apache.spark.sql.SparkSession

trait WithSparkSession {
  def withSpark(testMethod: (SparkSession) => Any) {
    val sparkSession = SparkSession.builder()
      .appName("Spark unit-tests")
      .master("local")
      .getOrCreate()
    try {
      testMethod(sparkSession)
    }
    finally sparkSession.stop()
  }
}
