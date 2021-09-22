# Notes

Personal notes taken while developing the tech challenge

## Technical task

ETL options:
1. Python
1. Apache Nifi
1. Scala/Spark

I decided to build a ETL service with Scala/Spark taking in consideration:
 * Spark performance and scalability features
 * Scala (unlike Python) being a strongly typed language
 * Code versioning (Apache Nifi less friendlier in this department)
 * Unit and integration tests
