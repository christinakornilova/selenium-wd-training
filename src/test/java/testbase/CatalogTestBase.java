package testbase;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static litecart.common.Utils.waitForJS;

public class CatalogTestBase extends TestBase {

    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
//        driver = new ChromeDriver();
        driver = new FirefoxDriver();
//        driver = new SafariDriver();
        wait = new WebDriverWait(driver, 10);

        driver.manage().window().maximize();
        driver.get("http://localhost/litecart/en/");
        wait.until(ExpectedConditions.urlContains("localhost/litecart"));
        waitForJS(driver);
    }

    @After()
    public void stop() {
        System.out.println("Quit driver");
        driver.quit();
        driver = null;
    }
}
