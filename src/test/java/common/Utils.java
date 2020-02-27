package common;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class Utils {

    public static WebElement waitAndGetElementByName(WebDriver driver, String locator) {
        By by = By.name(locator);
        return new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static WebElement waitAndGetElement(WebDriver driver, String xpath) {
        long start = System.currentTimeMillis();
        By by = By.xpath(xpath);
        WebElement el = new WebDriverWait(driver, 15).ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfElementLocated(by));
        System.out.println("Element located by " + xpath + " appeared after " + (System.currentTimeMillis() - start) + " ms");
        return el;
    }

    public static List<WebElement> waitAndGetElementsListByXpath(WebDriver driver, String xpath) {
        return waitAndGetElementsList(driver, By.xpath(xpath));
    }

    public static List<WebElement> waitAndGetElementsList(WebDriver driver, By by) {
        long start = System.currentTimeMillis();
        List<WebElement> elementList = new WebDriverWait(driver, 15).ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        System.out.println("Elements list located by " + by + " appeared after " + (System.currentTimeMillis() - start) + " ms");
        return  elementList;
    }

    public static void clearAndFillField(WebElement el, String data) {
        el.clear();
        el.sendKeys(data);
    }

    public static boolean isElementPresent(By by, WebDriver driver) {
        return driver.findElements(by).size() == 1;
    }

    public static boolean isElementPresent(WebElement element) {
        try {
            ExpectedConditions.visibilityOf(element);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void waitForJS(WebDriver driver) {
        new WebDriverWait(driver, 10)
                .until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (Exception e) {

        }
    }
}
