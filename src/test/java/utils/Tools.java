package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Clase de utilidades legadas.
 * 
 * @deprecated Esta clase contiene métodos de inicialización que no soportan ThreadLocal (Thread-Safety).
 * Se recomienda migrar su uso hacia {@link utils.DriverManager}.
 * 
 * @author QA Automation Senior
 */
@Deprecated
public class Tools {

    /**
     * Inicializa una nueva instancia de ChromeDriver de forma aislada y navega a una URL.
     * Nota: Esta instancia NO se registra en DriverManager, por lo que debe cerrarse manualmente.
     * 
     * @param URL Dirección web de destino.
     * @return Una instancia no administrada de WebDriver.
     */
    public static WebDriver getDriver(String URL) {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        return driver;
    }
}
