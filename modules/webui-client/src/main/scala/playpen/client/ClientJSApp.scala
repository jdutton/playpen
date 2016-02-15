package playpen.client

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import org.scalajs.dom
import org.scalajs.jquery._
import scala.scalajs.js

object ClientJSApp extends js.JSApp {

  def main(): Unit = {
    jQuery(setupUI _)
  }

  def setupUI(): Unit = {
    val reactmain = org.scalajs.dom.document.getElementById("reactmain")

    val reactApp = ReactComponentB[Unit]("PlayPenReactApp")
      .initialState(App.State("Hello"))
      .renderBackend[App.Backend]
      .buildU

    ReactDOM.render(reactApp(), reactmain)
  }

}
