import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static litecart.common.Utils.*;

public class AdminNavigateEx7 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    private String composeLocator(String itemName) {
        return "//span[@class='name' and .='" + itemName + "']";
    }

    private void navMainMenu(WebDriver driver, String menuItemName) {


    }

    private void navigateAssertTitle(WebDriver driver, String menuItemName, boolean hasSubItems) {
        do {
            waitAndGetElementByXpath(driver, composeLocator(menuItemName));
            driver.findElement(By.xpath(composeLocator(menuItemName))).click();
            waitForJS(driver);
        } while (hasSubItems && !isElementPresent(By.xpath("//li/ul/li[@class='selected']/a/span"), driver));
        waitAndGetElementByXpath(driver, "//td[@id='content']");
        waitAndGetElementByXpath(driver, "//h1");
        Assert.assertTrue("No header displayed on page", isElementPresent(By.xpath("//h1"), driver));
//        waitForSeconds(1);
    }

    @Test
    public void myFirstTest() {
        try {
            //login
            String loginPwd = "litecart/admin";

            driver.get("http://localhost/litecart/admin/");
            wait.until(ExpectedConditions.urlContains("litecart/admin/"));

            WebElement loginField = waitAndGetElementByName(driver, "username");
            clearAndFillField(loginField, loginPwd);

            WebElement pwdField = waitAndGetElementByName(driver,"password");
            clearAndFillField(pwdField, loginPwd);

            WebElement loginButton = waitAndGetElementByName(driver, "login");
            loginButton.click();
            wait.until((ExpectedConditions.invisibilityOfElementLocated(By.id("loader"))));

            Assert.assertTrue("No success alert message appeared on login", driver.findElement(By.xpath("//div[text()=' You are now logged in as admin']")).isDisplayed());
            Assert.assertTrue("No Logout link displayed", driver.findElement(By.xpath("//a[@title='Logout']")).isDisplayed());

            System.out.println("navigate started");

            navigateAssertTitle(driver, "Appearence",true);
            navigateAssertTitle(driver, "Template",true);
            navigateAssertTitle(driver, "Logotype",true);

            //Catalog
            navigateAssertTitle(driver, "Catalog", true);
            navigateAssertTitle(driver, "Catalog", true);
            navigateAssertTitle(driver, "Product Groups", true);
            navigateAssertTitle(driver, "Option Groups", true);
            navigateAssertTitle(driver, "Manufacturers",true);
            navigateAssertTitle(driver, "Suppliers", true);
            navigateAssertTitle(driver, "Delivery Statuses", true);
            navigateAssertTitle(driver, "Sold Out Statuses", true);
            navigateAssertTitle(driver, "Quantity Units", true);
            navigateAssertTitle(driver, "CSV Import/Export", true);

            navigateAssertTitle(driver, "Countries", false);

            navigateAssertTitle(driver, "Currencies", false);

            //Customers
            navigateAssertTitle(driver, "Customers", true);
            navigateAssertTitle(driver, "Customers", true);
            navigateAssertTitle(driver, "CSV Import/Export", true);
            navigateAssertTitle(driver, "Newsletter", true);


            navigateAssertTitle(driver, "Geo Zones", false);

            //Languages
            navigateAssertTitle(driver, "Languages", true);
            navigateAssertTitle(driver, "Languages", true);
            navigateAssertTitle(driver, "Storage Encoding", true);

            //Modules
            navigateAssertTitle(driver, "Modules", true);
            navigateAssertTitle(driver, "Background Jobs", true);
            navigateAssertTitle(driver, "Customer", true);
            navigateAssertTitle(driver, "Shipping", true);
            navigateAssertTitle(driver, "Payment", true);
            navigateAssertTitle(driver, "Order Total", true);
            navigateAssertTitle(driver, "Order Success", true);
            navigateAssertTitle(driver, "Order Action", true);


            //Orders
            navigateAssertTitle(driver, "Orders", true);
            navigateAssertTitle(driver, "Orders", true);
            navigateAssertTitle(driver, "Order Statuses", true);

            navigateAssertTitle(driver, "Pages", false);

            //Reports
            navigateAssertTitle(driver, "Reports", true);
            navigateAssertTitle(driver, "Monthly Sales", true);
            navigateAssertTitle(driver, "Most Sold Products", true);
            navigateAssertTitle(driver, "Most Shopping Customers", true);

            //Settings
            navigateAssertTitle(driver, "Settings", true);
            navigateAssertTitle(driver, "Store Info", true);
            navigateAssertTitle(driver, "Defaults", true);
            navigateAssertTitle(driver, "General", true);
            navigateAssertTitle(driver, "Listings", true);
            navigateAssertTitle(driver, "Images", true);
            navigateAssertTitle(driver, "Checkout", true);
            navigateAssertTitle(driver, "Advanced", true);
            navigateAssertTitle(driver, "Security", true);

            navigateAssertTitle(driver, "Slides", false);

            //Tax
            navigateAssertTitle(driver, "Tax", true);
            navigateAssertTitle(driver, "Tax Classes",  true);
            navigateAssertTitle(driver, "Tax Rates", true);

            //Translations
            navigateAssertTitle(driver, "Translations", true);
            navigateAssertTitle(driver, "Search Translations", true);
            navigateAssertTitle(driver, "Scan Files", true);
            navigateAssertTitle(driver, "CSV Import/Export", true);

            navigateAssertTitle(driver, "Users", false);

            navigateAssertTitle(driver, "vQmods", false);

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
