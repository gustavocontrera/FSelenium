package varios2.clase105.pages;

import varios2.clase104.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreateAccountPage {

    public WebDriver driver;
    @FindBy(id = "email")
    public WebElement emailUserField;

    @FindBy (id = "id_gender1")
    public WebElement maleGenderRadioButton;

    @FindBy (id ="customer_firstname")
    public WebElement firstNameField;

    @FindBy (id ="customer_lastname")
    public WebElement lastNameField;

    @FindBy (id = "passwd")
    public WebElement passwdField;
    public CreateAccountPage(WebDriver remoteDriver){
        driver = remoteDriver;
        PageFactory.initElements(driver, this);
    }
    public String getEmailValue() {
        return emailUserField.getAttribute("value");
    }
    public void fillRegistrationFields(){

        maleGenderRadioButton.click();

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

    }

    public MyAccountPage clickOnRegisterAccountBtn(){
        WebElement registerButton = driver.findElement(By.id("submitAccount"));
        registerButton.click();

        MyAccountPage nextPage = new MyAccountPage(driver);
        return nextPage;

    }

}
