package varios2.clase101;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class WebElementsTest {

    public WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
    }

    @Test
    public void primerTest() {
        driver.get("https://www.spotify.com");

        //WebElement fakeElement = driver.findElement(By.tagName("cursooooo"));

        List<WebElement> fakeList = driver.findElements(By.xpath("//*[@class='error']"));

        //los 4 assert siguientes son 4 formas de hacer lo mismo
        //Assert.assertNotEquals(fakeList.size(), 0,  "No se encontraron elementos en la lista");

        Assert.assertFalse(fakeList.isEmpty(), "No se encontraron elementos en la lista");

        //Assert.assertTrue(fakeList.isEmpty() == false);
        // isEmpty retorna true si la lista esta vacia...
        // isEmpty retorna false si la lista tiene elementos

        //Assert.assertFalse(fakeList.isEmpty() == true);

        for (WebElement e : fakeList) {
            System.out.println("---> " + e.getText());
        }

        System.out.println("Fin del test");
    }

    @Test
    public void bookingTest () {
        driver.get("https://www.booking.com");

        List<WebElement> listaH2s = driver.findElements(By.tagName("h2"));
        Assert.assertNotEquals(listaH2s.size(), 0);

        boolean encontreTexto = false;

        for (WebElement h2 : listaH2s) {
            System.out.println(h2.getText());
            if (h2.getText().equals("Conectate con otros viajeros")) {
                encontreTexto = true;
            }
        }

        Assert.assertTrue(encontreTexto, "No se encontro el texto");
        //Assert.assertFalse(encontreTexto == false);

    }

    @Test
    public void Netflixtest() {
        driver.get("https://www.netflix.com");

        WebElement h1 = driver.findElement(By.tagName("h1"));
        System.out.println("H1: " + h1.getText());
        System.out.println(h1.getAttribute("class"));
        System.out.println(h1.getAttribute("data-uia"));

    }

    @Test
    public void mundoMacTest() {
        driver.get("https://www.mundomac.com");

        WebElement elementoTienda = driver.findElement(By.id("menu-item-8120"));
        WebElement elementoDos = elementoTienda.findElement(By.tagName("ul"));

        //System.out.println("--> " + elementoTienda.getText());

        List<WebElement> elementosDeLaTienda = elementoDos.findElements(By.tagName("li"));
        Assert.assertFalse(elementosDeLaTienda.isEmpty(), "No se encontraron elementos dentro del menu");
        System.out.println("Cantidad de elementos " + elementosDeLaTienda.size());

        for (WebElement elemento : elementosDeLaTienda) {
            WebElement link = elemento.findElement(By.tagName("a"));
            System.out.println("---> " + link.getText());
        }

    }

}
