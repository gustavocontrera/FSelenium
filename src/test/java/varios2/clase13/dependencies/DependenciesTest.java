package varios2.clase13.dependencies;

import varios2.clase13.refactoring.SpotifyFillingForm;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.Tools;

public class DependenciesTest {

    public WebDriver driver;

    @BeforeTest
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
    }

    @Test (dependsOnMethods = {"landingPageTest"})
    public void fillingFieldsTest() throws InterruptedException {
        SpotifyFillingForm spotifyFillingForm = new SpotifyFillingForm(driver);
        spotifyFillingForm.fillingFields("test@test.com","test@test.com", "holamundo123", "12");

        Thread.sleep(2000);
        WebElement emailAlreadyRegisteredError = driver.findElement(By.xpath("//*[@aria-label='Indicador de error']"));

    }

    @Test (dependsOnMethods = {"fillingFieldsTest"})
    public void validateErrorMessage(){
        WebElement emailAlreadyRegisteredError = driver.findElement(By.xpath("//*[@aria-label='Indicador de error']"));

        Assert.assertEquals(emailAlreadyRegisteredError.getText(), "Este correo electrónico ya está conectado a una cuenta. Inicia sesión.", "Se esperaba un mensaje de error ya que el email ya esta registrado" );
        Assert.assertTrue(emailAlreadyRegisteredError.getText().contains("Este correo electrónico ya está conectado a una cuenta."), "Error, se esperaba otro mensaje de error");
        Assert.assertTrue(emailAlreadyRegisteredError.getText().contains("Inicia sesión."));

    }

}
