package litecart.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    public static Logger log;

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        log = LogManager.getLogger(this);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        log.info("navigating");
    }
}
