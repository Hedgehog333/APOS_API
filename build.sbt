name := "APOS_API"
 
version := "1.0" 
      
lazy val `apos_api` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.7"
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )