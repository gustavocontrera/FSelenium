package varios2.clase16.Test;

import varios2.clase16.PageObject.SalesforceLandingPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.Tools;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public WebDriver driver;
    protected SalesforceLandingPage salesforceLandingPage;

    @BeforeMethod
    public void setup(){
        WebDriver driver = Tools.getDriver("https://www.salesforce.com");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        salesforceLandingPage = new SalesforceLandingPage(driver);

    }

    @AfterMethod
    public void closeDriver() throws InterruptedException {
        Thread.sleep(3000);
        driver.close();
    }

}
