package litecart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import static litecart.common.Utils.*;

public class ProductPage extends HeaderPage {

    private static final String productPageUrlPart = "/rubber-ducks-c-1";

    @FindBy(xpath = "//a[@href='#tab-information']")
    WebElement tabInfo;

    @FindBy(xpath = "//a[@href='#tab-details']")
    WebElement tabDetails;

    @FindBy(name = "login")
    WebElement login;

    @FindBy(xpath = "//h1")
    WebElement pageHeader;

    @FindBy(xpath = "//select[@name='options[Size]']")
    WebElement sizeSelect;

    @FindBy(name = "add_cart_product")
    WebElement addToCartButton;

    public ProductPage(WebDriver driver) {
        super(driver);
        waitForProductPageToLoad();
    }

    public void waitForProductPageToLoad() {
        waitForPageUrlToContain(driver, productPageUrlPart);
        waitAndGetElement(driver, tabInfo);
        waitAndGetElement(driver, tabDetails);
        waitAndGetElement(driver, login);
        waitAndGetElement(driver, pageHeader);
    }

    public void addElementToCart(int index) {
        if (isElementPresent(sizeSelect)) {
            new Select(sizeSelect).selectByValue("Small");
        }
        addToCartButton.click();
        waitUntilCartItemsQtyToBe(index + 1);
    }
}
