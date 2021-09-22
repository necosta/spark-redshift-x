# Technical task

### Prerequisites

1. Install SDKMAN : `curl -s "https://get.sdkman.io" | bash`
1. Install Java: `sdk install java`
1. Install SBT: `sdk install sbt`

### How to

1. Build: `sbt compile`
1. Unit-test: `sbt test`
1. Package JAR: `sbt package`

### How to run Spark application

1. Download Spark binary and run:
```bash
./bin/spark-submit \
    --class com.necosta.etl.Main \
    --master local[2] \
    $PATH_TO_JAR/techTask/target/scala-2.12/etl-workflow-assembly-0.1.0-SNAPSHOT.jar
```

or

2. Run docker file (**ToDo**)


