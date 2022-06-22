ThisBuild / scalaVersion := "2.13.8"

ThisBuild / version := "1.0-SNAPSHOT"

val tapirVersion = "1.0.1"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """play-tapir-sample""",
    libraryDependencies ++= Seq(
      guice,
      ehcache,
      "org.scalatestplus.play"        %% "scalatestplus-play"      % "5.1.0"      % Test,
      "com.softwaremill.sttp.tapir"   %% "tapir-play-server"       % tapirVersion,
      "com.softwaremill.sttp.tapir"   %% "tapir-swagger-ui-bundle" % tapirVersion,
      "com.softwaremill.sttp.tapir"   %% "tapir-json-circe"        % tapirVersion,
      "com.softwaremill.sttp.tapir"   %% "tapir-sttp-stub-server"  % tapirVersion % Test,
      "com.softwaremill.sttp.client3" %% "circe"                   % "3.6.2"      % Test
    )
  )
