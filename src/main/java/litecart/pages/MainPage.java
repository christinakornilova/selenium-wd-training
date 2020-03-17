package litecart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static litecart.common.Utils.*;

public class MainPage extends HeaderPage {
    final static String homePageUrl = "http://localhost/litecart/en/";

    @FindBy(name = "login")
    WebElement loginButton;

    @FindBy(xpath = "//a/img[@src='/litecart/images/slides/1-lonely-duck.jpg']")
    WebElement duckImage;

    @FindBy(xpath = "//li[contains(@class,'product')]/a[@title]")
    List<WebElement> productList;

    @FindBy(linkText = "Checkout »")
    WebElement checkoutLink;


    public MainPage(WebDriver driver) {
        super(driver);
        waitForMainPageToLoad();
    }

    public void waitForMainPageToLoad() {
        waitForUrlToBe(driver, homePageUrl);
        waitAndGetElement(driver, loginButton);
        waitAndGetElement(driver, duckImage);
        waitAndGetElementsList(driver, productList);
        waitAndGetElement(driver, cartItemQtyLabel);
    }

    public ProductPage openProductPage(int index) {
        log.info("1st Item is " + productList.get(index).findElement(By.xpath("./div[@class='name']")).getText());
        productList.get(index).click();
        return new ProductPage(driver);
    }

    public CartPage openCartPage() {
        waitAndGetElement(driver, checkoutLink).click();
        return new CartPage(driver);
    }

}
