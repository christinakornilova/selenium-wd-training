import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import testbase.AdminTestBase;
import java.util.*;

import static litecart.common.Utils.*;

public class BrowserLogsEx17 extends AdminTestBase {

    @Test
    public void testBrowserLogs() {
        login();

        //navigate to Catalog page
        String catalogUrl = "http://localhost/litecart/admin/?app=catalog&doc=catalog&category_id=1";
        driver.get(catalogUrl);
        waitForUrlToBe(driver, catalogUrl);
        WebElement pageTitle = waitAndGetElementByXpath(driver, "//h1");
        Assert.assertTrue("Page title doe not match", pageTitle.getText().contains("Catalog"));
        waitAndGetElementByXpath(driver, "//a[text()='Subcategory']").click();

        String productsListLocator = "//tr/td/input[contains(@name,'products')]/parent::td/following-sibling::td/a[not(@title='Edit')]";
        List<WebElement> productsList = waitAndGetElementsListByXpath(driver, productsListLocator);
        List<LogEntry> browserLog = new ArrayList<>();

        //open each product page and check for browser logs
        for(int i = 0; i < productsList.size(); i++) {
            waitAndGetElementsListByXpath(driver, productsListLocator).get(i).click();
            waitAndGetElementByXpath(driver, "//h1[contains(text(),'Edit Product')]");
            driver.manage().logs().get(LogType.BROWSER).getAll().forEach(l -> browserLog.add(l));
            driver.navigate().back();
        }

        //show browser logs if detected
        if (browserLog.size() > 0) {
            for (LogEntry l: browserLog) {
                log.info(l.getLevel() + " : " + l.getMessage());
            }
        }

        Assert.assertTrue("Browser log contains entries", browserLog.isEmpty());

    }
}
