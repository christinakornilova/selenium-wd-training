import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BrowsersExampleTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
//        driver = new ChromeDriver();
//        driver = new FirefoxDriver();
//        driver = new SafariDriver();
        DesiredCapabilities caps = new DesiredCapabilities();
//        caps.setCapability(FirefoxDriver.BINARY, "/Applications/Firefox Nightly.app/Contents/MacOS/firefox-bin"); //nightly
        caps.setCapability(FirefoxDriver.BINARY, "/Applications/Firefox esr.app/Contents/MacOS/firefox-bin"); //ff esr
        caps.setCapability(FirefoxDriver.MARIONETTE, "false");
        driver = new FirefoxDriver(caps);
//        FirefoxOptions options = new FirefoxOptions().setLegacy(true);
//        driver = new FirefoxDriver(options);
        System.out.println(((HasCapabilities) driver).getCapabilities());
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void myFirstTest() {
        driver.get("http://www.google.com/");
    }

    @After()
    public void stop() {
        System.out.println("Quit driver");
        driver.quit();
        driver = null;
    }
}
