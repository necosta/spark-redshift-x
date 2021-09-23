package com.necosta.etl.step

import com.necosta.etl.WithSpark
import org.apache.spark.sql.{DataFrame, SaveMode}

class Writer extends Utils with WithSpark {

  def write(df: DataFrame): Unit = {
    df.write
      .format(sourceFormat)
      .option("url", jdbcUrl)
      .option("user", dbUser)
      .option("password", dbPassword)
      .option("tempdir", s3TempDir)
      .option("dbtable", targetTable)
      .option("forward_spark_s3_credentials", "true")
      .mode(SaveMode.Overwrite)
      .save()
  }
}
