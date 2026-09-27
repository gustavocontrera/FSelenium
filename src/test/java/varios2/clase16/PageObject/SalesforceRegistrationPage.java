package varios2.clase16.PageObject;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SalesforceRegistrationPage extends BasePage {

    public SalesforceRegistrationPage (WebDriver remoteDriver){
        driver = remoteDriver;
    }

    public void fillRegistrationFields(){
        Faker faker = new Faker();

        driver.findElement(By.name("UserFirstName")).sendKeys(faker.name().firstName());
        driver.findElement(By.name("UserLastName")).sendKeys(faker.name().lastName());
        driver.findElement(By.name("UserTitle")).sendKeys(faker.job().title());
        driver.findElement(By.name("UserEmail")).sendKeys(faker.internet().emailAddress());
        driver.findElement(By.name("UserPhone")).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(By.name("CompanyName")).sendKeys("Salesforce");

        /*WebElement industryElement = driver.findElement(By.name("Lead.Industry"));
        Select industrySelect = new Select(industryElement);
        industrySelect.selectByValue("Manufacturing");*/

    }


}
