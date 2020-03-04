import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import testbase.CatalogTestBase;

import java.text.SimpleDateFormat;
import java.util.Date;

import static litecart.common.Utils.*;

public class UserCreationEx11 extends CatalogTestBase {

    @Test
    public void testRegisterNewUser() {
        WebElement signupLink = waitAndGetElementByXpath(driver, "//a[contains(text(),'New customers')]");
        signupLink.click();

        waitAndGetElement(driver, waitAndGetElementByXpath(driver, "//h1[text()='Create Account']"));
        waitForJS(driver);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
        Date currentDate = new Date();

        String password = "password123";
        String emailAddress = "sample_" + formatter.format(currentDate) + "@email.com";
        String firstName = "User" + formatter.format(currentDate);
        String lastName = "Test";
        log.info(emailAddress);

        WebElement firstNameInput = waitAndGetElementByName(driver, "firstname");
        firstNameInput.click();
        clearAndFillField(firstNameInput, firstName);

        WebElement lastNameInput = waitAndGetElementByName(driver, "lastname");
        lastNameInput.click();
        clearAndFillField(lastNameInput, lastName);

        WebElement address1Input = waitAndGetElementByName(driver, "address1");
        clearAndFillField(address1Input, "101 31st street apt.1");

        WebElement postcodeInput = waitAndGetElementByName(driver, "postcode");
        clearAndFillField(postcodeInput, "94111");

        WebElement cityInput = waitAndGetElementByName(driver, "city");
        clearAndFillField(cityInput, "Random City");

        WebElement countrySelectArrow = waitAndGetElement(driver, By.xpath("//span[@class='select2-selection__arrow']"));
        countrySelectArrow.click();
        WebElement countrySearchField = waitAndGetElementByXpath(driver, "//input[@class='select2-search__field']");
        clearAndFillField(countrySearchField, "United States");
        waitAndGetElement(driver, By.xpath("//ul/li[contains(@id,'US') and text()='United States']")).click();
        Assert.assertEquals("Select value is incorrect", "United States",
                waitAndGetElementByXpath(driver, "//span[contains(@id,'select2-country_code')]").getText());

        WebElement zoneStateSelect = waitAndGetElementByXpath(driver, "//select[@name='zone_code']");
        WebElement caStateSelectOption = waitAndGetElement(driver, zoneStateSelect.findElement(By.xpath("./option[@value='CA']")));
        caStateSelectOption.click();
        Assert.assertEquals("Selected state is not California","true", caStateSelectOption.getAttribute("selected"));

        WebElement emailInput = waitAndGetElementByName(driver, "email");
        clearAndFillField(emailInput, emailAddress);

        WebElement phoneInput = waitAndGetElementByName(driver, "phone");
        clearAndFillField(phoneInput, "+11234556677");

        WebElement passwordInput = waitAndGetElementByName(driver, "password");
        clearAndFillField(passwordInput, password);

        WebElement confirmedPasswordInput = waitAndGetElementByName(driver, "confirmed_password");
        clearAndFillField(confirmedPasswordInput, password);

        WebElement createAccountButton = waitAndGetElementByName(driver, "create_account");
        createAccountButton.click();

        WebElement successAlert = waitAndGetElement(driver, By.xpath("//div[contains(@class,'success')]"));
        Assert.assertEquals("Account was not created successfully", "Your customer account has been created.", successAlert.getText());

        WebElement logoutButton = waitAndGetElementByXpath(driver, "//li/a[text()='Logout']");
        logoutButton.click();
        waitForUrlToBe(driver, "http://localhost/litecart/en/");

        WebElement emailInputLoginForm = waitAndGetElementByName(driver, "email");
        clearAndFillField(emailInputLoginForm, emailAddress);

        WebElement passwordInputLoginForm = waitAndGetElementByName(driver, "password");
        clearAndFillField(passwordInputLoginForm, password);

        waitAndGetElementByName(driver, "login").click();

        successAlert = waitAndGetElement(driver, By.xpath("//div[contains(@class,'success')]"));
        Assert.assertEquals("User was not logged in successfully", "You are now logged in as " + firstName + " " + lastName + ".", successAlert.getText());

        logoutButton = waitAndGetElementByXpath(driver, "//li/a[text()='Logout']");
        logoutButton.click();

    }

}
