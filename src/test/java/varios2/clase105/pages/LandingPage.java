package varios2.clase105.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {
    public WebDriver driver;

    @FindBy(xpath =  "//*[@href = 'http://automationpractice.com/index.php?controller=my-account']")
    public WebElement loginBtnMenu;

    public LandingPage(WebDriver remoteDriver){
        this.driver = remoteDriver;
        PageFactory.initElements(driver, this);
    }

    public AuthenticationPage loginBtnClick(){
        loginBtnMenu.click();
        AuthenticationPage nextPage = new AuthenticationPage(driver);
        return nextPage;
    }


}
