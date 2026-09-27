package varios2.clase102;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class EcommerceSiteTest {

    public WebDriver driver;
    public static String H1_AUTHENTICATION = "AUTHENTICATION";

    @BeforeMethod
    public void setup() {
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();
    }

    @Test
    public void registrationTest() throws InterruptedException {
        driver.findElement(By.xpath("//*[@href = 'http://automationpractice.com/index.php?controller=my-account']")).click();

        Assert.assertEquals(driver.getTitle(), "Login - My Store", "Se esperaba otro titlo!!");
        Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php?controller=authentication&back=my-account", "Se esperaba otra URL!!");
        Assert.assertTrue(driver.getCurrentUrl().contains("authentication"));

        WebElement h1Element = driver.findElement(By.tagName("h1"));
        Assert.assertEquals(h1Element.getText(), H1_AUTHENTICATION, "Se esperaba el h1: " + H1_AUTHENTICATION);

        String emailAddress = "seleniumintermedio" + Math.random() + "test@gmail.com";
        System.out.println("--->  " +  emailAddress);

        driver.findElement(By.name("email_create")).sendKeys(emailAddress);
        driver.findElement(By.id("SubmitCreate")).click();

        Thread.sleep(3000);
        WebElement emailElement = driver.findElement(By.id("email"));
        String populatedEmail = emailElement.getAttribute("value");
        System.out.println("-----> populatedEmail: " + populatedEmail);
        Assert.assertEquals(populatedEmail, emailAddress, "Los emails deberian de coincidir!!!");

        WebElement maleRadioButton = driver.findElement(By.id("id_gender1"));
        WebElement firstNameField = driver.findElement(By.id("customer_firstname"));
        WebElement lastNameField = driver.findElement(By.id("customer_lastname"));

        maleRadioButton.click();
        firstNameField.sendKeys("Alan");
        lastNameField.sendKeys("Rowen");
    }

}
