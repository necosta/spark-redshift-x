package com.necosta.etl.step

import com.necosta.etl.WithSpark
import com.necosta.etl.config.RuntimeConfig
import org.apache.spark.sql.{DataFrame, SaveMode}

class Writer(runtimeConf: RuntimeConfig) extends Utils with WithSpark {

  def write(df: DataFrame, targetTable: String): Unit = {
    df.write
      .format(sourceFormat)
      .option("url", runtimeConf.jdbcUrl())
      .option("user", runtimeConf.dbUser())
      .option("password", runtimeConf.dbPassword())
      .option("tempdir", runtimeConf.s3TempDir())
      .option("dbtable", targetTable)
      // https://docs.databricks.com/data/data-sources/aws/amazon-redshift.html
      .option("extracopyoptions", "EMPTYASNULL")
      .option("tempformat", "CSV")
      .option("csvnullstring", "@NULL@")
      .option("forward_spark_s3_credentials", "true")
      .mode(SaveMode.Overwrite)
      .save()
  }
}
