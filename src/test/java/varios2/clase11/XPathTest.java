package varios2.clase11;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import utils.Tools;

public class XPathTest {

    @Test
    public void xpathByPlaceHolderTest() throws InterruptedException {
        WebDriver driver = Tools.getDriver("https://www.spotify.com");
        driver.manage().window().maximize();

        Thread.sleep(2000);
        driver.findElement(By.xpath("//a[@href='https://www.spotify.com/ar/signup/']")).click();

        Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@placeholder='Introduce tu correo electrónico.']")).sendKeys("test@test.com");
        driver.findElement(By.xpath("//*[@placeholder='Vuelve a introducir tu correo electrónico.']")).sendKeys("test@test.com");
        driver.findElement(By.xpath("//*[@placeholder='Crea una contraseña.']")).sendKeys("holamundo");
    }

    @Test
    public void xpathByNameTest() throws InterruptedException {

        WebDriver driver = Tools.getDriver("https://www.spotify.com");

        driver.findElement(By.xpath("//a[@href='https://www.spotify.com/ar/signup/']")).click();

        //Thread.sleep(2000);
        driver.findElement(By.xpath("//*[@name='email']")).sendKeys("test@test.com");
        driver.findElement(By.xpath("//*[@name='confirm']")).sendKeys("test@test.com");
    }
    
    @Test
    public void cssSelectorByPlaceHolderTest() throws InterruptedException {

        WebDriver driver = Tools.getDriver("https://www.spotify.com");

        Thread.sleep(2000);
        driver.findElement(By.cssSelector("a[href='https://www.spotify.com/ar/signup/']")).click();

        driver.findElement(By.cssSelector("input[placeholder='Introduce tu correo electrónico.']")).sendKeys("test@test.com");
        
    }

}
