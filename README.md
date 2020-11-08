# spark_sbtconsole_playground

This is my sbt console playground for [Apache Spark](https://spark.apache.org/).


## Usage

It has preloaded the follows:

- spark: `org.apache.spark.sql.SparkSession`
- df: `org.apache.spark.sql.DataFrame`

```bash
$ sbt console

scala> spark
res0: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@xxxxxxxx

scala> df
res1: org.apache.spark.sql.DataFrame = [byte: tinyint, short: smallint ... 14 more fields]

scala> df.show
...
+----+-----+----------+-------------------+-----+------+-------+------------+-------+-------+-------------------+----------+--------------------+--------------------+--------------------+----+
|byte|short|   integer|               long|float|double|decimal|      string| binary|boolean|          timestamp|      date|               array|                 map|              struct|null|
+----+-----+----------+-------------------+-----+------+-------+------------+-------+-------+-------------------+----------+--------------------+--------------------+--------------------+----+
| 127|32767|2147483647|9223372036854775807|  1.1|   1.2|    1.3|ThisIsString|[01 02]|   true|2020-12-31 21:34:56|2020-12-31|[This, is, String...|[ThisIsKey -> Thi...|[StringValue1, St...|null|
+----+-----+----------+-------------------+-----+------+-------+------------+-------+-------+-------------------+----------+--------------------+--------------------+--------------------+----+
```
