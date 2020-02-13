import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LoginTestEx3 {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    private WebElement waitAndGetElementByName(String locator) {
        By by = By.name(locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return driver.findElement(by);
    }

    private void clearAndFillField(WebElement el, String data) {
        el.clear();
        el.sendKeys(data);
    }

    @Test
    public void myFirstTest() {
        try {
            String loginPwd = "admin";

            driver.get("http://localhost/litecart/admin/");
            wait.until(ExpectedConditions.urlContains("litecart/admin/"));

            WebElement loginField = waitAndGetElementByName("username");
            clearAndFillField(loginField, loginPwd);

            WebElement pwdField = waitAndGetElementByName("password");
            clearAndFillField(pwdField, loginPwd);

            WebElement loginButton = waitAndGetElementByName("login");
            loginButton.click();
            wait.until((ExpectedConditions.invisibilityOfElementLocated(By.id("loader"))));

            Assert.assertTrue("No success alert message appeared on login", driver.findElement(By.xpath("//div[text()=' You are now logged in as admin']")).isDisplayed());
            Assert.assertTrue("No Logout link displayed", driver.findElement(By.xpath("//a[@title='Logout']")).isDisplayed());

        } catch (Exception e) {
            throw new AssertionError("Description of the failure: ", e);
        }

    }

    @After()
    public void stop() {
        System.out.println("Quit driver");
        driver.quit();
        driver = null;
    }
}

