import scala.collection.JavaConversions._

import org.openqa.selenium.htmlunit.HtmlUnitDriver
import org.openqa.selenium.{ By, WebElement }

object App {

  def main(args: Array[String]) = {

    val urlOfSuumoTokyoNew = "http://suumo.jp/jj/chintai/ichiran/FR301FC001/?ar=030&bs=040&ta=13&sc=13101&sc=13102&sc=13103&sc=13104&sc=13105&sc=13113&sc=13106&sc=13107&sc=13108&sc=13118&sc=13121&sc=13122&sc=13123&sc=13109&sc=13110&sc=13111&sc=13112&sc=13114&sc=13115&sc=13120&sc=13116&sc=13117&sc=13119&sc=13201&sc=13202&sc=13203&sc=13204&sc=13205&sc=13206&sc=13207&sc=13208&sc=13209&sc=13210&sc=13211&sc=13212&sc=13213&sc=13214&sc=13215&sc=13218&sc=13219&sc=13220&sc=13221&sc=13222&sc=13223&sc=13224&sc=13225&sc=13227&sc=13228&sc=13229&sc=13300&cb=0.0&ct=9999999&mb=0&mt=9999999&ts=1&et=9999999&cn=9999999&shkr1=03&shkr2=03&shkr3=03&shkr4=03&sngz=&po1=09&po2=99"

    request(urlOfSuumoTokyoNew)
  }

  private def request(url: String): Unit = {
    val driver = new HtmlUnitDriver()
    driver.get(url)
    scrape(driver)
  }

  private def getTitle(driver: HtmlUnitDriver): String = {
    "1"
  }

  private def scrape(driver: HtmlUnitDriver): Unit = {
    println(s"title: ${driver.getTitle()}")

    driver.findElementByXPath("//div[@id='js-bukkenList']")
      .findElements(By.xpath("//div[@class='property property--highlight js-property js-cassetLink']"))
      .foreach { we: WebElement =>
        val headerElem = we.findElement(By.className("property-header"))
        val propertyElem = we.findElement(By.className("property-body"))

        // get title ok
        //println(headerElem.findElement(By.className("js-cassetLinkHref")).getText)
        println(getHouseName(headerElem))
        println(getHouseURL(headerElem))
        println("----------------------")

        println(getImage(propertyElem))
        println("----------------------")
        println()
    }
  }

  private def getHouseName(headerWe: WebElement): String =
    headerWe.findElement(By.className("js-cassetLinkHref")).getText

  private def getHouseURL(headerWe: WebElement): String =
    headerWe.findElement(By.className("js-cassetLinkHref")).getAttribute("href")

  private def getImage(propertyWe: WebElement): Unit =

//        propertyWe.findElement(By.xpath("//img[@class='js-noContextMenu js-linkImage js-scrollLazy js-adjustImg']")).getAttribute("rel")
    for(elem <- propertyWe.findElements(By.xpath("//img[@class='js-noContextMenu js-linkImage js-scrollLazy js-adjustImg']"))){
      println(elem.getAttribute("rel"))
    }





 /*
    driver.findElementByXPath("//div[@class='property property--highlight js-property js-cassetLink']")
      .findElements(By.xpath("//a[@class='js-cassetLinkHref']"))
      .foreach { we: WebElement =>

      // これはok

      // １件ごとに分けてやろうとすると最初の１件しか反応されない
      println("Start -----------")
      println(s"title: ${we.getText()}")
      println(s"link: ${we.getAttribute("href")}")
      println("End   -----------")
      println()

    }
    */



}