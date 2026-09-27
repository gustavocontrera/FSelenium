package varios2.clase104;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BasePage {

    public WebDriver driver;
    public static String H1_AUTHENTICATION = "AUTHENTICATION";

    @FindBy (xpath =  "//*[@href = 'http://automationpractice.com/index.php?controller=my-account']")
    public WebElement loginBtn;

    @FindBy (tagName = "h1")
    public WebElement autenticationH1;

    @FindBy (name = "email_create")
    public WebElement emailNewAccountField;

    @FindBy (id = "SubmitCreate")
    public WebElement createAccountBtn;

    @FindBy (id = "email")
    public WebElement emailUserField;

    @FindBy (id = "id_gender1")
    public WebElement maleGenderRadioButton;

    @FindBy (id ="customer_firstname")
    public WebElement firstNameField;

    @FindBy (id ="customer_lastname")
    public WebElement lastNameField;

    @FindBy (id = "passwd")
    public WebElement passwdField;
}
