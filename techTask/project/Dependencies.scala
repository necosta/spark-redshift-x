import sbt._

object Dependencies {

  private val slf4jVersion = "1.7.32"
  private val redShiftConnVersion = "5.0.3"
  private val hadoopAwsVersion = "3.2.0"
  private val guiceVersion = "5.0.1"
  private val scallopVersion = "4.0.4"

  private val slf4jApi = "org.slf4j" % "slf4j-api" % slf4jVersion
  private val slf4jLog4j = "org.slf4j" % "slf4j-log4j12" % slf4jVersion

  private val redShiftConn = ("io.github.spark-redshift-community" %% "spark-redshift" % redShiftConnVersion)
    .excludeAll("org.apache.hadoop","hadoop-aws")

  private val hadoopAws = ("org.apache.hadoop" % "hadoop-aws" % hadoopAwsVersion)
    .exclude("org.mortbay.jetty","servlet-api")

  private val guice = "com.google.inject" % "guice" % guiceVersion

  private val scallop = "org.rogach" %% "scallop" % scallopVersion

  val coreDependencies = Seq(
    slf4jApi,
    slf4jLog4j,
    redShiftConn,
    hadoopAws,
    guice, // Fixes Guava "class-not-found" errors
    scallop
  )

  private val sparkVersion = "3.1.0"

  val providedDependencies = Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion % Provided,
    "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
    "org.apache.spark" %% "spark-avro" % sparkVersion % Provided,
    "org.apache.spark" %% "spark-hive" % sparkVersion % Provided
  )

  private val scalaTestVersion = "3.2.10"

  val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )
}
