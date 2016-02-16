import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

  "Application" should {

    "work from within a browser" in new WithBrowser {
      skipped // !!! Selenium HtmlUnit driver just can't handle the jQuery or React Javascript 

      browser.goTo("http://localhost:" + port)

      val html = browser.pageSource
      val jsTypes = List("jsdeps", "fastopt", "launcher")
      for (typ <- jsTypes) yield {
        html must contain("<script type='text/javascript' src='/assets/playpen-webui-client-$typ.js'")
      }
    }
  }
}
