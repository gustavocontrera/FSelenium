package varios2.clase10;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Tools;

import java.util.List;

public class  FacebookTest {

    private WebDriver getFacebookDriver(){
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.facebook.com");
        return driver;
    }

    @Test
    public void facebookLinksTest() throws InterruptedException {

        WebDriver driver = getFacebookDriver();

        System.out.println("URL " + driver.getCurrentUrl());
        System.out.println("Titulo de la pagina "  + driver.getTitle());

        driver.findElement(By.linkText("¿Olvidaste tu contraseña?")).click();

        System.out.println("URL " + driver.getCurrentUrl());
        System.out.println("Titulo de la pagina "  + driver.getTitle());

        Thread.sleep(2000);
        driver.close();
    }

    @Test
    public void facebookPartialLinkTest() throws InterruptedException {

        WebDriver driver = Tools.getDriver("https://www.facebook.com");

        System.out.println("Titulo --> " + driver.getTitle());
        System.out.println("URL --> " + driver.getCurrentUrl());

        Assert.assertEquals(driver.getTitle(), "Facebook - Inicia sesión o regístrate");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/");

        driver.findElement(By.partialLinkText("¿Olvidaste")).click();

        System.out.println("Se ha clickeado Olvidaste tu contraseña....");

        System.out.println("Titulo --> " + driver.getTitle());
        System.out.println("URL --> " + driver.getCurrentUrl());

        Assert.assertEquals(driver.getTitle(), "¿Olvidaste tu contraseña? | No puedo iniciar sesión | Facebook");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/login/identify/?ctx=recover&ars=facebook_login&from_login_screen=0");

        Thread.sleep(2000);
        //driver.close();
    }

    @Test
    public void facebookRegistrationTest() throws InterruptedException {

        WebDriver driver = Tools.getDriver("https://www.facebook.com");

        driver.findElement(By.linkText("Crear cuenta nueva")).click();

        // si el driver no puede encontrar el elemento.. entonces hay dos posibilidades:
        // 1. se esta localizando mal el elemento.. es decir, no existe ese name en este caso..
        // 2. los elementos no estan cargados en el sitio cuando el driver va a buscarlos...

        Thread.sleep(2000);

        driver.findElement(By.name("firstname")).sendKeys("Jhon");
        driver.findElement(By.name("lastname")).sendKeys("Smith");

        WebElement daysElement = driver.findElement(By.name("birthday_day"));
        WebElement monthElement = driver.findElement(By.name("birthday_month"));
        WebElement yearElement = driver.findElement(By.name("birthday_year"));

        Select daySelect = new Select(daysElement);
        Select monthSelect = new Select(monthElement);
        Select yearSelect = new Select(yearElement);

        daySelect.selectByIndex(20);
        monthSelect.selectByVisibleText("ago");
        yearSelect.selectByValue("1995");

        List<WebElement> listaSexos = driver.findElements(By.name("sex"));
        Assert.assertEquals(listaSexos.size(), 3);

        WebElement sexoMascElement = listaSexos.get(1);
        sexoMascElement.click();

        driver.findElement(By.name("reg_email__")).sendKeys("test@qa.com");
        driver.findElement(By.id("password_step_input")).sendKeys("holamundo");

        Thread.sleep(2000);
        driver.close();
    }


}
