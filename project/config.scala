import sbt._

object Config {
  val sparkVersion = settingKey[String]("Specify spark version.")
  val hadoopVersion = settingKey[String]("Specify hadoop version.")
}
