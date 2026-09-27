package varios2.clase110.ejemplo6;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(value = TestListener.class)
public class SpotifyTest {
    WebDriver driver;

    //@BeforeMethod
    public void setup(ITestContext context){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.spotify.com");
        context.setAttribute("WebDriver", driver);
    }

    @Test (invocationCount = 1, successPercentage = 100)
    public void spotifyTest() throws InterruptedException {
        System.out.println("---> driver Title " + driver.getTitle());
        System.out.println("---> "+ System.getProperty("user.dir"));
        Thread.sleep(3000);
        driver.navigate().to("https://www.spotify.com/ar/signup/");

        driver.findElement(By.xpath("//button[@type]")).click();
        Assert.assertEquals(2,3);
    }

    private int i = 0;
    @Test (invocationCount = 5, successPercentage = 100)
    //si el successPercentage = 0, aunque fallen todos los tests, el resultado sera exitoso
    //si el successPercetage = 60, entonces tienen que pasar 3 de las 5 ejecuciones
    //si el successPercetage = 80, entonces tienen que pasar 4 de las 5 ejecuciones
    //si el successPercentage = 100, todas las ejecuciones deben ser exitosas
    public void percentageTest(){
        i++;
        System.out.println("----> se invoco al test " + i );

        if (i == 1 || i == 2){
            System.out.println("El test debe fallar");
            Assert.assertTrue(false);
        }

    }
}
