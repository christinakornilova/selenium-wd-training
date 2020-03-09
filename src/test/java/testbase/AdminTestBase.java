package testbase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static litecart.common.Utils.*;

public class AdminTestBase extends TestBase {
    public WebDriver driver;
    public WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
//        driver = new SafariDriver();
        wait = new WebDriverWait(driver, 10);

        driver.get("http://localhost/litecart/admin/");
        wait.until(ExpectedConditions.urlContains("litecart/admin/"));
    }

    protected void login() {
        //login
        String loginPwd = "admin";

        WebElement loginField = waitAndGetElementByName(driver, "username");
        clearAndFillField(loginField, loginPwd);

        WebElement pwdField = waitAndGetElementByName(driver,"password");
        clearAndFillField(pwdField, loginPwd);

        WebElement loginButton = waitAndGetElementByName(driver, "login");
        loginButton.click();
        wait.until((ExpectedConditions.invisibilityOfElementLocated(By.id("loader"))));

        Assert.assertTrue("No success alert message appeared on login", driver.findElement(By.xpath("//div[text()=' You are now logged in as admin']")).isDisplayed());
        Assert.assertTrue("No Logout link displayed", driver.findElement(By.xpath("//a[@title='Logout']")).isDisplayed());

        log.info("Login completed");
    }

    @After()
    public void stop() {
        System.out.println("Quit driver");
        driver.quit();
        driver = null;
    }
}
