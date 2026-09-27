package varios2.clase108;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class EcommerceWithActions {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void registrationTest() throws InterruptedException {
        Actions actions = new Actions(driver);

        WebElement loginBtn = driver.findElement(By.xpath("//*[@href = 'http://automationpractice.com/index.php?controller=my-account']"));
        actions.moveToElement(loginBtn).click().build().perform();

        String emailAddress = "seleniumintermedio" + Math.random() + "test@gmail.com";

        WebElement emailField = driver.findElement(By.name("email_create"));

        Action completeEmailFieldAction =
                actions.moveToElement(emailField)
                        .click()
                        .sendKeys(emailAddress)
                        .build();
        completeEmailFieldAction.perform();

        //driver.findElement(By.id("SubmitCreate")).click();
        WebElement submitBtn = driver.findElement(By.id("SubmitCreate"));
        actions.moveToElement(submitBtn).click().build().perform();

        Thread.sleep(3000);

        WebElement maleRadioButton = driver.findElement(By.id("id_gender1"));

        Action maleRadioButtonAction = actions.moveToElement(maleRadioButton).click().build();
        maleRadioButtonAction.perform();

        WebElement firstNameElement = driver.findElement(By.id("customer_firstname"));

        Action firstNameAction = actions
                .moveToElement(firstNameElement)
                .click()
                .sendKeys("Jona")
                .keyDown(Keys.SHIFT)
                .sendKeys("than")
                .keyUp(Keys.SHIFT)
                .build();

        WebElement lastNameElement = driver.findElement(By.id("customer_lastname"));
        WebElement password = driver.findElement(By.id("passwd"));

        Action lastNameAction = actions
                .moveToElement(lastNameElement)
                .click()
                .keyDown(Keys.SHIFT)
                .sendKeys("Rodriguez")
                .keyUp(Keys.SHIFT)
                .build();

        Action passwordAction = actions
                .moveToElement(password)
                .click()
                .sendKeys("holamundo")
                .build();

        firstNameAction.perform();
        lastNameAction.perform();
        passwordAction.perform();

    }
}
