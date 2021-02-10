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
import java.sql.{Date, Timestamp}

import cats.implicits._
import eu.timepit.refined.auto._
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.types._
import scalikejdbc._

val spark = SparkSession
  .builder
  .master("local[*]")
  .appName("playground")
  .config("spark.sql.caseSensitive", "true")
  .getOrCreate

val bd: BigDecimal = BigDecimal.valueOf(1.3)
val df = spark.createDataFrame(
  collection.mutable.ArrayBuffer(
    Row(
      127.toByte,
      32767.toShort,
      2147483647,
      9223372036854775807L,
      1.1.toFloat,
      1.2,
      bd,
      "ThisIsString",
      Array[Byte](1.toByte, 2.toByte),
      true,
      new Timestamp(1609418096000L), // 2020-12-31T12:34:56Z
      new Date(1609418096000L),      // 2020-12-31T12:34:56Z
      Array[String]("This", "is", "String", "Array"),
      Map[String, String]("ThisIsKey" -> "ThisIsValue", "ThisIsAnotherKey" -> "ThisIsAnotherValue"),
      Row("StringValue1", "StringValue2"),
      null
    )
  ).asJava,
  StructType(
    StructField("byte", ByteType, true)
      :: StructField("short", ShortType, true)
      :: StructField("integer", IntegerType, true)
      :: StructField("long", LongType, true)
      :: StructField("float", FloatType, true)
      :: StructField("double", DoubleType, true)
      :: StructField("decimal", DataTypes.createDecimalType(bd.precision, bd.scale))
      :: StructField("string", StringType, true)
      :: StructField("binary", BinaryType, true)
      :: StructField("boolean", BooleanType, true)
      :: StructField("timestamp", TimestampType, true)
      :: StructField("date", DateType, true)
      :: StructField("array", ArrayType(StringType), true)
      :: StructField("map", MapType(StringType, StringType), true)
      :: StructField(
        "struct",
        new StructType()
         .add("StringField1", StringType)
         .add("StringField2", StringType),
        true
      )
      :: StructField("null", NullType, true)
      :: Nil
  )
).toDF
"""
