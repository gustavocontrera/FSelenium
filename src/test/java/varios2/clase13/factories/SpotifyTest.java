package varios2.clase13.factories;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Tools;

public class SpotifyTest {

    public WebDriver driver;
    private String email;

    //constructor con un parametro...
    public SpotifyTest(String anEmail){
        this.email = anEmail;
    }

    @BeforeMethod
    public void setup(){
        WebDriver driver = Tools.getDriver("https://www.spotify.com");
        driver.manage().window().maximize();
    }

    @Test
    public void landingPageTest(){
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.spotify.com/ar/", "Se esperaba otra URL");
        Assert.assertEquals(driver.getTitle(), "Escuchar es todo - Spotify", "Se esperaba otro titulo");

        driver.findElement(By.xpath("//a[@href='https://www.spotify.com/ar/signup/']")).click();

        Assert.assertEquals(driver.getCurrentUrl(), "https://www.spotify.com/ar/signup/", "Se esperaba otra URL");
        Assert.assertEquals(driver.getTitle(), "Registrarte - Spotify", "Se esperaba otro titulo");

        driver.findElement(By.name("email")).sendKeys(email);

    }

}
