package com.necosta.etl.step

trait Utils {

  val sourceFormat = "io.github.spark_redshift_community.spark.redshift"

  // ToDo: Soft-code these values
  val jdbcUrl = "jdbc:redshift://redshift-cluster-1.cwrp8bc46fgq.us-east-1.redshift.amazonaws.com:5439/dev"
  val dbUser = "awsuser"
  val dbPassword = "fillMe"
  val s3TempDir = "s3a://redshift-necosta/temp/"
  val targetTable = "table1"
}
