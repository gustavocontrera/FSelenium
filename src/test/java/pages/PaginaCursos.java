package pages;

/**
 * Modelado de la Página de Cursos de Free Range Testers.
 * Define las interacciones disponibles dentro de la sección de cursos.
 * 
 * @author QA Automation Senior
 */
public class PaginaCursos extends BasePage {

    // XPath para localizar el enlace específico del curso "Introducción al Testing de Software"
    private final String introduccionTestingLink = "//a[normalize-space()='Introducción al Testing de Software' and @href]";

    /**
     * Constructor por defecto.
     * Vincula el WebDriver e inicializa las esperas explícitas heredadas de BasePage.
     */
    public PaginaCursos() {
        super();
    }

    /**
     * Hace clic en el enlace para ingresar al curso de Introducción al Testing de Software.
     */
    public void clickIntroduccionTestingLink() {
        clickElement(introduccionTestingLink);
    }
}
