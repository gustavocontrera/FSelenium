package varios2.clase09;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.List;

public class EjerciciosSelenium {

    @Test
    public void ejercicio1(){
        //Muestra por consola el título de la página y la url
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.bbc.com");
        driver.manage().window().maximize();

        System.out.println("Titulo de la pagina "  + driver.getTitle());
        System.out.println("URL de la pagina "  + driver.getCurrentUrl());

        driver.close();
    }

    @Test
    public void bbcMundoTest(){
        String URL_BBC_MUNDO = "https://www.bbc.com/mundo";
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(URL_BBC_MUNDO);

        List<WebElement> listaH1s = driver.findElements(By.tagName("h1"));
        System.out.println("La cantidad de H1s del sitio son: " + listaH1s.size() );
        System.out.println("---> Elementos H1: <---");
        for (WebElement elementoH1: listaH1s) {
            System.out.println(elementoH1.getText());
        }
        System.out.println("*****************");


        List<WebElement> listaH2s = driver.findElements(By.tagName("h2"));
        System.out.println("La cantidad de H2s del sitio son: " + listaH2s.size());


        System.out.println("---> Elementos H2: <---");
        for (WebElement elementoH2: listaH2s) {
            System.out.println(elementoH2.getText());
        }
        System.out.println("*****************");

        //  <a>
        List<WebElement> listaLinks = driver.findElements(By.tagName("a"));
        System.out.println("Cantidad de Links del sitio: " + listaLinks.size());

        int contadorLinksSinTexto = 0;
        for (WebElement link : listaLinks) {
            //is empty es una opción
            //if (link.getText().isEmpty() == false) {
            if (!link.getText().isEmpty()) {
                System.out.println("Link --> " + link.getText());
            } else {
                contadorLinksSinTexto++;
            }
        }

        System.out.println("Se encontraron " + contadorLinksSinTexto + " links sin texto!!!!");

        driver.close();
    }

    @Test
    public void netflixTest(){
        String URL_NETFLIX = "https://www.netflix.com";
        //System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(URL_NETFLIX);

        List<WebElement> listaBotones = driver.findElements(By.tagName("button"));
        System.out.println("Cantidad de botones: " + listaBotones.size());

        for (WebElement boton: listaBotones) {
            System.out.println("Button: " + boton.getText());
        }

        listaBotones.get(0).click();

        // WebElement cuantoCuestaNetflixBtn = listaBotones.get(2);
        // cuantoCuestaNetflixBtn.click();

        driver.close();

    }

}
