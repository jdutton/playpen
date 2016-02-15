package playpen.client

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._

object App {

  case class State(msg: String)

  class Backend($: BackendScope[Unit, State]) {
    def render(s: State) = <.span(s.msg)
  }
}
