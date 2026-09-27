package varios2.clase12;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.Tools;

import java.util.List;

public class SpotifyWithTestngTest {

    public WebDriver driver;

    @BeforeMethod
    public void setup(){
        WebDriver driver = Tools.getDriver("https://www.spotify.com");
        driver.manage().window().maximize();
    }

    @Test (priority = 1, groups = {"successTest"})
    public void verifySpotifyTitle() {
        Assert.assertEquals(driver.getTitle(),"Escuchar es todo - Spotify");
    }

    @Test (priority = 2, groups = {"successTest"})
    @Parameters({"username", "password"})
    public void cssSelectorByPlaceHolderTest(@Optional("qa@qa.com") String testEmail,@Optional ("holamundo") String password) throws InterruptedException {

        Thread.sleep(2000);
        driver.findElement(By.cssSelector("a[href='https://www.spotify.com/ar/signup/']")).click();
        driver.findElement(By.cssSelector("input[placeholder='Introduce tu correo electrónico.']")).sendKeys(testEmail);
    }

    @Test (priority = 3, groups = {"successTest"})
    @Parameters({"tagName"})
    public void parametersFromTestngXML(@Optional("a") String tagNameParameter){

        List<WebElement> tagElementList = driver.findElements(By.tagName(tagNameParameter));

        for (WebElement elemento : tagElementList){
            System.out.println("--> " + elemento.getText());
        }
    }

    @Test (priority = 4, groups = {"failureTest"})
    public void spotifyErrorsTest() throws InterruptedException {

        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@href='https://www.spotify.com/ar/signup/']")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@type='submit']")).click();

        List<WebElement> listaErrores = driver.findElements(By.xpath("//*[@aria-label='Indicador de error']"));
        Assert.assertEquals(listaErrores.size(), 9, "Se esperaba tener 9 errores");

        boolean errorContraseñaPresente = false;
        for (WebElement element : listaErrores){
            System.out.println("--> " + element.getText());
            if (element.getText().equals("Debes introducir una contraseña.")){
                errorContraseñaPresente = true;
            }
        }

        Assert.assertTrue(errorContraseñaPresente, "No se encontró el error de la contraseña");

        driver.findElement(By.xpath("//*[contains(text(), 'Hombre')]")).click();
        WebElement perfilVacioErrorElement = driver.findElement(By.xpath("//*[contains(text(), 'Introduce un nombre para tu perfil.')]"));
        Assert.assertEquals(perfilVacioErrorElement.getText(),"Introduce un nombre para tu perfil.", "El error de perfil vacio deberia estar presente" );
    }

    @Test (priority = 5, groups = {"failureTest"})
    public void invalidEmailTest() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@href='https://www.spotify.com/ar/signup/']")).click();
        driver.findElement(By.xpath("//*[@placeholder='Introduce tu correo electrónico.']")).sendKeys("test.com");
        driver.findElement(By.xpath("//*[@placeholder='Vuelve a introducir tu correo electrónico.']")).sendKeys("test.com");
        WebElement emailError = driver.findElement(By.xpath("//span[contains(text(), 'Este correo electrónico no es válido. Asegúrate de que tenga un formato como este: ejemplo@email.com')]"));
        Assert.assertEquals(emailError.getText(), "Este correo electrónico no es válido. Asegúrate de que tenga un formato como este: ejemplo@email.com");
    }

    @Test (priority = 6, groups = {"successTest"})
    public void validateExistingEmail() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@href='https://www.spotify.com/ar/signup/']")).click();
        driver.findElement(By.xpath("//*[@placeholder='Introduce tu correo electrónico.']")).sendKeys("test@test.com");
        driver.findElement(By.xpath("//*[@placeholder='Vuelve a introducir tu correo electrónico.']")).sendKeys("test@test.com");

        Thread.sleep(2000);
        WebElement ErrorMesage = driver.findElement(By.xpath("//*[contains(text(), 'Este correo electrónico ya está conectado a una cuenta.')]"));
        Assert.assertEquals(ErrorMesage.getText(), "Este correo electrónico ya está conectado a una cuenta. Inicia sesión.");
    }

    private static final String EMAIL_ERROR = "Es necesario que introduzcas tu correo electrónico.";
    private static final String CONFIRMATION_ERROR = "Es necesario que confirmes tu correo electrónico.";
    private static final String PASSWORD_ERROR = "Debes introducir una contraseña.";
    private static final String PROFILE_ERROR = "Introduce un nombre para tu perfil.";
    private static final String DAY_ERROR = "Indica un día del mes válido.";
    private static final String MONTH_ERROR = "Selecciona tu mes de nacimiento.";
    private static final String YEAR_ERROR = "Indica un año válido.";
    private static final String SEX_ERROR = "Selecciona tu sexo.";
    private static final String CAPTCHA_ERROR = "Confirma que no eres un robot.";

    @Test (priority = 7, groups = {"failureTest"})
    public void checkErrorMessage() throws InterruptedException {

        driver.findElement(By.xpath("//a[@href='https://www.spotify.com/ar/signup/']")).click();

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@type='submit']")).click();

        WebElement emailMessage = driver.findElement(By.xpath("//*[contains(text(), 'Es necesario que introduzcas tu correo electrónico.')]"));
        WebElement confirmaationMessage = driver.findElement(By.xpath("//*[contains(text(), 'Es necesario que confirmes tu correo electrónico.')]"));
        WebElement passwordMessage = driver.findElement(By.xpath("//*[contains(text(), 'Debes introducir una contraseña.')]"));
        WebElement profileMessage = driver.findElement(By.xpath("//*[contains(text(), 'Introduce un nombre para tu perfil.')]"));
        WebElement dayMessage = driver.findElement(By.xpath("//*[contains(text(), 'Indica un día del mes válido.')]"));
        WebElement monthMessage = driver.findElement(By.xpath("//*[contains(text(), 'Selecciona tu mes de nacimiento.')]"));
        WebElement yearMessage = driver.findElement(By.xpath("//*[contains(text(), 'Indica un año válido.')]"));
        WebElement sexMessage = driver.findElement(By.xpath("//*[contains(text(), 'Selecciona tu sexo.')]"));
        WebElement captchalMessage = driver.findElement(By.xpath("//*[contains(text(), 'Confirma que no eres un robot.')]"));

        Assert.assertEquals(emailMessage.getText(), EMAIL_ERROR);
        Assert.assertEquals(confirmaationMessage.getText(), CONFIRMATION_ERROR);
        Assert.assertEquals(passwordMessage.getText(), PASSWORD_ERROR);
        Assert.assertEquals(profileMessage.getText(), PROFILE_ERROR);
        Assert.assertEquals(dayMessage.getText(), DAY_ERROR);
        Assert.assertEquals(monthMessage.getText(), MONTH_ERROR);
        Assert.assertEquals(yearMessage.getText(), YEAR_ERROR);
        Assert.assertEquals(sexMessage.getText(), SEX_ERROR);
        Assert.assertEquals(captchalMessage.getText(), CAPTCHA_ERROR);

    }

    @AfterMethod
    public void closeDriver() throws InterruptedException {
        Thread.sleep(3000);
        driver.close();
    }

}
