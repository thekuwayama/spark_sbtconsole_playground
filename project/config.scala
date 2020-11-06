import sbt._

object Config {
  val sparkVersion       = settingKey[String]("Specify spark version.")
  val hadoopVersion      = settingKey[String]("Specify hadoop version.")
  val catsVersion        = settingKey[String]("Specify cats version.")
  val circeVersion       = settingKey[String]("Specify circe version.")
  val scalikejdbcVersion = settingKey[String]("Specify scalikejdbc version.")
}
