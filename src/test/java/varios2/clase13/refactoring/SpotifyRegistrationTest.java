package varios2.clase13.refactoring;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Tools;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SpotifyRegistrationTest {

    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeMethod
    public void setup(){
        WebDriver driver = Tools.getDriver("https://www.spotify.com");
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void registrationEmailAlreadyRegisteredTest() throws InterruptedException {

        driver.findElement(By.xpath("//a[@href='https://www.spotify.com/ar/signup/']")).click();

        //wait.until(ExpectedConditions.urlToBe("https://www.spotify.com/ar/signup"));
        //wait.until(ExpectedConditions.titleIs("Registrarte - Spotify"));

        SpotifyFillingForm spotifyFillingForm = new SpotifyFillingForm(driver);
        spotifyFillingForm.fillingFields("test@test.com","test@test.com", "holamundo123", "12");

        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@aria-label='Indicador de error']")));

        WebElement emailAlreadyRegisteredError = driver.findElement(By.xpath("//*[@aria-label='Indicador de error']"));

        Assert.assertEquals(emailAlreadyRegisteredError.getText(), "Este correo electrónico ya está conectado a una cuenta. Inicia sesión.", "Se esperaba un mensaje de error ya que el email ya esta registrado" );
        Assert.assertTrue(emailAlreadyRegisteredError.getText().contains("Este correo electrónico ya está conectado a una cuenta."), "Error, se esperaba otro mensaje de error");
        Assert.assertTrue(emailAlreadyRegisteredError.getText().contains("Inicia sesión."));
    }

    @Test
    public void registrationSuccessTest() throws InterruptedException {
        driver.findElement(By.xpath("//a[@href='https://www.spotify.com/ar/signup/']")).click();

        SpotifyFillingForm spotifyFillingForm = new SpotifyFillingForm(driver);
        spotifyFillingForm.fillingFields("testing2021selenium@testing.com","testing2021selenium@testing.com", "holamundo123", "12");

        //dos formas de verificar que no aparezca ningún error
        List<WebElement> errorList = driver.findElements(By.xpath("//*[@aria-label='Indicador de error']"));
        Assert.assertEquals(errorList.size(), 0, "No deberian de haber errores!!");
        Assert.assertTrue(errorList.isEmpty(), "La lista deberia estar vacia!!");
    }

}
