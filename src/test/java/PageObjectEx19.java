import litecart.pages.CartPage;
import litecart.pages.MainPage;
import litecart.pages.ProductPage;
import org.junit.Assert;
import org.junit.Test;
import testbase.CatalogTestBase;

public class PageObjectEx19 extends CatalogTestBase {

    @Test
    public void testCart() {
        MainPage mainPage = new MainPage(driver);

        //add 3 elements to cart
        for (int i = 0; i < 3; i++) {
            ProductPage productPage = mainPage.openProductPage(0);
            productPage.addElementToCart(i);
            Assert.assertEquals("Items qty differs", String.valueOf(i + 1), mainPage.getCartItemsQty());
            mainPage = productPage.backToMainPage();
        }

        //open cart and remove all elements from it
        CartPage cartPage = mainPage.openCartPage();
        cartPage.removeAllElements();

        Assert.assertEquals("Cart 'no items' text differs", "There are no items in your cart.",
                cartPage.getNoItemsLabelText());
        Assert.assertTrue("No '<< Back' link appeared after all elements were deleted from the cart",
                cartPage.isBackLinkPresent());
    }

}
