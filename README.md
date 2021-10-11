# Tech challenge

Technical challenge using Spark and Redshift.

## Technical task

* Develop an ETL workflow that copies data (one-off process)
* Add high-level documentation

Use the Open Source / free account from AWS or Snowflake to complete the below task.

1. Download the data from Web - This [link](https://github.com/microsoft/sql-server-samples/tree/master/samples/databases). Use either `data-warehouse-install-script` or `oltp-install-script` from adventureworks DB
1. Use any open source software to ingest the data. Apache Nifi, any ELT / ETL tools.
Use a modern data stack approach to bring the data from source.
If you don't have access to a free trial, use this instead: https://aws.amazon.com/rds/free
1. Bring the data to centralised repository [AWS Redshift / Snowflake]
1. Outline the observations from using this data either via BI tool or via simple SQL queries
reading the data from a centralised repository.

## Solution design

* Draw the diagram
* Add high-level description

#### Problem statement

ABC company wants to provide reports to their end customer from one time daily to real time.

1. The data required for the report is stored in MSSQL, large data files and Google Analytic.
The schema for the above is very different, but few data points are common and can be linked.
They use AWS as a cloud provider and want to make sure the data is secure while at rest or in transit.
1. Event driven architecture is also something on their roadmap.
1. The data from the above sources are also required for use of Regulatory reporting, Data Science and BI reporting.

Draw an approach / solution / architecture design that helps ABC company to build reports in real time.
Outline the tools that you would like to use: OpenSource, SAAS tools, anything that
you think will help the company to address the immediate need but also prepare for scaling in future.

Explain the advantage of using the proposed architecture.

# License

See [LICENSE](LICENSE)
