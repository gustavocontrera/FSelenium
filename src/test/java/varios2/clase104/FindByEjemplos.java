package varios2.clase104;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FindByEjemplos {

    String URL = "https://www.netflix.com";

    public WebDriver driver;

    @BeforeMethod
    public void setup() {
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();
        PageFactory.initElements(driver, this);
    }

    @FindBy(tagName = "p")
    public List<WebElement> listaParrafos;
    @FindBy (tagName = "a")
    public List<WebElement> listaLinks;
    @FindBy (name = "email")
    private WebElement emailField;

    @FindBy (xpath = "//*[@href='/login']")
    private WebElement iniciarSesionBtn;

    @FindBy (linkText = "Privicidad y Seguridad")
    private WebElement privacidadLink;

    @Test
    public void ejercicio1(){

        Assert.assertNotEquals(listaParrafos.size(), 0);
        Assert.assertNotEquals(listaLinks.size(), 0);

        for (WebElement parrafo: listaParrafos){
            if (parrafo.getText().isEmpty() == false) {
                System.out.println("--> " + parrafo.getText());
            }
        }

        System.out.println("**** Links ****");
        for (WebElement link: listaLinks){
            if (link.getText().isEmpty() == false) {
                System.out.println("--> " + link.getText());
            }
        }

        emailField.sendKeys("test@testing.com");
        iniciarSesionBtn.click();

        driver.close();

    }

}
