logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.4.7")

//Web plugins
addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.0.6")