package varios2.clase114;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SpotifyTest {

    public WebDriver driver;
    private static String DRIVER_NAME_TEST = "Firefox";

    public SpotifyTest(String driverName_){

        if (driverName_ == Constants.CHROME_DRIVER_NAME || driverName_ == Constants.FIREFOX_DRIVER_NAME || driverName_ == Constants.IE_DRIVER_NAME){
            this.DRIVER_NAME_TEST = driverName_;
        }
    }

    @BeforeMethod
    public void setup() {

        if (DRIVER_NAME_TEST.equals(Constants.FIREFOX_DRIVER_NAME)){
            //System.setProperty("webdriver.chrome.driver", "drivers/firefoxdriver");
            //driver = new FirefoxDriver();
            System.out.println("Se instancia el driver de FIREFOX!!");

        } else if (DRIVER_NAME_TEST.equals(Constants.FIREFOX_DRIVER_NAME)){
            //System.setProperty("webdriver.chrome.driver", "drivers/internetexplorerdriver");
            //driver = new InternetExplorerDriver();
            System.out.println("Se instancia el driver de IE!!");

        } else {
            //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
            // driver = new ChromeDriver();
            System.out.println("Se instancia el driver de CHROME!!");
        }

       /* driver.get("https://www.airbnb.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();*/
    }

    @Test
    public void test1(){
        System.out.println("Esto es un test");
    }

}
