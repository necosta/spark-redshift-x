package com.necosta.etl.step

import com.necosta.etl.WithSpark
import com.necosta.etl.config.RuntimeConfig
import org.apache.spark.sql.DataFrame

class Reader(runtimeConf: RuntimeConfig) extends Utils with WithSpark {

  def read(): DataFrame = {
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
