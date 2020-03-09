import litecart.common.Utils;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testbase.AdminTestBase;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNewProductEx12 extends AdminTestBase {

    private void unhide(WebDriver driver, WebElement element) {
        String script = "arguments[0].style.opacity=1;"
                + "arguments[0].style['transform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['MozTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['WebkitTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['msTransform']='translate(0px, 0px) scale(1)';"
                + "arguments[0].style['OTransform']='translate(0px, 0px) scale(1)';"
                + "return true;";
        ((JavascriptExecutor) driver).executeScript(script, element);
    }

    private void attachFile(WebDriver driver, WebElement input, String relativePath) throws IOException {
        unhide(driver, input);
        input.sendKeys(new File(relativePath).getCanonicalPath());
    }

    private void switchToTab(WebDriver driver, String tabName, String tabElementXpathLocator, WebElement saveButton) {
        WebElement tabElement = Utils.waitAndGetElementByXpath(driver, tabElementXpathLocator);
        tabElement.click();
        Utils.waitForJS(driver);
        Utils.waitAndGetElement(driver, saveButton);
        Assert.assertEquals(tabName + " tab is not opened", "active", tabElement.findElement(By.xpath("./parent::li")).getAttribute("class"));
    }

    @Test
    public void testAddNewProduct() {
        login();
        Utils.adminPageNavigateAssertTitle(driver, "Catalog", true);

        WebElement addNewProductButton = Utils.waitAndGetElementByXpath(driver, "//a[text()=' Add New Product']");
        addNewProductButton.click();
        Utils.waitAndGetElementByXpath(driver, "//h1[text()=' Add New Product']");
        Utils.waitForJS(driver);

        //fill General tab
        WebElement activeListElement = Utils.waitAndGetElementByXpath(driver, "//li[@class='active']");
        WebElement nameInput = Utils.waitAndGetElementByName(driver, "name[en]");
        WebElement codeInput = Utils.waitAndGetElementByName(driver, "code");
        WebElement saveButton = Utils.waitAndGetElementByName(driver, "save");
        WebElement statusEnabledLabel = Utils.waitAndGetElementByXpath(driver, "//label[text()=' Enabled']");

        Assert.assertTrue("General tab is not opened",
                Utils.isElementPresent(Utils.waitAndGetElement(driver, activeListElement.findElement(By.xpath("./a[@href='#tab-general']")))));

        WebElement statusEnabledRadioButton = Utils.waitAndGetElementByXpath(driver, "//label[text()=' Enabled']/input[@name='status']");
        do {
            statusEnabledLabel.click();
            log.info("checked: " + statusEnabledRadioButton
                    .getAttribute("checked"));
        } while(statusEnabledRadioButton.getAttribute("checked").equals("false"));

        Assert.assertTrue("Status button not enabled",
                statusEnabledRadioButton.isSelected());

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
        Date currentDate = new Date();
        String productName = "Black Cat Plush Toy " + formatter.format(currentDate);
        Utils.clearAndFillField(nameInput, productName);
        Utils.clearAndFillField(codeInput, "001_" + formatter.format(currentDate));

        WebElement qtyField = Utils.waitAndGetElementByName(driver, "quantity");
        Utils.clearAndFillField(qtyField, "100");

        Utils.waitAndGetElementByXpath(driver, "//input[@value='1-3']").click(); //gender checkbox

        WebElement uploadImagesInput = Utils.waitAndGetElementByName(driver, "new_images[]");
        try {
            attachFile(driver, uploadImagesInput, "src/test/resources/img/ex012/blk_cat.jpeg");
        } catch (IOException e) {
            log.error("Unable to convert relative path to absolute. Caused by ", e);
        }

        formatter = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        WebElement dateValidFromInput = Utils.waitAndGetElementByName(driver, "date_valid_from");
        Utils.clearAndFillField(dateValidFromInput, formatter.format(calendar.getTime()));

        calendar.add(Calendar.DATE, 10);
        WebElement dateValidToInput = Utils.waitAndGetElementByName(driver, "date_valid_to");
        Utils.clearAndFillField(dateValidToInput, formatter.format(calendar.getTime()));

        //fill Information tab
        switchToTab(driver, "Information", "//a[@href='#tab-information']", saveButton);

        WebElement manufacturerSelect = Utils.waitAndGetElementByName(driver, "manufacturer_id");
        manufacturerSelect.click();
        Utils.waitAndGetElement(driver, manufacturerSelect.findElement(By.xpath("./option[text()='ACME Corp.']")));

        WebElement shortDescInput = Utils.waitAndGetElementByName(driver, "short_description[en]");
        Utils.clearAndFillField(shortDescInput, "Black Cat");

        WebElement descInput = Utils.waitAndGetElementByXpath(driver, "//div[@class='trumbowyg-editor']");
        Utils.clearAndFillField(descInput, "Black Cat Plush Toy");

        //fill Prices tab
        switchToTab(driver, "Prices", "//a[@href='#tab-prices']", saveButton);

        WebElement priceInput = Utils.waitAndGetElementByName(driver, "purchase_price");
        Utils.clearAndFillField(priceInput, "10.00");

        WebElement priceUsdInput = Utils.waitAndGetElementByName(driver, "prices[USD]");
        Utils.clearAndFillField(priceUsdInput, "20.00");

        saveButton.click();

        //wait for success alert
        Utils.waitAndGetElementByXpath(driver, "//div[contains(@class,'success') and text()=' Changes were successfully saved.']");

        Assert.assertTrue("Product was not added", Utils.isElementPresent(driver, By.xpath("//td/a[text()='" + productName + "']")));

    }

}
