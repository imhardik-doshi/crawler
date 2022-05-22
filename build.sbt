ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.4"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.2" % "provided"
val scalaCommon = "com.softwaremill.common" %% "tagging" % "2.2.1"
val jsonExtensions = "ai.x" %% "play-json-extensions" % "0.10.0"
val lagomConsul = "lagom-service-locator-scaladsl-consul" %% "lagom-service-locator-scaladsl-consul" % "1.4.0-SNAPSHOT"
val cats = "org.typelevel" %% "cats-core" % "1.4.0"
val lagomScaladslAkkaDiscovery = "com.lightbend.lagom" %% "lagom-scaladsl-akka-discovery-service-locator" % "1.5.1"
val jsoup = "org.jsoup" % "jsoup" % "1.15.1"
val scalaTest = "org.scalatest" %% "scalatest" % "3.2.10" % Test
val scalacheck = "org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0" % "test"
val scalaMock = "org.scalamock" %% "scalamock" % "5.1.0" % Test

lazy val root = (project in file("."))
  .aggregate(`crawler-common`, `crawler-api`, `crawler-impl`
  )

val tracing = Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.github.danielwegener" % "logback-kafka-appender" % "0.2.0-RC2"
)

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-encoding", "UTF-8", // source files are in UTF-8
    "-deprecation", // warn about use of deprecated APIs
    "-unchecked", // warn about unchecked type parameters
    "-feature", // warn about misused language features
    "-language:higherKinds", // allow higher kinded types without `import scala.language.higherKinds`
    "-Xlint", // enable handy linter warnings
    "-Ypartial-unification" // allow the compiler to unify type constructors of different arities
  )
)

lazy val `crawler-common` = (project in file("crawler-common"))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      lagomScaladslServer,
      lagomScaladslKafkaBroker,
      lagomScaladslPersistenceCassandra,
      lagomScaladslClient,
      lagomScaladslAkkaDiscovery,
      jsonExtensions,
      cats
    ),
    libraryDependencies ++= tracing,
    updateOptions := updateOptions.value.withCachedResolution(false)
  )

lazy val `crawler-api` = (project in file("crawler-api"))
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      jsonExtensions,
    ),
    updateOptions := updateOptions.value.withCachedResolution(false)
  ).dependsOn(`crawler-common`)

lazy val `crawler-impl` = (project in file("crawler-impl"))
  .enablePlugins(LagomScala)
  .settings(
    commonSettings,
    version := "1.0-SNAPSHOT",
    libraryDependencies ++= Seq(
      macwire,
      scalaCommon,
      scalaTest,
      lagomConsul,
      jsoup,
      scalaTest,
      scalaMock,
      scalacheck
    ),
    updateOptions := updateOptions.value.withCachedResolution(false)
  )
  .settings(resolvers += "Local Maven Repository" at "file:///" + Path.userHome + "/.m2/repository")
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`crawler-api`)