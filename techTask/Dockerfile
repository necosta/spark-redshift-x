# Global Arguments
ARG SCALA_VERSION=2.12
ARG SBT_VERSION=1.5.4
ARG SPARK_VERSION=3.1.1
ARG REDSHIFT_JDBC_VERSION=2.0.0.7
ARG USER="necosta"

## Build Image ##
# https://hub.docker.com/r/mozilla/sbt
FROM mozilla/sbt:8u292_$SBT_VERSION AS build

# Labels
LABEL maintainer=$USER

# Env variables
ENV APP_HOME /opt/artifacts
ENV SBT_OPTS "-Xmx2G"

# Workdir creation
WORKDIR $APP_HOME
COPY src src/
COPY project/Dependencies.scala project/
COPY project/plugins.sbt project/
COPY build.sbt .

# Download
ENV BASE_PATH https://s3.amazonaws.com/redshift-downloads/drivers/jdbc
ARG REDSHIFT_JDBC_VERSION
RUN wget -P $APP_HOME "$BASE_PATH/$REDSHIFT_JDBC_VERSION/redshift-jdbc42-$REDSHIFT_JDBC_VERSION.jar"

# Runs unit-tests and packaging
RUN sbt -batch -Dsbt.ci=true assembly

## Release Image ##
# https://hub.docker.com/r/bde2020/spark-master
FROM bde2020/spark-master:$SPARK_VERSION-hadoop3.2 AS release

# Labels
LABEL maintainer=$USER

ARG SCALA_VERSION
ARG SPARK_VERSION
ARG REDSHIFT_JDBC_VERSION
ARG UID=500
ARG GID=500

# Strange bug in docker-compose where, if arg used more than once, has to be saved in env
ENV SCALA_VERSION $SCALA_VERSION
ENV REDSHIFT_JDBC_VERSION $REDSHIFT_JDBC_VERSION

USER $UNAME

COPY --chown=$USER:$USER --from=build /opt/artifacts/target/scala-$SCALA_VERSION/etl-workflow-assembly-*.jar /opt/etl-job-spark.jar
COPY --chown=$USER:$USER --from=build /opt/artifacts/redshift-jdbc42-$REDSHIFT_JDBC_VERSION.jar /opt/

ENV CLASS_PATH="com.necosta.etl.Main"

WORKDIR /spark

ENTRYPOINT /spark/bin/spark-submit \
       --packages org.apache.spark:spark-avro_$SCALA_VERSION:$SPARK_VERSION \
       --jars /opt/redshift-jdbc42-$REDSHIFT_JDBC_VERSION.jar \
       --class $CLASS_PATH \
       --master local[2] \
       /opt/etl-job-spark.jar \
       --jdbcUrl $JDBC_URL \
       --s3TempDir $S3_TEMP_DIR \
       --dbUser $DB_USER \
       --dbPassword $DB_PASSWORD \
       --s3AccessKey $S3_ACCESS_KEY \
       --s3SecretKey $S3_SECRET_KEY
