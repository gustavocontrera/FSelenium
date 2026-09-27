package varios2.clase13.refactoring;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SpotifyFillingForm {

    public WebDriver driver;

    public SpotifyFillingForm(WebDriver remoteDriver){
        driver = remoteDriver;
    }

    public void fillingFieldsWithAlreadyRegisteredEmail(){
        driver.findElement(By.name("email")).sendKeys("test@test.com");
        driver.findElement(By.id("confirm")).sendKeys("test@test.com");
        driver.findElement(By.xpath("//*[@type='password']")).sendKeys("holamundo");
        driver.findElement(By.id("day")).sendKeys("12");
    }

    public void fillingFieldsSuccessfully(){
        driver.findElement(By.name("email")).sendKeys("test5mayo2021@test5mayo2021.com");
        driver.findElement(By.id("confirm")).sendKeys("test5mayo2021@test5mayo2021.com");
        driver.findElement(By.xpath("//*[@type='password']")).sendKeys("holamundo");
        driver.findElement(By.id("day")).sendKeys("12");
    }

    public void fillingFields(String anEmail, String aConfirmationEmail, String aPassoword, String aDay){
        driver.findElement(By.name("email")).sendKeys(anEmail);
        driver.findElement(By.id("confirm")).sendKeys(aConfirmationEmail);
        driver.findElement(By.xpath("//*[@type='password']")).sendKeys(aPassoword);
        driver.findElement(By.id("day")).sendKeys(aDay);
    }

}
