package com.necosta.etl.step

import com.necosta.etl.WithSpark
import org.apache.spark.sql.DataFrame

class Reader extends Utils with WithSpark {

  def read(): DataFrame = {
    spark.read
      .format(sourceFormat)
      .option("url", jdbcUrl)
      .option("query", s"select * from $targetTable")
      .option("user", dbUser)
      .option("password", dbPassword)
      .option("tempdir", s3TempDir)
      .option("forward_spark_s3_credentials", "true")
      .load()
  }
}
