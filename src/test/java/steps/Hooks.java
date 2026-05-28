package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverManager;

/**
 * Controla el ciclo de vida de ejecución de cada escenario de Cucumber BDD.
 * Se encarga de inicializar y cerrar el navegador de manera segura para cada prueba.
 * 
 * @author QA Automation Senior
 */
public class Hooks {

    /**
     * Hook que se ejecuta ANTES de cada escenario de Cucumber.
     * Llama a DriverManager para inicializar el WebDriver del hilo actual de forma automática.
     * 
     * @param scenario Metadatos del escenario actual.
     */
    @Before
    public void setUp(Scenario scenario) {
        System.out.println("--- [BDD INICIO] Iniciando escenario: " + scenario.getName() + " ---");
        // Fuerza la inicialización del driver
        DriverManager.getDriver();
    }

    /**
     * Hook que se ejecuta DESPUÉS de cada escenario de Cucumber.
     * Si la prueba falla, captura una imagen de pantalla y la adjunta al reporte HTML de Cucumber.
     * Posteriormente, cierra de forma segura el navegador en este hilo de ejecución.
     * 
     * @param scenario Metadatos del escenario que finaliza.
     */
    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            try {
                // Validamos si el escenario actual falló
                if (scenario.isFailed()) {
                    scenario.log("Escenario fallido. Tomando captura de pantalla para el reporte...");
                    // Captura la pantalla en formato de arreglo de bytes
                    final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    // Adjunta la captura al reporte HTML de Cucumber
                    scenario.attach(screenshot, "image/png", "Captura de Pantalla - Error");
                }
            } catch (Exception e) {
                System.err.println("ERROR: No se pudo realizar la captura de pantalla: " + e.getMessage());
            } finally {
                System.out.println("--- [BDD FIN] Finalizando escenario: " + scenario.getName() + " ---");
                // Cierre absoluto y limpieza de ThreadLocal para este hilo
                DriverManager.quitDriver();
            }
        }
    }
}
