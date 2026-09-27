package varios2.clase15.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NetflixLoginPage {

    public WebDriver driver;

    public NetflixLoginPage(WebDriver remoteDriver){
        driver = remoteDriver;
    }

    public String getNetflixTitle (){
        return driver.getTitle();
    }

    public String getNetflixCurrentUrl (){
        return driver.getCurrentUrl();
    }

    public String getH1Text(){
       return driver.findElement(By.tagName("h1")).getText();
    }

    public void fillEmailField(String anEmail){
        driver.findElement(By.id("id_userLoginId")).sendKeys(anEmail);
    }

    public void fillPasswordField(String aPassword){
        driver.findElement(By.id("id_password")).sendKeys(aPassword);
    }
}
