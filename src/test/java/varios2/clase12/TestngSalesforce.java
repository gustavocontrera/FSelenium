package varios2.clase12;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Tools;

public class TestngSalesforce {

    public static final String SALESFORCE_URL = "https://login.salesforce.com/";

    public WebDriver driver;

    @BeforeMethod
    public void setup(){
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        WebDriver driver = Tools.getDriver(SALESFORCE_URL);
        driver.manage().window().maximize();
    }

    @Test (priority = 1)
    public void validateSalesforceLogo() {
        WebElement e = driver.findElement (By.id("logo"));
        System.out.println(e.getTagName());
        System.out.println(e.getAttribute("alt"));
    }

    @Test (priority = 4)
    public void RememberMeIsSelected () {
        WebElement rememberMe = driver.findElement (By.name("rememberUn"));
        rememberMe.click();
        Assert.assertTrue(rememberMe.isSelected());
    }

    @Test (enabled = false) //(priority = 2)
    public void FooterIsValid() {
        WebElement footer = driver.findElement(By.id("footer"));
        Assert.assertTrue(footer.getText().contains("All rights reserved"));
    }

    @Test (priority = 3)
    public void LoginFailureTest () {
        WebElement userName = driver.findElement(By.id("username"));
        userName.sendKeys("test@test.com");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("123466");
        driver.findElement(By.id("Login")).click();
        WebElement elementError = driver.findElement(By.id("error"));
        System.out.println(elementError.getText());
        String expectedError = "Your access to salesforce.com has been disabled by your system administrator. Please contact your Administrator for more information.";
        Assert.assertEquals(elementError.getText(), expectedError);
    }

    @AfterMethod
    public void closeDriver() throws InterruptedException {
        Thread.sleep(3000);
        driver.close();
    }

}
