# Technical task

### Prerequisites

1. Install SDKMAN : `curl -s "https://get.sdkman.io" | bash`
1. Install Java: `sdk install java`
1. Install SBT: `sdk install sbt`

### How to

1. Build: `sbt compile`
1. Unit-test: `sbt test`
1. Package JAR: `sbt assembly`

### How to run Spark application

1. Download Spark binary and run:
```bash
./bin/spark-submit \
    --packages org.apache.spark:spark-avro_2.12:3.1.2 \
    --jars $PATH_TO_JAR/redshift-jdbc42-2.0.0.4.jar \
    --class com.necosta.etl.Main \
    --master local[2] \
    $PATH_TO_JAR/techTask/target/scala-2.12/etl-workflow-assembly-0.1.0-SNAPSHOT.jar \
    --jdbcUrl jdbc:redshift://clusterName.redshift.amazonaws.com:5439/dbName \
    --s3TempDir s3a://bucketName/folderName/ \
    --dbUser fillMe \
    --dbPassword fillMe \
    --s3AccessKey fillMe \
    --s3SecretKey fillMe
```

or

2. Run docker file (**ToDo**)


