import java.util

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

  private def scrape(driver: HtmlUnitDriver): Unit = {
    println(s"title: ${driver.getTitle()}")

    driver.findElementByXPath("//div[@id='js-bukkenList']")
      .findElements(By.xpath("//div[@class='property property--highlight js-property js-cassetLink']"))
      .foreach { we: WebElement =>
        val headerElem: WebElement = we.findElement(By.className("property-header"))
        val propertyElem: WebElement = we.findElement(By.className("property-body"))

        // get title ok
        //println(headerElem.findElement(By.className("js-cassetLinkHref")).getText)
        println("House name: " + getHouseName(headerElem))
        println("URL of house info: " + getHouseURL(headerElem))
        println("House image: " + getImage(propertyElem))
        println("House rent:" + getRent(propertyElem).replaceAll("\n", "/"))
        println("----------------------")

        println("House other rent:" + getOtherRnet(propertyElem).replaceAll("\n", "/"))
        println("House room info:" + getRoomInfo(propertyElem).replaceAll("\n", "/"))
        println("House building info:" + getBuildingInfo(propertyElem).replaceAll("\n", "/"))
        println("Access:" + getHowToAccess(propertyElem).replaceAll("\n", "/"))
        println("----------------------")

        println("Property Company Info:" + getPropertyCompanyInfo(propertyElem).replaceAll("\n", "/"))
        println("----------------------")
        println()
    }
  }

  private def getHouseName(headerWe: WebElement): String =
    headerWe.findElement(By.className("js-cassetLinkHref")).getText

  private def getHouseURL(headerWe: WebElement): String =
    headerWe.findElement(By.className("js-cassetLinkHref")).getAttribute("href")

  // only reading first picture
  private def getImage(propertyWe: WebElement): String =
      propertyWe.findElement(By.className("js-linkImage")).getAttribute("rel")

  /*
  private def getImages(propertyWe: WebElement): Unit =
    val tmp : util.ArrayList[String] = By.findElements(By.className("js-linkImage"))
*/
  
  private def getRent(propertyWe: WebElement): String =
    propertyWe.findElement(By.className("detailbox-property--col1")).getText

  private def getOtherRnet(propertyWe: WebElement): String =
    propertyWe.findElement(By.className("detailbox-property--col2")).getText

  // this tag doesn't have specific class name.
  private def getRoomInfo(propertyWe: WebElement): String =
    // Return type of findElements is util.List(not Scala, but Java)
    propertyWe.findElements(By.className("detailbox-property--col3")).get(0).getText

  // this tag doesn't have specific class name.
  private def getBuildingInfo(propertyWe: WebElement): String =
    propertyWe.findElements(By.className("detailbox-property--col3")).get(1).getText

  // this tag doesn't have specific class name.
  private def getAddress(propertyWe: WebElement): String =
    propertyWe.findElements(By.className("detailbox-property-col")).get(4).getText

  private def getHowToAccess(propertyWe: WebElement): String =
    propertyWe.findElement(By.className("detailnote-box")).getText

  private def getPropertyCompanyInfo(propertyWe: WebElement): String =
    propertyWe.findElement(By.className("detailnote-box-item")).getText
}