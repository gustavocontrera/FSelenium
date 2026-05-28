package pages;

import java.util.List;

/**
 * Modelado de la Página de Registro / Checkout de planes en Free Range Testers.
 * Proporciona métodos para extraer y validar elementos dentro del formulario de registro.
 * 
 * @author QA Automation Senior
 */
public class PaginaRegistro extends BasePage {

    // Localizador XPath del menú select/desplegable para la selección del plan de suscripción
    private final String planDropdown = "//select[@id='cart_cart_item_attributes_plan_with_interval']";

    /**
     * Constructor por defecto.
     * Vincula el WebDriver e inicializa las esperas explícitas heredadas de BasePage.
     */
    public PaginaRegistro() {
        super();
    }

    /**
     * Obtiene todos los valores en formato texto que se muestran dentro del desplegable de planes.
     * 
     * @return Lista de cadenas (List de String) con las opciones disponibles del plan (Academia, Free, etc.).
     */
    public List<String> returnPlanDropdownValues() {
        return getDropdownValues(planDropdown);
    }
}
