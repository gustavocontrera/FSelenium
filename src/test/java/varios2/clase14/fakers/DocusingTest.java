package varios2.clase14.fakers;

import varios2.clase14.dataProviders.DataProviderGenerator;
import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Tools;

public class DocusingTest {


    public WebDriver driver;

    @BeforeMethod
    public void setup(){
        WebDriver driver = Tools.getDriver("https://go.docusign.com/o/trial/");
        driver.manage().window().maximize();

    }

    @Test(dataProvider = "registro", dataProviderClass = DataProviderGenerator.class)
    public void docusignTest(String anEmail, String aMessage){
        Faker faker = new Faker();

        driver.findElement(By.name("first_name")).sendKeys(faker.name().firstName());
        driver.findElement(By.name("last_name")).sendKeys(faker.name().lastName());
        driver.findElement(By.name("email")).sendKeys(anEmail);
        driver.findElement(By.name("phone")).sendKeys(faker.phoneNumber().cellPhone());
        driver.findElement(By.name("title")).sendKeys(faker.job().title());
    }

}
