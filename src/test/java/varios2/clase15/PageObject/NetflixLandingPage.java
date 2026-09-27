package varios2.clase15.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NetflixLandingPage {

    public WebDriver driver;

    public NetflixLandingPage(WebDriver remoteDriver){
        driver = remoteDriver;
    }

    public String getNetflixTitle (){
        return driver.getTitle();
    }

    public String getNetflixCurrentUrl (){
        return driver.getCurrentUrl();
    }

    public NetflixLoginPage clickOnIniciarSesion(){
        driver.findElement(By.xpath("//*[@href='/login']")).click();
        NetflixLoginPage netflixLoginPage = new NetflixLoginPage(driver);
        return netflixLoginPage;
    }


}
