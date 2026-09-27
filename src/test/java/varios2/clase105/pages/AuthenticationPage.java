package varios2.clase105.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuthenticationPage{
    public WebDriver driver;

    @FindBy(tagName = "h1")
    public WebElement autenticationH1;

    @FindBy (name = "email_create")
    public WebElement emailNewAccountField;

    @FindBy (id = "SubmitCreate")
    public WebElement createAccountBtn;

    public AuthenticationPage(WebDriver remoteDriver){
        this.driver = remoteDriver;
        PageFactory.initElements(driver, this);
    }
    public String getPageTitle(){
        return driver.getTitle();
    }
    public String getPageURL(){
        return driver.getCurrentUrl();
    }
    public String getAutenticationH1Text(){
        return autenticationH1.getText();
    }

    public void fillEmailAddress(String anEmail){
        emailNewAccountField.sendKeys(anEmail);
    }

    public CreateAccountPage clickOnCreateAccountBtn(){
        createAccountBtn.click();
        CreateAccountPage nextPage = new CreateAccountPage(driver);
        return nextPage;
    }

    public void clickOnSignInBtn(){
        WebElement nameElement = driver.findElement(By.xpath("(//*[@href='http://automationpractice.com/index.php?controller=my-account']/span)[1]"));
    }
}