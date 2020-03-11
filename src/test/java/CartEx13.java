import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import testbase.CatalogTestBase;

import java.util.List;

import static litecart.common.Utils.*;

public class CartEx13 extends CatalogTestBase {

    private void waitForHomePageElementsToAppear(WebDriver driver) {
        waitAndGetElementByName(driver, "login");
        waitAndGetElementByXpath(driver, "//a/img[@src='/litecart/images/slides/1-lonely-duck.jpg']");
        waitAndGetElementByName(driver, "login");
    }

    private void waitForProductPageElementsToAppear(WebDriver driver) {
        waitForPageUrlToContain(driver, "/rubber-ducks-c-1");
        waitAndGetElementByXpath(driver, "//a[@href='#tab-information']");
        waitAndGetElementByXpath(driver, "//a[@href='#tab-details']");
        waitAndGetElementByName(driver, "login");
        waitAndGetElementByXpath(driver, "//h1");
    }

    @Test
    public void testCart() {
        String cartItemQtyLabelLocator = "//span[@class='quantity']";
        String homePageUrl = "http://localhost/litecart/en/";
        waitAndGetElementByXpath(driver, cartItemQtyLabelLocator);
        waitForHomePageElementsToAppear(driver);

        //add elements to cart
        for (int i = 0; i < 3; i++) {
            waitForUrlToBe(driver, homePageUrl);

            List<WebElement> productList =
                    waitAndGetElementsListByXpath(driver, "//li[contains(@class,'product')]/a[@title]");
            log.info("1st Item is " + productList.get(0).findElement(By.xpath("./div[@class='name']")).getText());
            productList.get(0).click();

            waitForProductPageElementsToAppear(driver);

            if (isElementNotPresent(driver, By.xpath("//select[@name='options[Size]']"))) {
                waitAndGetElementByName(driver, "add_cart_product").click();
                wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(cartItemQtyLabelLocator), Integer.toString(i + 1)));
            } else {
                Select selectSize = new Select(waitAndGetElementByName(driver, "options[Size]"));
                selectSize.selectByValue("Small");
                Assert.assertTrue("Size is not selected", waitAndGetElementByName(driver, "options[Size]")
                        .getAttribute("value").equals("Small"));
                waitAndGetElementByName(driver, "add_cart_product").click();
            }

            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(cartItemQtyLabelLocator), String.valueOf(i + 1)));
            log.info(waitAndGetElementByXpath(driver, cartItemQtyLabelLocator).getText());
            Assert.assertEquals("Items qty differs", String.valueOf(i + 1), waitAndGetElementByXpath(driver, cartItemQtyLabelLocator).getText());

            //back to Home page
            driver.navigate().back();
            waitForUrlToBe(driver, homePageUrl);
            waitForHomePageElementsToAppear(driver);
        }


        //remove elements from cart
        waitAndGetElement(driver, By.linkText("Checkout »")).click();
        waitForUrlToBe(driver, "http://localhost/litecart/en/checkout");

        List<WebElement> cartItemsList = waitAndGetElementsListByXpath(driver, "//td[@class='item']");
        for (int i = 0; i < cartItemsList.size(); i++) {
            log.info("Clicking 'Remove' button");
            waitAndGetElementByName(driver, "remove_cart_item" ).click();
            wait.until(ExpectedConditions.stalenessOf(cartItemsList.get(i)));
        }
        waitAndGetElement(driver, By.linkText("<< Back"));
        WebElement noItemsInCart = waitAndGetElementByXpath(driver, "//p/em");

        Assert.assertEquals("Cart 'no items' text differs", "There are no items in your cart.", noItemsInCart.getText());
        Assert.assertTrue("No '<< Back' link appeared after all elements were deleted from the cart",
                isElementPresent(driver, By.linkText("<< Back")));
    }
}
