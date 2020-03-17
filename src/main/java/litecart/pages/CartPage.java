package litecart.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static litecart.common.Utils.*;

public class CartPage extends BasePage {
    private static final String cartPageUrl = "http://localhost/litecart/en/checkout";

    @FindBy(xpath = "//td[@class='item']")
    List<WebElement> cartItemsList;

    @FindBy(name = "remove_cart_item")
    WebElement removeButton;

    @FindBy(linkText = "<< Back")
    WebElement backLink;

    @FindBy(xpath = "//p/em")
    WebElement noItemsInCartLabel;

    public CartPage(WebDriver driver) {
        super(driver);
        waitForUrlToBe(driver, cartPageUrl);
        waitAndGetElementsList(driver, cartItemsList);
    }

    public void removeAllElements() {
        for (int i = cartItemsList.size(); cartItemsList.size() > 0; i--)  {
            log.info("cartItemsList.size() = " + cartItemsList.size());
            waitAndGetElement(driver, removeButton).click();
            waitForStalenessOfElement(driver, cartItemsList.get(i-1));
            if (i > 1) cartItemsList = waitAndGetElementsList(driver, cartItemsList);
        }
        waitAndGetElement(driver, noItemsInCartLabel);
        waitAndGetElement(driver, backLink);

    }

    public String getNoItemsLabelText() {
        return noItemsInCartLabel.getText();
    }

    public boolean isBackLinkPresent() {
        return isElementPresent(backLink);
    }
}
