name := "playpen"

val scalaVers = "2.11.7"
val scalajsReactVersion = "0.10.4"

lazy val commonSettings = Seq(
  version := "0.1-SNAPSHOT",

  scalaVersion := scalaVers,

  scalacOptions ++= Seq("-unchecked", "-deprecation"),

  parallelExecution := false
) ++ scalariformSettings

lazy val model = (crossProject.crossType(CrossType.Pure) in file("modules/model"))
  .settings(commonSettings: _*)
lazy val modelJVM = model.jvm.settings(name := "modelJVM")
lazy val modelJS = model.js.settings(name := "modelJS")

// To resolve scalaz-stream for Specs2
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

lazy val webui: Project = (project in file("modules/webui"))
  .enablePlugins(PlayScala)
  .enablePlugins(BuildInfoPlugin)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      cache,
      ws,
      specs2 % Test,
      //scalatest,
      "org.webjars" %% "webjars-play" % "2.4.0-2",
      "org.webjars" % "bootstrap" % "3.3.6",
      "org.webjars" % "jquery" % "1.11.3",
      "com.vmunier" %% "play-scalajs-scripts" % "0.3.0"),
    scalaJSProjects := Seq(webuiClient),
    pipelineStages := Seq(scalaJSProd),
    routesGenerator := InjectedRoutesGenerator
  )
  .aggregate(webuiClient)
  .dependsOn(modelJVM)

lazy val webuiClient: Project = (project in file("modules/webui-client"))
  .settings(commonSettings)
  .settings(name := "webuiClient")
  .settings(
    persistLauncher := true,
    persistLauncher in Test := false,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.0",
      "be.doeraene" %%% "scalajs-jquery" % "0.9.0",
      "com.github.japgolly.scalajs-react" %%% "core" % scalajsReactVersion,
      "com.github.japgolly.scalajs-react" %%% "ext-monocle" % scalajsReactVersion,
      "com.github.japgolly.fork.monocle" %%% "monocle-macro" % "1.2.0"
    ),
    jsDependencies ++= Seq(
      RuntimeDOM % "test",
      "org.webjars.bower" % "react" % "0.14.3" / "react-with-addons.js" minified "react-with-addons.min.js" commonJSName "React",
      "org.webjars.bower" % "react" % "0.14.3" / "react-dom.js" minified  "react-dom.min.js" dependsOn "react-with-addons.js" commonJSName "ReactDOM"
    )
  )
  .enablePlugins(ScalaJSPlugin, ScalaJSPlay)
  .dependsOn(modelJS)


lazy val root = (project in file("."))
  .aggregate(webui, modelJVM)
  .settings(commonSettings)
