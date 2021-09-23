import Dependencies._

val scala212 = "2.12.15"

lazy val etlWorkflow = (project in file("."))
  .settings(
    name := "etl-workflow",
    libraryDependencies ++= coreDependencies ++ providedDependencies ++ testDependencies,
    scalaVersion := scala212,
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x if x.endsWith("/module-info.class") => MergeStrategy.discard
      case x => (assembly / assemblyMergeStrategy).value.apply(x)
    }
  )


