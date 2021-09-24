package com.necosta.etl.step

trait Utils {

  val sourceFormat = "io.github.spark_redshift_community.spark.redshift"

  val targetTable = "table1"

  val ddlFilePath = "https://raw.githubusercontent.com/microsoft/sql-server-samples/" +
    "master/samples/databases/adventure-works/data-warehouse-install-script"

  val ddlFileName = "instawdbdw.sql"
}
