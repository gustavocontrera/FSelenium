package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.DriverManager;

/**
 * Clase base para la ejecución de pruebas puras con TestNG (sin Cucumber).
 * Gestiona el ciclo de vida del WebDriver para cada caso de prueba individual.
 * 
 * @author QA Automation Senior
 */
public class BaseTest {

    /**
     * Configuración previa a la ejecución de cada método de prueba.
     * Invoca DriverManager para asegurar que el navegador adecuado se levante e inicialice.
     */
    @BeforeMethod
    public void setUp() {
        // Inicializa el WebDriver correspondiente para este hilo de ejecución
        DriverManager.getDriver();
    }

    /**
     * Limpieza posterior a la ejecución de cada método de prueba.
     * Cierra de forma segura el navegador del hilo activo y destruye la referencia de ThreadLocal.
     */
    @AfterMethod
    public void tearDown() {
        // Cierra el navegador y limpia la instancia de WebDriver en este hilo
        DriverManager.quitDriver();
    }

    /**
     * Helper para retornar el WebDriver activo en el hilo actual si una prueba hija
     * necesita interactuar directamente con la API nativa de Selenium.
     * 
     * @return El WebDriver activo.
     */
    protected WebDriver getDriver() {
        return DriverManager.getDriver();
    }
}
