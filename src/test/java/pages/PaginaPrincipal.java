package pages;

import utils.ConfigReader;

/**
 * Modelado de la Página Principal (Landing Page) de Free Range Testers.
 * Hereda de BasePage y define localizadores y métodos interactivos específicos de esta página.
 * 
 * @author QA Automation Senior
 */
public class PaginaPrincipal extends BasePage {

    // Locator XPath con marcador de posición (%s) para navegar dinámicamente en el menú superior
    private final String sectionLink = "//a[normalize-space()='%s' and @href]";
    
    // Locator XPath directo para el botón de elección de planes
    private final String elegirUnPlanButton = "//a[normalize-space()='Elegir Plan' and @href]";

    /**
     * Constructor por defecto.
     * Invoca al constructor de BasePage para inicializar el WebDriver del hilo correspondiente.
     */
    public PaginaPrincipal() {
        super();
    }

    /**
     * Navega a la página de Free Range Testers utilizando la URL cargada
     * desde el archivo centralizado config.properties.
     */
    public void navigateToFreeRangeTesters() {
        // Obtenemos la URL base configurada globalmente
        String baseUrl = ConfigReader.getProperty("baseUrl");
        if (baseUrl == null) {
            baseUrl = "https://www.freerangetesters.com"; // Respaldo si no está definida
        }
        navigateTo(baseUrl);
    }

    /**
     * Hace clic en una sección específica de la barra de navegación.
     * Construye dinámicamente el XPath reemplazando el marcador con el nombre de la sección.
     * 
     * @param section Nombre visible del enlace de la barra de navegación (ej: "Academia", "Cursos").
     */
    public void clickOnSectionNavigationBar(String section) {
        // Reemplaza el marcador %s con el texto exacto de la sección a pulsar
        String xpathSection = String.format(sectionLink, section);
        clickElement(xpathSection);
    }

    /**
     * Hace clic en el botón 'Elegir Plan' en la página de inicio.
     */
    public void clickOnElegirPlanButton() {
        clickElement(elegirUnPlanButton);
    }
}
