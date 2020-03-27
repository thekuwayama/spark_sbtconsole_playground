# spark_sbtconsole_playground

This is my sbt console playground for [Apache Spark](https://spark.apache.org/).


## Usage

It has preloaded the follows:

- spark: `org.apache.spark.sql.SparkSession`
- ds: `org.apache.spark.sql.Dataset[Row]`

```bash
$ sbt console

scala> spark
res0: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@xxxxxxxx

scala> ds
res1: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [binary: binary, bool: boolean ... 9 more fields]

scala> ds.show
...
+----------+----+----+------+-----+---+----+----+-----+------+--------------------+
|    binary|bool|byte|double|float|int|long|null|short|string|           timestamp|
+----------+----+----+------+-----+---+----+----+-----+------+--------------------+
|[00 01 02]|true| 123|  4.56| 7.89|123| 456|null|  789|   abc|2020-01-01 12:34:...|
+----------+----+----+------+-----+---+----+----+-----+------+--------------------+
```
