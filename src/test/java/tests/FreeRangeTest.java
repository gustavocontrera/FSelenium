package tests;

import org.testng.annotations.Test;
import pages.PaginaPrincipal;

/**
 * Ejemplo de caso de prueba puro utilizando TestNG (sin Cucumber).
 * Hereda de BaseTest para gestionar de manera transparente el ciclo de vida del navegador.
 * Implementa el patrón Page Object Model interactuando a través de las páginas.
 * 
 * @author QA Automation Senior
 */
public class FreeRangeTest extends BaseTest {

    /**
     * Test de ejemplo para validar que el framework híbrido puede ejecutar 
     * flujos sin Cucumber. Interactúa con los Page Objects definidos de forma estándar.
     */
    @Test(description = "Validar la navegación a la página principal utilizando TestNG directo y Page Objects")
    public void testNavegacionPaginaPrincipal() {
        // Instanciamos el Page Object para interactuar con la página de inicio.
        // Al instanciarse, toma automáticamente la conexión del WebDriver activo del hilo.
        PaginaPrincipal principal = new PaginaPrincipal();
        
        System.out.println("Ejecutando caso de prueba puro TestNG...");
        
        // Ejecutamos la acción de navegación
        principal.navigateToFreeRangeTesters();
        
        // Opcional: Se podrían añadir aserciones directas aquí usando los elementos expuestos por la página
    }
}
