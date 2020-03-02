import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testbase.CatalogTestBase;

import static litecart.common.Utils.*;


public class ProductEx10 extends CatalogTestBase {

    @Test
    public void testProductProperties() {
        driver.get("http://localhost/litecart/en/");
        wait.until(ExpectedConditions.urlContains("localhost/litecart"));
        waitForJS(driver);

        //campaigns
        WebElement campaignsBox = waitAndGetElement(driver, By.id("box-campaigns"));
        WebElement productMainPageElement = waitAndGetElement(driver, campaignsBox.findElement(By.xpath("./descendant::a[@class='link']")));

        String productNameMainPage = waitAndGetElement(driver, productMainPageElement.findElement(By.xpath("./div[@class='name']"))).getText();
        WebElement regularPrice = waitAndGetElement(driver, campaignsBox.findElement(By.xpath("./descendant::s[@class='regular-price']")));
        WebElement discountPrice = waitAndGetElement(driver, campaignsBox.findElement(By.xpath("./descendant::strong[@class='campaign-price']")));
        int regularPriceValueMainPage = getPrice(regularPrice);
        int discountPriceValueMainPage = getPrice(discountPrice);

        String regularPriceCrossedPropMainPage = regularPrice.getCssValue("text-decoration"); //line-through solid rgb(119, 119, 119)

        String regularPriceGrayedPropMainPage = regularPrice.getCssValue("color"); //rgba(119, 119, 119, 1)
        int[] parsedRegularPrice = parseColor(driver, regularPriceGrayedPropMainPage);

        String discountPriceBoldPropMainPage = discountPrice.getCssValue("font-weight"); //Chrome:700; FF:900 //bold

        String discountPriceRedPropMainPage = discountPrice.getCssValue("color"); //rgba(204, 0, 0, 1)
        int[] parsedDiscountPrice = parseColor(driver, discountPriceRedPropMainPage);

        String discountPriceFontSizeMainPage = discountPrice.getCssValue("font-size").replace("px", ""); //18px
        String regularPriceFontSizeMainPage = regularPrice.getCssValue("font-size").replace("px", ""); //14.4px


        Assert.assertTrue("MainPage: Discount price value is higher than regular", regularPriceValueMainPage > discountPriceValueMainPage);
        Assert.assertTrue("Regular price is NOT crossed out", regularPriceCrossedPropMainPage.contains("line-through"));
        Assert.assertTrue("Color is not gray (R==G==B)", parsedRegularPrice[0] == parsedRegularPrice[1]
                && parsedRegularPrice[2] == parsedRegularPrice[0]);
        Assert.assertTrue("Discount price is not bold", isBold(driver, discountPriceBoldPropMainPage));
        Assert.assertTrue("Color is not red (G==B)", parsedDiscountPrice[1] == parsedDiscountPrice[2]);
        Assert.assertTrue("Main page: Discount price font size is smaller than regular price font", Double.parseDouble(discountPriceFontSizeMainPage) > Double.parseDouble(regularPriceFontSizeMainPage));

        //switch to product page
        if (driver instanceof SafariDriver) {
            //safari 13+ version affected with https://github.com/SeleniumHQ/selenium/issues/7649 https://bugs.webkit.org/show_bug.cgi?id=202589
            JavascriptExecutor executor = (JavascriptExecutor)driver;
            executor.executeScript("arguments[0].click();", productMainPageElement);
        }
        productMainPageElement.click();
        waitForJS(driver);

        WebElement productPageElement = waitAndGetElement(driver, By.id("box-product"));
        WebElement regularPriceProductPage = waitAndGetElement(driver, productPageElement.findElement(By.xpath("./descendant::s[@class='regular-price']")));
        WebElement discountPriceProductPage = waitAndGetElement(driver, productPageElement.findElement(By.xpath("./descendant::strong[@class='campaign-price']")));
        int regularPriceValueProductPage = getPrice(regularPriceProductPage);
        int discountPriceValueProductPage = getPrice(discountPriceProductPage);
        log.info(regularPriceValueProductPage + " " + discountPriceValueProductPage);

        String productNameProdPage = waitAndGetElement(driver, By.xpath("//h1[@itemprop='name']")).getText();
        String discountPriceFontSizeProductPage = discountPriceProductPage.getCssValue("font-size").replace("px", "");
        String regularPriceFontSizeProductPage = regularPriceProductPage.getCssValue("font-size").replace("px", "");

        String regularPriceCrossedPropProductPage = regularPriceProductPage.getCssValue("text-decoration");;
        String regularPriceGrayedPropProductPage = regularPriceProductPage.getCssValue("color");
        int[] parsedRegularPriceProdPage = parseColor(driver, regularPriceGrayedPropProductPage);

        String discountPriceBoldPropProductPage = discountPriceProductPage.getCssValue("font-weight");
        String discountPriceRedPropProductPage = discountPriceProductPage.getCssValue("color");
        int[] parsedDiscountPriceProdPage = parseColor(driver, discountPriceRedPropProductPage);

        Assert.assertTrue("Product page: Discount price is higher than regular", regularPriceValueProductPage > discountPriceValueProductPage);
        Assert.assertTrue("Regular price is NOT crossed out", regularPriceCrossedPropProductPage.contains("line-through"));
        Assert.assertTrue("Color is not gray (R==G==B)", parsedRegularPriceProdPage[0] == parsedRegularPriceProdPage[1]
                && parsedRegularPriceProdPage[2] == parsedRegularPriceProdPage[0]);
        Assert.assertTrue("Discount price is not bold", isBold(driver, discountPriceBoldPropProductPage));
        Assert.assertTrue("Color is not red (G==B)", parsedDiscountPriceProdPage[1] == parsedDiscountPriceProdPage[2]);
        log.info("Discount " + discountPriceFontSizeProductPage + " regular: " + regularPriceFontSizeProductPage);
        Assert.assertTrue("Product page: Discount price font size is smaller than regular price font", Double.parseDouble(discountPriceFontSizeProductPage) > Double.parseDouble(regularPriceFontSizeProductPage));


        Assert.assertEquals("Regular prices on main and product pages differ", regularPriceValueMainPage, regularPriceValueProductPage);
        Assert.assertEquals("Discount prices on main and product pages differ", discountPriceValueMainPage, discountPriceValueProductPage);
        Assert.assertEquals("Product name is different on Main and Product page", productNameProdPage, productNameMainPage);

    }

    private int getPrice(WebElement el) {
        return Integer.valueOf(el.getText().replaceAll("\\$", ""));
    }

    private int[] parseColor(WebDriver driver, String color) {
        String[] hexValue;
        if (driver instanceof ChromeDriver) {
            hexValue = color.replace("rgba(", "").replace(")", "")
                    .replaceAll(" ", "").split(",");
        } else {
            hexValue = color.replace("rgb(", "").replace(")", "")
                    .replaceAll(" ", "").split(",");
        }

        int hexValue1=Integer.parseInt(hexValue[0]);
        int hexValue2=Integer.parseInt(hexValue[1]);
        int hexValue3=Integer.parseInt(hexValue[2]);

        return new int[]{hexValue1, hexValue2, hexValue3};
    }

    private boolean isBold(WebDriver driver, String fontWeight) {
        if (driver instanceof ChromeDriver || driver instanceof FirefoxDriver) {
            return fontWeight.equals("700") || fontWeight.equals("900");
        } else {
            return fontWeight.equals("bold");
        }
    }

}
