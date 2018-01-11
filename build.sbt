name          := "ConnectFourWeb"
organization  := "de.htwg.sa"
version := "1.0"
scalaVersion := "2.12.2"
scalacOptions := Seq("-unchecked", "-feature", "-deprecation", "-encoding", "utf8")
      
lazy val `connectfourweb` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

// Scala Test
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % "test"


libraryDependencies += "org.postgresql" % "postgresql" % "42.1.4"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.2.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1"
)

libraryDependencies += "com.h2database" % "h2" % "1.4.192"

libraryDependencies += "com.google.code.gson" % "gson" % "2.8.2"