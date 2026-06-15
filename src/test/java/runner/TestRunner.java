package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import java.io.IOException;

/**
 * Ejecutor de pruebas (Runner) para Cucumber BDD utilizando JUnit 4.
 * Mapea los archivos de características (features) con sus respectivas definiciones de pasos (glue).
 * NOTA DE ARQUITECTURA: Ya no es necesario un método @AfterClass para cerrar el navegador 
 * de forma global, dado que cada escenario gestiona su ciclo de vida y limpieza de manera 
 * aislada y segura en la clase Hooks (@Before y @After).
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
        tags = "@Courses"
)
public class TestRunner {
    // Clase controladora para ejecutar los tests desde IDE/Maven

    @AfterClass
    public static void generateReport() {
        try {
            System.out.println("Generando reporte HTML de Allure...");
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "npx -y allure-commandline generate test-output/allure-results --clean -o test-output/allure-report");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            process.waitFor();
            System.out.println("Reporte de Allure generado con éxito en la carpeta 'test-output/allure-report'.");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al generar el reporte de Allure: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
