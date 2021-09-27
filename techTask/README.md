# Technical task

### Prerequisites

1. Install SDKMAN : `curl -s "https://get.sdkman.io" | bash`
1. Install Java: `sdk install java`
1. Install SBT: `sdk install sbt`

### How to

1. Build: `sbt compile`
1. Unit-test: `sbt test`
1. Package JAR: `sbt assembly`
1. Code coverage analysis: `sbt clean coverage test coverageReport`

### How to run Spark application

Download Spark binary and run:
```bash
./bin/spark-submit \
    --packages org.apache.spark:spark-avro_2.12:3.1.2 \
    --jars $PATH_TO_JAR/redshift-jdbc42-2.0.0.7.jar \
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
**Note:** Download the Redshift JDBC connector [here](https://s3.amazonaws.com/redshift-downloads/drivers/jdbc/2.0.0.7/redshift-jdbc42-2.0.0.7.jar)

### Using docker

1. Build docker: `docker-compose build sparkJob`
1. Set secrets on `docker-compose.yml`, env variables section
1. Run docker: `docker-compose run sparkJob`

### Checking data with Apache Zeppelin

1. Load Zeppelin docker: `docker-compose up zeppelin`
1. Open `http://localhost:8081/`

**Note:** Check https://zeppelin.apache.org/docs/latest/interpreter/jdbc.html#redshift

### Database analysis

Typical [Star schema](https://en.wikipedia.org/wiki/Star_schema) data warehouse database.

* 1 log table - `DatabaseLog.csv`
* 16 dimension tables
* 11 fact tables
* 1 sys table - `sysdiagrams.csv`
* 1 other table - `ProspectiveBuyer.csv`

Example SQL queries. See [here](https://github.com/trijitghosh/LetsGetChecked/blob/master/src/main/resources/QUERIES.sql)
