package varios2.clase105.tests;

import varios2.clase105.pages.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public WebDriver driver;
    public LandingPage landingPage;

    public static String H1_AUTHENTICATION = "AUTHENTICATION";

    @BeforeMethod
    public void setup() {
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();
        PageFactory.initElements(driver, this);
        landingPage = new LandingPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
