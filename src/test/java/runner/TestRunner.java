package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Ejecutor de pruebas (Runner) para Cucumber BDD utilizando JUnit 4.
 * Mapea los archivos de características (features) con sus respectivas definiciones de pasos (glue).
 * 
 * NOTA DE ARQUITECTURA: Ya no es necesario un método @AfterClass para cerrar el navegador 
 * de forma global, dado que cada escenario gestiona su ciclo de vida y limpieza de manera 
 * aislada y segura en la clase Hooks (@Before y @After).
 * 
 * Se ha integrado Allure Cucumber para generar reportes dinámicos de ejecución de forma automática.
 * 
 * @author QA Automation Senior
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        // Ruta de acceso a los archivos de características (features)
        features = "src/test/resources/features",
        // Paquete que contiene las clases de definición de pasos y hooks
        glue = "steps",
        // Reportes y consolas legibles, incluyendo el plugin de Allure
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty.html",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" // Genera reportes para Allure
        },
        // Etiquetas (tags) a ejecutar por defecto
        tags = "@Navigation"
)
public class TestRunner {
    // Clase controladora vacía para ejecutar los tests desde IDE/Maven
}
