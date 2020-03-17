package litecart.pages;

import litecart.common.Utils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static litecart.common.Utils.defaultTimeout;

public class HeaderPage extends BasePage {
    final static String cartItemQtyLabelLocator = "//span[@class='quantity']";

    @FindBy(xpath = cartItemQtyLabelLocator)
    WebElement cartItemQtyLabel;

    @FindBy(xpath = "//a[@href='http://localhost/litecart/en/']/img/parent::a")
    WebElement mainPageLink;

    public HeaderPage(WebDriver driver) {
        super(driver);
        Utils.waitAndGetElement(driver, cartItemQtyLabel);
    }

    public String getCartItemsQty() {
        return cartItemQtyLabel.getText();
    }

    public void waitUntilCartItemsQtyToBe(int itemsQty) {
        new WebDriverWait(driver, defaultTimeout)
                .until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(cartItemQtyLabelLocator),
                        Integer.toString(itemsQty)));
    }

    public MainPage backToMainPage() {
        mainPageLink.click();
        return new MainPage(driver);
    }
}
