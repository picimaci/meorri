
name := "meorri"

version := "1.0"

lazy val `meorri` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"


resolvers += "julienrf.github.com" at "http://julienrf.github.com/repo/"

resolvers += "maven2.wanari.com" at "http://maven2.wanari.com/maven2"

resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.io/releases/"))(Resolver.ivyStylePatterns)

resolvers += "jitpack" at "https://jitpack.io"

TwirlKeys.templateImports ++= Seq(
  "play.i18n.Messages",
  "be.objectify.deadbolt.java.views.html._",
  "be.objectify.deadbolt.core.utils.TemplateUtils._"
)

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  filters,
  "junit" % "junit" % "4.12" % "test",
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "be.objectify" %% "deadbolt-java" % "2.4.3",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
  "mysql" % "mysql-connector-java" % "5.1.20",
  "org.julienrf" %% "play-jsmessages" % "2.0.0",
  "org.webjars" %% "webjars-play" % "2.4.0",
  "org.webjars" % "angularjs" % "1.3.17",
  "org.webjars" % "bootstrap" % "3.1.1",
  "org.webjars" % "jquery" % "1.11.1",
  "org.webjars" % "jquery-migrate" % "1.2.1",
  "org.webjars" % "modernizr" % "2.7.1",
  "org.webjars" % "toastr" % "2.0.1",
  "org.webjars" % "bootbox" % "4.2.0",
  "org.webjars" % "angular-loading-bar" % "0.5.1",
  "org.webjars" % "font-awesome" % "4.4.0",
  "org.webjars" % "angular-paginate-anything" % "4.0.2",
  "org.webjars" % "jquery-ui" % "1.11.4",
  "org.apache.commons" % "commons-io" % "1.3.2",
  "org.apache.commons" % "commons-csv" % "1.3",
  "org.apache.commons" % "commons-email" % "1.4",
  "org.webjars" % "nervgh-angular-file-upload" % "2.1.1"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
