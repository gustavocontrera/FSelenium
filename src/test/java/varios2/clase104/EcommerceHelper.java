package varios2.clase104;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class EcommerceHelper extends BasePage {

    public WebDriver driver;

    public EcommerceHelper(WebDriver remoteDriver){
        driver = remoteDriver;
        PageFactory.initElements(driver, this);
    }

    public void completeRegistration() throws InterruptedException {
        //driver.findElement(By.xpath("//*[@href = 'http://automationpractice.com/index.php?controller=my-account']")).click();
        loginBtn.click();

        Assert.assertEquals(driver.getTitle(), "Login - My Store", "Se esperaba otro titlo!!");
        Assert.assertEquals(driver.getCurrentUrl(), "http://automationpractice.com/index.php?controller=authentication&back=my-account", "Se esperaba otra URL!!");
        Assert.assertTrue(driver.getCurrentUrl().contains("authentication"));

        Assert.assertEquals(autenticationH1.getText(), H1_AUTHENTICATION, "Se esperaba el h1: " + H1_AUTHENTICATION);

        String emailAddress = "seleniumintermedio" + Math.random() + "test@gmail.com";

        emailNewAccountField.sendKeys(emailAddress);
        createAccountBtn.click();

        Thread.sleep(6000);
        //WebElement emailElement = driver.findElement(By.id("email"));
        String populatedEmail = emailUserField.getAttribute("value");
        System.out.println("-----> populatedEmail: " + populatedEmail);
        Assert.assertEquals(populatedEmail, emailAddress, "Los emails deberian de coincidir!!!");

        maleGenderRadioButton.click();

        /*Faker faker = new Faker();
        String fakeFirstName = faker.name().firstName();
        String fakeLastName = faker.name().lastName();
        */
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

        maleGenderRadioButton.click();
        firstNameField.sendKeys(Constants.FAKER_FIRST_NAME);
        lastNameField.sendKeys(Constants.FAKER_LAST_NAME);
        passwdField.sendKeys(Constants.PASSWORD);

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
