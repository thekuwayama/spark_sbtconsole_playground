import Dependencies._
import Config._

ThisBuild / scalaVersion       := "2.11.12"
ThisBuild / sparkVersion       := "2.4.5"
ThisBuild / hadoopVersion      := "2.9.2"
ThisBuild / circeVersion       := "0.11.2"
ThisBuild / catsVersion        := "2.0.0"
ThisBuild / scalikejdbcVersion := "3.4.2"
ThisBuild / version            := "0.1.0-SNAPSHOT"
ThisBuild / organization       := "com.example"
ThisBuild / organizationName   := "example"

lazy val root = (project in file("."))
  .settings(
    name := "playground",
    resolvers += DefaultMavenRepository,

    libraryDependencies ++= Seq(
      "org.scala-lang"    % "scala-compiler" % scalaVersion.value,
      "org.apache.spark"  %% "spark-core"    % sparkVersion.value % "provided",
      "org.apache.spark"  %% "spark-sql"     % sparkVersion.value % "provided",
      "org.apache.hadoop" % "hadoop-client"  % hadoopVersion.value,
      "org.apache.hadoop" % "hadoop-common"  % hadoopVersion.value,
      "org.typelevel"     %% "cats-core"                        % catsVersion.value,
      "io.circe"          %% "circe-core"                       % circeVersion.value,
      "io.circe"          %% "circe-generic"                    % circeVersion.value,
      "io.circe"          %% "circe-generic-extras"             % circeVersion.value,
      "io.circe"          %% "circe-parser"                     % circeVersion.value,
      "io.circe"          %% "circe-shapes"                     % circeVersion.value,
      "io.circe"          %% "circe-optics"                     % "0.11.0",
      "eu.timepit"        %% "refined"                          % "0.9.12",
      "org.scalikejdbc"   %% "scalikejdbc"                      % scalikejdbcVersion.value,
      "org.scalikejdbc"   %% "scalikejdbc-syntax-support-macro" % scalikejdbcVersion.value
    )
  )

scalacOptions in console += "-Ywarn-unused:-imports,_"
initialCommands in console := """
import collection.JavaConverters._
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.sql.types._

import cats.implicits._
import eu.timepit.refined.auto._
import scalikejdbc._

val spark = SparkSession
  .builder
  .master("local[*]")
  .appName("playground")
  .config("spark.sql.caseSensitive", "true")
  .getOrCreate
val df = spark.createDataFrame(
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
).toDF
"""
