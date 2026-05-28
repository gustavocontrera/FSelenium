package steps;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.PaginaCursos;
import pages.PaginaPrincipal;
import pages.PaginaRegistro;

import java.util.Arrays;
import java.util.List;

/**
 * Definición de Pasos (Step Definitions) para los escenarios de navegación.
 * Conecta los pasos descritos en Gherkin (.feature) con las acciones definidas en los Page Objects.
 * 
 * @author QA Automation Senior
 */
public class FreeRangeNavega {

    // Inicialización de los objetos de página (Page Objects)
    // Se instancian al crearse la clase y asocian automáticamente el driver del hilo activo.
    private final PaginaPrincipal landingPage = new PaginaPrincipal();
    private final PaginaCursos cursosPage = new PaginaCursos();
    private final PaginaRegistro registro = new PaginaRegistro();

    /**
     * Paso de precondición: Navega a la página de inicio de Free Range Testers.
     */
    @Given("I navigate to www.freerangetesters.com")
    public void iNavigateToFRT() {
        landingPage.navigateToFreeRangeTesters();
    }

    /**
     * Paso de acción: Navega a una sección del menú superior utilizando la barra de navegación.
     * 
     * @param section Nombre de la sección (ej: "Academia", "Cursos").
     */
    @When("I go to {word} using the navigation bar")
    public void navigationBarUse(String section) {
        landingPage.clickOnSectionNavigationBar(section);
    }

    /**
     * Paso de acción: Hace clic en la opción "Elegir Plan".
     * Soporta múltiples pronombres o artículos en Gherkin (I, The user, The client).
     */
    @When("^(?:I|The user|The client) selects? Elegir Plan$")
    public void selectElegirPlan() {
        landingPage.clickOnElegirPlanButton();
    }

    /**
     * Paso de acción: Hace clic en "Introducción al Testing".
     */
    @And("^(?:I|The user|The client) selects? Introducción al Testing$")
    public void navigateToIntro() {
        cursosPage.clickIntroduccionTestingLink();
    }

    /**
     * Paso de verificación: Valida que los planes y precios mostrados en el checkout sean los esperados.
     * Utiliza Hard Assertions para asegurar que si el checkout difiere de lo esperado, el test falle inmediatamente.
     */
    @Then("^(?:I|The user|The client) can validate the options in the checkout page$")
    public void validateCheckoutPlans() {
        // Extrae los valores actuales del dropdown en la página de registro
        List<String> lista = registro.returnPlanDropdownValues();
        
        // Define la lista de valores esperados
        List<String> listaEsperada = Arrays.asList(
            "Academia: $16.99 / mes • 14 productos",
            "Academia: $176 / año • 14 productos", 
            "Free: Gratis • 2 productos"
        );

        // Realiza la aserción comparando ambas listas
        Assert.assertEquals(lista, listaEsperada, "Las opciones del checkout no coinciden con los planes esperados.");
    }
}
