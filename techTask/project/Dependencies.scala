import sbt._

object Dependencies {

  private val slf4jVersion = "1.7.32"

  private val slf4jApi = "org.slf4j" % "slf4j-api" % slf4jVersion
  private val slf4jLog4j = "org.slf4j" % "slf4j-log4j12" % slf4jVersion

  val coreDependencies = Seq(
    // Logging libs
    slf4jApi,
    slf4jLog4j
  )

  private val sparkVersion = "3.1.2"

  val providedDependencies = Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion % Provided,
    "org.apache.spark" %% "spark-sql" % sparkVersion % Provided
  )

  private val scalaTestVersion = "3.2.10"

  val testDependencies = Seq(
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )
}
