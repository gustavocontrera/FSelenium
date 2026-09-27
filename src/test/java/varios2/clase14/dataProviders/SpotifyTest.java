package varios2.clase14.dataProviders;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Tools;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SpotifyTest {

    public WebDriver driver;

    @BeforeMethod
    public void setup(){
        WebDriver driver = Tools.getDriver("https://www.spotify.com");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    //                 ===> Es necesario que introduzcas tu correo electrónico.

    // test@test.com  ===> Este correo electrónico ya está conectado a una cuenta. Inicia sesión.

    // qaselenium ===> Este correo electrónico no es válido. Asegúrate de que tenga un formato como este: ejemplo@email.com
//                 {"", "email_vacio"},
//                {"test@test.com", "email_ya_registrado"},
//                {"qaselenium", "email_invalido"},
//                {"selenium12mayo@gmail.com", "email_correcto"}
//


    @Test(dataProvider = "registro", dataProviderClass = DataProviderGenerator.class)
    public void spotifyRegistrationTest(String anEmail, String emailRecibido){

        driver.findElement(By.xpath("//a[@href='https://www.spotify.com/ar/signup/']")).click();
        driver.findElement(By.name("email")).sendKeys(anEmail);
        driver.findElement(By.xpath("//*[@type='submit']")).click();

        if (emailRecibido.equals("email_vacio")){
            WebElement errorMsgElement = driver.findElement(By.xpath("//*[contains(text(), 'Es necesario que introduzcas tu correo electrónico.')]"));
            Assert.assertEquals(errorMsgElement.getText(), "Es necesario que introduzcas tu correo electrónico.", "Se esperaba el error del email vacio...");
            List<WebElement> listaErrores = driver.findElements(By.xpath("//*[@aria-label='Indicador de error']"));
            Assert.assertEquals(listaErrores.size(), 9, "Se esperaba tener 9 errores");

        } else if (emailRecibido.equals("email_ya_registrado")){
            WebElement errorMsgElement = driver.findElement(By.xpath("//*[contains(text(), 'Este correo electrónico ya está conectado a una cuenta.')]"));
            Assert.assertEquals(errorMsgElement.getText(), "Este correo electrónico ya está conectado a una cuenta.", "Se esperaba el error del email ya registrado...");
            //Assert.assertTrue(errorMsgElement.getText().contains("ya está conectado a una cuenta"));
            List<WebElement> listaErrores = driver.findElements(By.xpath("//*[@aria-label='Indicador de error']"));
            Assert.assertEquals(listaErrores.size(), 9, "Se esperaba tener 9 errores");

        } else if (emailRecibido.equals("email_invalido")){
            WebElement errorMsgElement = driver.findElement(By.xpath("//*[contains(text(), 'Este correo electrónico no es válido. Asegúrate de que tenga un formato como este: ejemplo@email.com')]"));
            Assert.assertEquals(errorMsgElement.getText(), "Este correo electrónico no es válido. Asegúrate de que tenga un formato como este: ejemplo@email.com", "Se esperaba el error del email invalido...");
            List<WebElement> listaErrores = driver.findElements(By.xpath("//*[@aria-label='Indicador de error']"));
            Assert.assertEquals(listaErrores.size(), 9, "Se esperaba tener 9 errores");

        } else if (emailRecibido.equals("email_correcto")){
            List<WebElement> listaErrores = driver.findElements(By.xpath("//*[@aria-label='Indicador de error']"));
            Assert.assertEquals(listaErrores.size(), 8, "Se esperaba tener 8 errores");
        }


    }

    @AfterMethod
    public void closeDriver(){
        driver.close();
    }

}
