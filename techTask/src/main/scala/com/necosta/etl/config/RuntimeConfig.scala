package com.necosta.etl.config

import org.rogach.scallop.exceptions.{Exit, MajorInternalException}
import org.rogach.scallop.{ScallopConf, ScallopOption}

class RuntimeConfig(args: Seq[String]) extends ScallopConf(args) {

  val s3AccessKey: ScallopOption[String] = opt[String](
    name = "s3AccessKey",
    required = true,
    descr = "Set AWS S3 access key"
  )

  val s3SecretKey: ScallopOption[String] = opt[String](
    name = "s3SecretKey",
    required = true,
    descr = "Set AWS S3 secret key"
  )

  val jdbcUrl: ScallopOption[String] = opt[String](
    name = "jdbcUrl",
    required = true,
    descr = "Set Redshift database JDBC url"
  )

  val dbUser: ScallopOption[String] = opt[String](
    name = "dbUser",
    default = Some("awsuser"),
    descr = "Set Redshift database username"
  )

  val dbPassword: ScallopOption[String] = opt[String](
    name = "dbPassword",
    required = true,
    descr = "Set Redshift database password"
  )

  val s3TempDir: ScallopOption[String] = opt[String](
    name = "s3TempDir",
    required = true,
    descr = "Set S3 temp dir path"
  )

  //verify all parameters
  verify()

  override def onError(e: Throwable): Unit = e match {
    case Exit() => throw MajorInternalException()
    case other => throw other
  }
}
