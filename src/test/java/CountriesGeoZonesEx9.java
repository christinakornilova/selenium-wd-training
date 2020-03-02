import com.google.common.collect.Ordering;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testbase.TestBase;

import java.util.ArrayList;
import java.util.List;

import static litecart.common.Utils.*;

public class CountriesGeoZonesEx9 extends TestBase {
    private WebDriver driver;
    private WebDriverWait wait;


    @Before
    public void start() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);

        //login
        String loginPwd = "admin";

        driver.get("http://localhost/litecart/admin/");
        wait.until(ExpectedConditions.urlContains("litecart/admin"));

        WebElement loginField = waitAndGetElementByName(driver, "username");
        clearAndFillField(loginField, loginPwd);

        WebElement pwdField = waitAndGetElementByName(driver,"password");
        clearAndFillField(pwdField, loginPwd);

        WebElement loginButton = waitAndGetElementByName(driver, "login");
        loginButton.click();
        wait.until((ExpectedConditions.invisibilityOfElementLocated(By.id("loader"))));

        Assert.assertTrue("No success alert message appeared on login", driver.findElement(By.xpath("//div[text()=' You are now logged in as admin']")).isDisplayed());
        Assert.assertTrue("No Logout link displayed", driver.findElement(By.xpath("//a[@title='Logout']")).isDisplayed());

        log.info("'@Before' completed");
    }

    private void assertThatListIsSorted(WebDriver driver, String listLocator) {
        List<WebElement> webElementsList = waitAndGetElementByXpath(driver, "//table[@id='table-zones']")
                .findElements(By.xpath(listLocator));

        List<String> stringList = new ArrayList<>();
        for (WebElement element : webElementsList) {
            stringList.add(element.getAttribute("textContent"));
        }
        Assert.assertTrue("List is not sorted", Ordering.natural().isOrdered(stringList));
    }

    private void switchToPreviousPage(WebDriver driver,  String pageTitle) {
        //switch back to previous page
        WebElement cancelButton = waitAndGetElementByXpath(driver, "//button[@name='cancel']");
        cancelButton.click();

        waitAndGetElementByXpath(driver, "//h1[text()='"+ pageTitle +"']");
    }

    @Test
    public void testCheckCountries() {
        WebElement countryMenuItem = waitAndGetElementByXpath(driver, "//span[text()='Countries']");
        countryMenuItem.click();

        //check that countries names are sorted
        List<WebElement> countriesList = waitAndGetElementsList(driver, By.xpath("//table[@class='dataTable']//td/a[string-length(text())>0]"));
        List<String> countriesNamesList = new ArrayList<>();
        for (WebElement country : countriesList) {
            countriesNamesList.add(country.getAttribute("textContent"));
        }
        Assert.assertTrue("Countries list is not sorted", Ordering.natural().isOrdered(countriesNamesList));

        //find countries with geo zones count > 0 and check that geo zones names are sorted
        String geoZoneCountListLocator = "//table[@class='dataTable']/tbody/tr/td[6]";
        String zonesListLocator = "./descendant::td/input[contains(@name,'name') and string-length(@value)>0]";
        List<WebElement> geoZonesCountList = waitAndGetElementsListByXpath(driver, geoZoneCountListLocator);
        int size = geoZonesCountList.size();
        for (int i = 0; i < size; i++) {
            //search for country with geo zones count > 0
            if(!geoZonesCountList.get(i).getAttribute("textContent").equals("0")) {

                //switch to country's page
                WebElement country = waitAndGetElementsListByXpath(driver, geoZoneCountListLocator).get(i).findElement(By.xpath("./following-sibling::td/a"));
                country.click();
                waitAndGetElementByXpath(driver, "//h1[text()=' Edit Country']");

                //check that zones list is sorted
                //get zones web elements list
                assertThatListIsSorted(driver, zonesListLocator);

                //back to countries page
                switchToPreviousPage(driver, " Countries");

                //renew web elements list to avoid SRE
                geoZonesCountList = waitAndGetElementsListByXpath(driver, geoZoneCountListLocator);
            }
        }

    }

    @Test
    public void testCheckGeoZones() {
        //2) на странице http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones
        //зайти в каждую из стран и проверить, что зоны расположены в алфавитном порядке
        WebElement geoZonesMenuItem = waitAndGetElementByXpath(driver, "//a/span[text()='Geo Zones']");
        geoZonesMenuItem.click();

        waitAndGetElementByXpath(driver, "//h1[text()=' Geo Zones']");

        String locator = "//table[@class='dataTable']//td/a[string-length(text())>0]";
        List<WebElement> countriesList = waitAndGetElementsList(driver, By.xpath(locator));
        int size = countriesList.size();
        String listLocator = "./descendant::select[contains(@name,'zone_code')]/option[@selected='selected']";
        for (int i = 0; i < size; i++) {
            //switch to country's page
            WebElement country = countriesList.get(i);
            country.click();

            //wait until Edit Geo Zone Page loaded
            waitAndGetElementByXpath(driver, "//h1[text()=' Edit Geo Zone']");

            //check that zones list is sorted
            //get zones web elements list and check that it is sorted
            assertThatListIsSorted(driver, listLocator);

            //switch back to geo zones page
            switchToPreviousPage(driver, " Geo Zones");

            //renew web elements list to avoid SRE
            countriesList = waitAndGetElementsList(driver, By.xpath(locator));
        }

    }

    @After()
    public void stop() {
        System.out.println("Quit driver");
        driver.quit();
        driver = null;
    }
}
