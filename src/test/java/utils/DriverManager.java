package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

/**
 * Gestor de ciclo de vida de WebDriver seguro para hilos (Thread-safe).
 * Utiliza ThreadLocal para asegurar ejecuciones paralelas limpias y sin interferencias entre hilos.
 * 
 * @author QA Automation Senior
 */
public class DriverManager {

    // ThreadLocal que mantiene una instancia de WebDriver independiente para cada hilo
    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    /**
     * Obtiene el WebDriver correspondiente al hilo actual.
     * Si no existe, inicializa una nueva instancia basada en las configuraciones globales.
     * 
     * @return Instancia activa de WebDriver para el hilo actual.
     */
    public static WebDriver getDriver() {
        if (threadLocalDriver.get() == null) {
            // Inicializar el driver si el hilo actual no tiene uno asignado
            initializeDriver();
        }
        return threadLocalDriver.get();
    }

    /**
     * Inicializa el WebDriver basado en el navegador configurado en config.properties.
     * Soporta Chrome, Firefox y Edge.
     */
    private static synchronized void initializeDriver() {
        // Leemos el navegador deseado desde config.properties, por defecto usamos chrome
        String browser = ConfigReader.getProperty("browser");
        if (browser == null) {
            browser = "chrome";
        }
        browser = browser.toLowerCase().trim();

        WebDriver driver;

        switch (browser) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                // Configuramos opciones de Chrome para estabilidad
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(options);
                break;
        }

        // Leer timeout por defecto desde config.properties (o usar 10 segundos por defecto)
        String timeoutString = ConfigReader.getProperty("timeout");
        int timeout = (timeoutString != null) ? Integer.parseInt(timeoutString) : 10;

        // Configuramos esperas implícitas base y maximizado de ventana
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().window().maximize();

        // Guardamos el driver en el contenedor del hilo actual
        threadLocalDriver.set(driver);
    }

    /**
     * Cierra el WebDriver del hilo actual de forma segura y limpia su referencia en ThreadLocal.
     * Previene fugas de memoria y procesos de navegador huérfanos.
     */
    public static void quitDriver() {
        WebDriver driver = threadLocalDriver.get();
        if (driver != null) {
            try {
                driver.quit(); // Cierra todas las ventanas y finaliza la sesión
            } catch (Exception e) {
                System.err.println("Error al intentar cerrar el WebDriver del hilo actual: " + e.getMessage());
            } finally {
                threadLocalDriver.remove(); // Remueve la referencia para el hilo actual
            }
        }
    }
}
