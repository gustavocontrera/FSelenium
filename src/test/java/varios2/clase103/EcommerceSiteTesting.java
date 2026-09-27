package varios2.clase103;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class EcommerceSiteTesting {

    public WebDriver driver;
    public static String H1_AUTHENTICATION = "AUTHENTICATION";

    @BeforeMethod
    public void setup() {
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://automationpractice.com/index.php");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();
    }

    @Test
    public void registrationTest() throws InterruptedException {

        completeRegistration();

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
        driver.close();

    }

    @Test
    public void loginTest() throws InterruptedException {

        completeRegistration();

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

        //SIGN IN.....
        WebElement signInElement = driver.findElement(By.id("SubmitLogin"));
        Assert.assertEquals(signInElement.getText(), "Sign in", "No se encontro el boton de Sign In");

        WebElement emailAddressLoginElement = driver.findElement(By.id("email"));
        emailAddressLoginElement.sendKeys(Constants.EMAIL_ADDRESS);

        WebElement passwordElement = driver.findElement(By.id("passwd"));
        passwordElement.sendKeys(Constants.PASSWORD);

        signInElement.click();

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
        completeRegistration();
        WebElement logoutElement = driver.findElement(By.className("logout"));
        logoutElement.click();
        WebElement signInElement = driver.findElement(By.id("SubmitCreate"));
        Assert.assertEquals(signInElement.getText(), "Create an account", "No se encontro el boton de Create an account");
        WebElement emailRegistrationField = driver.findElement(By.id("email_create"));
        emailRegistrationField.sendKeys(Constants.EMAIL_ADDRESS);
        signInElement.click();

        WebElement errorMsgElement = driver.findElement(By.xpath("//*[@id='create_account_error']/ol/li"));
        System.out.println("---> " + errorMsgElement.getText());
        Assert.assertEquals(errorMsgElement.getText(), "An account using this email address has already been registered. Please enter a valid password or request a new one.", "No se encontro el error esperado");
    }



    private void completeRegistration() throws InterruptedException {
        driver.findElement(By.xpath("//*[@href = 'http://automationpractice.com/index.php?controller=my-account']")).click();

        Assert.assertEquals(driver.getTitle(), "Login - My Store", "Se esperaba otro titlo!!");
        Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php?controller=authentication&back=my-account", "Se esperaba otra URL!!");
        Assert.assertTrue(driver.getCurrentUrl().contains("authentication"));

        WebElement h1Element = driver.findElement(By.tagName("h1"));
        Assert.assertEquals(h1Element.getText(), H1_AUTHENTICATION, "Se esperaba el h1: " + H1_AUTHENTICATION);

        //String emailAddress = "seleniumintermedio" + Math.random() + "test@gmail.com";

        driver.findElement(By.name("email_create")).sendKeys(Constants.EMAIL_ADDRESS);
        driver.findElement(By.id("SubmitCreate")).click();

        Thread.sleep(3000);
        WebElement emailElement = driver.findElement(By.id("email"));
        String populatedEmail = emailElement.getAttribute("value");
        System.out.println("-----> populatedEmail: " + populatedEmail);
        Assert.assertEquals(populatedEmail, Constants.EMAIL_ADDRESS, "Los emails deberian de coincidir!!!");

        WebElement maleRadioButton = driver.findElement(By.id("id_gender1"));

        maleRadioButton.click();

    /*Faker faker = new Faker();
    String fakeFirstName = faker.name().firstName();
    String fakeLastName = faker.name().lastName();
    */
        WebElement firstName = driver.findElement(By.id("customer_firstname"));
        WebElement lastName = driver.findElement(By.id("customer_lastname"));
        WebElement password = driver.findElement(By.id("passwd"));
        WebElement daySelector = driver.findElement(By.id("days"));
        WebElement monthSelector = driver.findElement(By.id("months"));
        WebElement yearSelector = driver.findElement(By.id("years"));
        WebElement company = driver.findElement(By.id("company"));
        WebElement address1 = driver.findElement(By.id("address1"));
        WebElement address2 = driver.findElement(By.id("address2"));
        WebElement city = driver.findElement(By.id("city"));
        WebElement stateSelector = driver.findElement(By.id("id_state"));
        WebElement postcode = driver.findElement(By.id("postcode"));
        WebElement countrySelector = driver.findElement(By.id("id_country"));
        WebElement additionalInfo = driver.findElement(By.id("other"));
        WebElement phone = driver.findElement(By.id("phone"));
        WebElement mobile = driver.findElement(By.id("phone_mobile"));
        WebElement addressAlias = driver.findElement(By.id("alias"));
        WebElement registerButton = driver.findElement(By.id("submitAccount"));

        maleRadioButton.click();
        firstName.sendKeys(Constants.FAKER_FIRST_NAME);
        lastName.sendKeys(Constants.FAKER_LAST_NAME);
        password.sendKeys(Constants.PASSWORD);

        Select selectDays = new Select(daySelector);
        selectDays.selectByValue(Constants.BIRTH_DAY);
        Select selectMonths = new Select(monthSelector);
        selectMonths.selectByValue(Constants.BIRTH_MONTH);
        Select selectYears = new Select(yearSelector);
        selectYears.selectByValue(Constants.BIRTH_YEAR);
        company.sendKeys("MyCompany");
        address1.sendKeys("My address Nr1");
        address2.sendKeys("My address Nr2");
        city.sendKeys(Constants.CITY);
        Select selectState = new Select(stateSelector);
        selectState.selectByValue("2");
        postcode.sendKeys("10000");
        Select selectCountry = new Select(countrySelector);
        selectCountry.selectByValue("21");
        additionalInfo.sendKeys("Additional information");
        phone.sendKeys("123456");
        mobile.sendKeys("123456789");
        addressAlias.clear();
        addressAlias.sendKeys("MyAlias");

        //clicking on register button
        registerButton.click();
    }

}
