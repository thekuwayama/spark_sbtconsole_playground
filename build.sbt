import Dependencies._
import Config._

ThisBuild / scalaVersion     := "2.11.12"
ThisBuild / sparkVersion     := "2.4.5"
ThisBuild / hadoopVersion    := "2.9.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "playground",
    resolvers += DefaultMavenRepository,

    libraryDependencies ++= Seq(
      "org.scala-lang"    % "scala-compiler" % scalaVersion.value,
      "org.apache.spark"  %% "spark-core"    % sparkVersion.value % "provided",
      "org.apache.spark"  %% "spark-sql"     % sparkVersion.value % "provided",
      "org.apache.hadoop" % "hadoop-client"  % hadoopVersion.value,
      "org.apache.hadoop" % "hadoop-common"  % hadoopVersion.value
    )
  )

scalacOptions in console += "-Ywarn-unused:-imports,_"
initialCommands in console := """
import collection.JavaConverters._
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.sql.types._

val spark = SparkSession
  .builder
  .master("local[*]")
  .appName("playground")
  .getOrCreate
val ds: Dataset[Row] = spark.createDataFrame(
  collection.mutable.ArrayBuffer(Row(
    Array[Byte](0, 1, 2),
    true,
    123.toByte,
    (4.56).toDouble,
    (7.89).toFloat,
    123.toInt,
    456.toLong,
    null,
    789.toShort,
    "abc",
    new java.sql.Timestamp(System.currentTimeMillis)
  )).asJava,
  StructType(
    StructField("binary", BinaryType, true)
      :: StructField("bool", BooleanType, true)
      :: StructField("byte", ByteType, true)
      :: StructField("double", DoubleType, true)
      :: StructField("float", FloatType, true)
      :: StructField("int", IntegerType, true)
      :: StructField("long", LongType, true)
      :: StructField("null", NullType, true)
      :: StructField("short", ShortType, true)
      :: StructField("string", StringType, true)
      :: StructField("timestamp", TimestampType, true)
      :: Nil
  )
)
"""
