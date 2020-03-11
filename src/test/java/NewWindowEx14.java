import java.util.*;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testbase.AdminTestBase;


import static litecart.common.Utils.*;

public class NewWindowEx14 extends AdminTestBase {

    @Test
    public void testCountryEditInNewWindow() {
        login();

        //open countries page
        waitAndGetElementByXpath(driver, "//span[@class='name' and text()='Countries']").click();

        //wait for page to load
        waitForUrlToBe(driver, "http://localhost/litecart/admin/?app=countries&doc=countries");
        waitAndGetElementByXpath(driver, "//h1[text()=' Countries']");
        waitAndGetElementByXpath(driver, "//td[text()='Countries: 238']");
        waitAndGetElementsListByXpath(driver, "//tr[@class='row']");
        WebElement addNewCountryButton = waitAndGetElementByXpath(driver, "//a[text()=' Add New Country']");

        //open Add New Country page
        addNewCountryButton.click();
        waitForUrlToBe(driver, "http://localhost/litecart/admin/?app=countries&doc=edit_country");
        waitAndGetElementsListByXpath(driver, "//h1[text()=' Add New Country']");
        waitAndGetElementByName(driver, "save");
        List<WebElement> newWindowLinks = waitAndGetElementsListByXpath(driver, "//td/a[@target='_blank']");

        for (int i = 0; i < newWindowLinks.size(); i++) {
            String originalWindow = driver.getWindowHandle();
            log.info("Original window: " + originalWindow);
            Assert.assertTrue("More than one window is opened", driver.getWindowHandles().size() == 1);


            String expectedNewWindowUrl = newWindowLinks.get(i).getAttribute("href").replaceAll("https?://", "");
            //redirect
            if (expectedNewWindowUrl.contains("www.addressdoctor.com/en/countries-data/address-formats.html"))
                expectedNewWindowUrl = "www.informatica.com/products/data-quality/data-as-a-service/address-verification/address-formats.html";

            //click the link which opens in a new window
            log.info(expectedNewWindowUrl);
            log.info("Click on link from the list");
            newWindowLinks.get(i).click();

            //wait for new window
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            //wait until new window handle appear and switch to it
            for (String windowHandle : driver.getWindowHandles()) {
                if(!originalWindow.contentEquals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }

            waitForPageUrlToContain(driver, expectedNewWindowUrl);

            //close new window and switch back to original one
            driver.close();
            driver.switchTo().window(originalWindow);
            Assert.assertTrue("No switch to original window happened", driver.getCurrentUrl().contains("app=countries&doc=edit_country"));
        }
    }
}
