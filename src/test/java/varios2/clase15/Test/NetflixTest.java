package varios2.clase15.Test;

import varios2.clase15.PageObject.NetflixLandingPage;
import varios2.clase15.PageObject.NetflixLoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Tools;

public class NetflixTest {

    public WebDriver driver;

    @BeforeMethod
    public void setup(){
        WebDriver driver = Tools.getDriver("https://www.netflix.com");
        driver.manage().window().maximize();
    }

    @Test
    public void netflixTest() throws InterruptedException {

        NetflixLandingPage netflixLandingPage = new NetflixLandingPage(driver);

        String title = netflixLandingPage.getNetflixTitle();
        String currentUrl = netflixLandingPage.getNetflixCurrentUrl();

        Assert.assertTrue(title.contains("Ve series online, ve películas online"), "Se esperaba otro titulo");
        Assert.assertEquals(currentUrl, "https://www.netflix.com/ar/", "Se esperaba otra url");

        NetflixLoginPage netflixLoginPage = netflixLandingPage.clickOnIniciarSesion();

        title = netflixLoginPage.getNetflixTitle();
        currentUrl = netflixLoginPage.getNetflixCurrentUrl();

        Assert.assertEquals(netflixLoginPage.getH1Text(), "Inicia sesión", "Se esperaba el h1 Inicia Sesion!!");

        Thread.sleep(2000);
        Assert.assertTrue(title.contains("Netflix"), "Se esperaba otro titulo");
        Assert.assertTrue(currentUrl.contains("/login"), "La URL no contiene la palabra login" );

        netflixLoginPage.fillEmailField("test@test.com");
        netflixLoginPage.fillPasswordField("holamundo");
    }






}
