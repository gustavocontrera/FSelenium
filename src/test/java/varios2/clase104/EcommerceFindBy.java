package varios2.clase104;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class EcommerceFindBy extends BasePage{

    @BeforeMethod
    public void setup() {
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();
        PageFactory.initElements(driver, this);
    }

    //@Test (invocationCount = 5, successPercentage = 60)
    @Test
    public void registrationTest() throws InterruptedException {
        EcommerceHelper helper = new EcommerceHelper(driver);
        helper.completeRegistration();

        WebElement myPersonaInformationBtn = driver.findElement(By.xpath("//*[@href='http://automationpractice.com/index.php?controller=identity']"));
        myPersonaInformationBtn.click();

        WebElement myPersonalInformationElement = driver.findElement(By.tagName("h1"));
        Assert.assertEquals(myPersonalInformationElement.getText(), "YOUR PERSONAL INFORMATION", "Se esperaba obtener otro h1!!");

        WebElement nameElement = driver.findElement(By.id("firstname"));
        String actualName = nameElement.getAttribute("value");

        WebElement lastNameElement = driver.findElement(By.id("lastname"));
        String actualLastName = lastNameElement.getAttribute("value");

        Assert.assertEquals(actualName, Constants.FAKER_FIRST_NAME, "Los nombres no coinciden!!" );
        Assert.assertEquals(actualLastName, Constants.FAKER_LAST_NAME, "Los apellidos no coinciden!!" );
        //driver.close();
    }

    @Test
    public void loginTest() throws InterruptedException {
        EcommerceHelper helper = new EcommerceHelper(driver);
        helper.completeRegistration();

        WebElement logoutElement = driver.findElement(By.className("logout"));

        WebElement nameElement = driver.findElement(By.xpath("(//*[@href='http://automationpractice.com/index.php?controller=my-account']/span)[1]"));
        System.out.println("---> " + nameElement.getText());

        String fullName = Constants.FAKER_FIRST_NAME + " " + Constants.FAKER_LAST_NAME;
        Assert.assertEquals(nameElement.getText(), fullName, "Se esperaba otro nombre!!");
        Assert.assertTrue(nameElement.getText().contains(Constants.FAKER_FIRST_NAME), "El primer nombre no se encontró");
        Assert.assertTrue(nameElement.getText().contains(Constants.FAKER_LAST_NAME), "El apellido no se encontró");
        Assert.assertEquals(logoutElement.getText(), "Sign out", "El link Signout deberia estar presente");

        //LOG OUT.....
        logoutElement.click();

        Assert.assertEquals(loginBtn.getText(), "Sign in", "No se encontro el boton de Sign In");

        WebElement emailAddressLoginElement = driver.findElement(By.id("email"));
        emailAddressLoginElement.sendKeys(Constants.EMAIL_ADDRESS);

        WebElement passwordElement = driver.findElement(By.id("passwd"));
        passwordElement.sendKeys(Constants.PASSWORD);

        //SIGN IN.....
        loginBtn.click();

        logoutElement = driver.findElement(By.className("logout"));

        nameElement = driver.findElement(By.xpath("(//*[@href='http://automationpractice.com/index.php?controller=my-account']/span)[1]"));
        System.out.println("---> " + nameElement.getText());

        fullName = Constants.FAKER_FIRST_NAME+ " " + Constants.FAKER_LAST_NAME;
        Assert.assertEquals(nameElement.getText(), fullName, "Se esperaba otro nombre!!");
        Assert.assertTrue(nameElement.getText().contains(Constants.FAKER_FIRST_NAME), "El primer nombre no se encontró");
        Assert.assertTrue(nameElement.getText().contains(Constants.FAKER_LAST_NAME), "El apellido no se encontró");
        Assert.assertEquals(logoutElement.getText(), "Sign out", "El link Signout deberia estar presente");

        //driver.close();
    }

    @Test
    public void uniqueEmailTest() throws InterruptedException {
        EcommerceHelper helper = new EcommerceHelper(driver);
        helper.completeRegistration();

        WebElement logoutElement = driver.findElement(By.className("logout"));
        logoutElement.click();
        WebElement signInElement = driver.findElement(By.id("SubmitCreate"));
        Assert.assertEquals(signInElement.getText(), "Create an account", "No se encontro el boton de Create an account");
        WebElement emailRegistrationField = driver.findElement(By.id("email_create"));

        String emailAddress = "seleniumintermedio" + Math.random() + "test@gmail.com";

        emailRegistrationField.sendKeys(emailAddress);
        signInElement.click();

        WebElement errorMsgElement = driver.findElement(By.xpath("//*[@id='create_account_error']/ol/li"));
        System.out.println("---> " + errorMsgElement.getText());
        Assert.assertEquals(errorMsgElement.getText(), "An account using this email address has already been registered. Please enter a valid password or request a new one.", "No se encontro el error esperado");
    }

    @AfterMethod
    public void closeDriver(){
        driver.close();
    }


}
