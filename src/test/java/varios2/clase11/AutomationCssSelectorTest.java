package varios2.clase11;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class AutomationCssSelectorTest {

    public WebDriver getChromeDriver(String URL) {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        return driver;
    }

    @Test
    public void registrationTestById() throws InterruptedException {
        WebDriver driver = getChromeDriver("http://www.automationpractice.pl/index.php");
        driver.manage().window().maximize();

        Thread.sleep(2000);

        WebElement registrarseBtn = driver.findElement(By.cssSelector("[href='http://www.automationpractice.pl/index.php?controller=my-account']"));
        registrarseBtn.click();

        driver.findElement(By.cssSelector("#email_create")).sendKeys("testing@gus.com");
        driver.findElement(By.cssSelector("#SubmitCreate")).click();

        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#customer_firstname")).sendKeys("Juan");
        driver.findElement(By.cssSelector("[name='customer_lastname']")).sendKeys("Perez");
        driver.findElement(By.cssSelector("[name='passwd']")).sendKeys("12345");

        WebElement daysElement = driver.findElement(By.cssSelector("[name='days']"));
        Select daySelect = new Select(daysElement);
        daySelect.selectByIndex(12);

        //driver.findElement(By.cssSelector("[placeholder='Introduce un nombre de perfil.']")).sendKeys("automation");

    }

    @Test
    public void registrationTestByXpath() throws InterruptedException {
        WebDriver driver = getChromeDriver("http://www.automationpractice.pl/index.php");
        driver.manage().window().maximize();

        Thread.sleep(2000);

        WebElement registrarseBtn = driver.findElement(By.cssSelector("[href='http://www.automationpractice.pl/index.php?controller=my-account']"));
        registrarseBtn.click();

        driver.findElement(By.xpath("/html/body/div[1]/div[2]/div/div[3]/div/div/div[1]/form/div/div[2]/input")).sendKeys("testi2@gmail.com");

    }
}
