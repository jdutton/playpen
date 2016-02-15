package controllers

import play.api._
import play.api.mvc._
import playpen.model.Foo

class Application extends Controller {

  def index = Action {
    //Ok(views.html.index(s"Your new application is ready with ${Foo("baz")}."))
    Ok(views.html.index())
  }

}
