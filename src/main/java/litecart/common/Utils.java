package litecart.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class Utils {
    public static Logger log = LogManager.getLogger(Utils.class);
    public static final int defaultTimeout = 10;

    public static WebElement waitAndGetElementByName(WebDriver driver, String locator) {
        long start = System.currentTimeMillis();
        By by = By.name(locator);
        WebElement el = new WebDriverWait(driver, defaultTimeout).ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfElementLocated(by));
        log.info("Element located by " + locator + " appeared after " + (System.currentTimeMillis() - start) + " ms");
        return el;
    }

    public static WebElement waitAndGetElement(WebDriver driver, String xpath) {
        long start = System.currentTimeMillis();
        By by = By.xpath(xpath);
        WebElement el = new WebDriverWait(driver, defaultTimeout).ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfElementLocated(by));
        log.info("Element located by " + xpath + " appeared after " + (System.currentTimeMillis() - start) + " ms");
        return el;
    }

    public static WebElement waitAndGetElement(int seconds, WebDriver driver, WebElement element) {
        return new WebDriverWait(driver, seconds).ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitAndGetElement(WebDriver driver, WebElement element) {
        long start = System.currentTimeMillis();
        WebElement el = waitAndGetElement(defaultTimeout, driver, element);
        log.info("Element " + element + " appeared after " + (System.currentTimeMillis() - start) + " ms");
        return el;
    }

    public static List<WebElement> waitAndGetElementsList(int timeout, WebDriver driver, By by) {
        long start = System.currentTimeMillis();
        List<WebElement> elementList = new WebDriverWait(driver, timeout).ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        log.info("Elements list located by " + by + " appeared after " + (System.currentTimeMillis() - start) + " ms");
        return  elementList;
    }

    public static List<WebElement> waitAndGetElementsList(WebDriver driver, By by) {
        return waitAndGetElementsList(defaultTimeout, driver, by);
    }

    public static List<WebElement> waitAndGetElementsListByXpath(WebDriver driver, String xpath) {
        return waitAndGetElementsList(driver, By.xpath(xpath));
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
        new WebDriverWait(driver, defaultTimeout)
                .until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (Exception e) {

        }
    }

    public static void waitForPageUrl(WebDriver driver, String expectedUrl) {
        new WebDriverWait(driver, defaultTimeout).until(ExpectedConditions.urlContains(expectedUrl));
    }
}
