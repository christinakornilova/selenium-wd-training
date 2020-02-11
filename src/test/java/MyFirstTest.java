import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyFirstTest {
    private WebDriver driver;

    @Before
    public void start() {
        driver = new ChromeDriver();
    }

    @Test
    public void myFirstTest() {
        driver.get("http://www.google.com/");
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }
}
