package varios2.clase102;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AirbnbTest {

    public WebDriver driver;
    public static String BUDAPEST_CITY = "Budapest";

    @BeforeMethod
    public void setup() {
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
        WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.airbnb.com");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();
    }

    @Test
    public void busquedaAlojamientoTest() throws InterruptedException {
        Thread.sleep(2000);

        driver.findElement(By.id("bigsearch-query-detached-query")).sendKeys(BUDAPEST_CITY);
        driver.findElement(By.xpath("//*[@data-testid='structured-search-input-field-split-dates-0']")).click();

        driver.findElement(By.xpath("//*[@data-testid='datepicker-day-2021-06-26']")).click();

        driver.findElement(By.xpath("//*[@data-testid='datepicker-day-2021-07-15']")).click();

        driver.findElement(By.xpath("//*[@data-testid='structured-search-input-field-guests-button']")).click();

        WebElement aumentarAdultosElement = driver.findElement(By.xpath("(//*[@aria-label='aumentar valor'])[1]"));
        aumentarAdultosElement.click();
        aumentarAdultosElement.click();

        WebElement aumentarNiñosElement = driver.findElement(By.xpath("(//*[@aria-label='aumentar valor'])[2]"));
        aumentarNiñosElement.click();

        driver.findElement(By.xpath("//*[@data-testid='structured-search-input-search-button']")).click();

        //Hasta acá se realiza la búsqueda

        Thread.sleep(1500);

        List<WebElement> tresHuespedesList = driver.findElements(By.xpath("//*[contains(text(), '3 huéspedes')]"));
        Assert.assertEquals(tresHuespedesList.size(), 3, "Deberia haber tres elementos en la lista!!");

        boolean encontroTexto = false;
        for (WebElement elemento : tresHuespedesList){
            if (elemento.getText().contains("26 jun.") && elemento.getText().contains("15 jul.")){
                encontroTexto = true;
            }
        }
        Assert.assertTrue(encontroTexto);

        WebElement h1Element = driver.findElement(By.tagName("h1"));
        Assert.assertEquals(h1Element.getText(), "Estancias en " + BUDAPEST_CITY);
        Assert.assertTrue(h1Element.getText().contains(BUDAPEST_CITY));
        Assert.assertTrue(h1Element.getText().endsWith(BUDAPEST_CITY));

        boolean encontroBudapest = false;
        List<WebElement> h1List = driver.findElements(By.tagName("h1"));
        for (WebElement h1: h1List){
            if (h1.getText().contains(BUDAPEST_CITY)){
                encontroBudapest = true;
                break;
            }
        }

        Assert.assertTrue(encontroBudapest);

    }

}
