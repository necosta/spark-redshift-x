import Dependencies._

lazy val etlWorkflow = (project in file("."))
  .settings(
    name := "etl-workflow",
    libraryDependencies ++= coreDependencies ++ providedDependencies ++ testDependencies
  )
