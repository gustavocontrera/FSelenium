package varios2.clase105.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage {
    public WebDriver driver;

    public MyAccountPage(WebDriver remoteDriver){
        this.driver = remoteDriver;
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle(){

        return driver.getTitle();
    }

    public String getPageURL(){

        return driver.getCurrentUrl();
    }
    public AuthenticationPage clickOnLogoutBtn(){
        WebElement logoutElement = driver.findElement(By.className("logout"));
        AuthenticationPage nextPage = new AuthenticationPage(driver);
        return nextPage;
    }
    public String getLogoutBtnText(){
        WebElement logoutElement = driver.findElement(By.className("logout"));
        return logoutElement.getText();
    }
    public String getAccountName(){
        WebElement nameElement = driver.findElement(By.xpath("(//*[@href='http://automationpractice.com/index.php?controller=my-account']/span)[1]"));
        return nameElement.getText();
    }

}
