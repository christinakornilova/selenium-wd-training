import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static litecart.common.Utils.*;

public class CatalogEx8 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void catalogElementsStickersCheck() {
        driver.get("http://localhost/litecart/en/");
        List<WebElement> list = waitAndGetElementsList(driver, By.cssSelector("li.product")); //By.xpath("//li[contains(@class,'product')]")
        for (WebElement el: list) {
            Assert.assertTrue("Item has more then one sticker", el.findElements(By.xpath("./descendant::div[starts-with(@class,'sticker')]")).size()==1);
        }

    }

    @After()
    public void stop() {
        System.out.println("Quit driver");
        driver.quit();
        driver = null;
    }
}
