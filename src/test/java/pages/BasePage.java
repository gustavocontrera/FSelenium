package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.DriverManager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase base para el patrón Page Object Model (POM).
 * Encapsula la interacción directa con Selenium y ofrece esperas explícitas preconfiguradas.
 * Admite localizadores de tipo By y tipo String (por defecto interpretados como XPath).
 * 
 * @author QA Automation Senior
 */
public class BasePage {

    // Instancia local de WebDriver compartida para las subclases
    protected WebDriver driver;

    // Instancia de WebDriverWait para esperas explícitas
    protected WebDriverWait wait;

    /**
     * Constructor por defecto de BasePage.
     * Recupera automáticamente la instancia de WebDriver correspondiente al hilo actual
     * e inicializa el objeto WebDriverWait utilizando el timeout del archivo config.properties.
     */
    public BasePage() {
        this.driver = DriverManager.getDriver();
        
        // Lee el timeout de config.properties, por defecto usa 10 segundos
        String timeoutString = ConfigReader.getProperty("timeout");
        int timeout = (timeoutString != null) ? Integer.parseInt(timeoutString) : 10;
        
        this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeout));
    }

    /**
     * Constructor de compatibilidad que acepta un driver de forma externa.
     * Aunque DriverManager gestiona los drivers de forma centralizada, este constructor
     * mantiene compatibilidad con código antiguo que inyecte un driver.
     * 
     * @param driver Instancia de WebDriver
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        String timeoutString = ConfigReader.getProperty("timeout");
        int timeout = (timeoutString != null) ? Integer.parseInt(timeoutString) : 10;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    /**
     * Navega a una URL especificada en el navegador activo del hilo actual.
     * 
     * @param url URL de destino (ej: https://www.google.com)
     */
    public static void navigateTo(String url) {
        DriverManager.getDriver().get(url);
    }

    /**
     * Cierra de forma segura el navegador del hilo actual llamando al DriverManager.
     */
    public static void closeBrowser() {
        DriverManager.quitDriver();
    }

    /**
     * Encuentra y devuelve un WebElement en el DOM esperando a que esté presente.
     * 
     * @param locator Localizador By del elemento web (id, xpath, cssSelector, etc.)
     * @return El WebElement encontrado y listo para interactuar.
     */
    private WebElement Find(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Sobrecarga de Find para soportar localizadores XPath tradicionales en formato String.
     * 
     * @param xpath Localizador XPath en texto.
     * @return El WebElement encontrado.
     */
    private WebElement Find(String xpath) {
        return Find(By.xpath(xpath));
    }

    /**
     * Hace clic en un elemento web identificado por un objeto By.
     * 
     * @param locator Localizador By del elemento a cliquear.
     */
    public void clickElement(By locator) {
        Find(locator).click();
    }

    /**
     * Hace clic en un elemento web identificado por un XPath en formato String.
     * 
     * @param xpath Localizador XPath en texto.
     */
    public void clickElement(String xpath) {
        Find(xpath).click();
    }

    /**
     * Limpia un campo de texto y escribe un valor utilizando un localizador By.
     * 
     * @param locator Localizador By de la caja de texto.
     * @param keysToSend El texto que se ingresará en el elemento.
     */
    public void write(By locator, String keysToSend) {
        WebElement element = Find(locator);
        element.clear();
        element.sendKeys(keysToSend);
    }

    /**
     * Limpia un campo de texto y escribe un valor utilizando un XPath en formato String.
     * 
     * @param xpath Localizador XPath de la caja de texto.
     * @param keysToSend El texto que se ingresará en el elemento.
     */
    public void write(String xpath, String keysToSend) {
        WebElement element = Find(xpath);
        element.clear();
        element.sendKeys(keysToSend);
    }

    /**
     * Selecciona una opción de un menú desplegable (Select) usando el atributo 'value'.
     * 
     * @param locator Localizador By del menú desplegable.
     * @param value El valor del atributo 'value' de la opción a seleccionar.
     */
    public void selectFromDropdownByValue(By locator, String value) {
        Select dropdown = new Select(Find(locator));
        dropdown.selectByValue(value);
    }

    /**
     * Selecciona una opción de un menú desplegable (Select) usando el atributo 'value' y un XPath String.
     * 
     * @param xpath Localizador XPath de la caja select.
     * @param value El valor del atributo 'value' a seleccionar.
     */
    public void selectFromDropdownByValue(String xpath, String value) {
        Select dropdown = new Select(Find(xpath));
        dropdown.selectByValue(value);
    }

    /**
     * Selecciona una opción de un menú desplegable (Select) a través de su índice de posición (0-indexed).
     * 
     * @param locator Localizador By del select.
     * @param index Índice de la opción a seleccionar.
     */
    public void selectFromDropdownByIndex(By locator, Integer index) {
        Select dropdown = new Select(Find(locator));
        dropdown.selectByIndex(index);
    }

    /**
     * Selecciona una opción de un menú desplegable (Select) a través de su índice y usando un XPath String.
     * 
     * @param xpath Localizador XPath del select.
     * @param index Índice de la opción a seleccionar.
     */
    public void selectFromDropdownByIndex(String xpath, Integer index) {
        Select dropdown = new Select(Find(xpath));
        dropdown.selectByIndex(index);
    }

    /**
     * Obtiene la cantidad total de opciones disponibles en un menú desplegable.
     * 
     * @param locator Localizador By del menú desplegable.
     * @return El número entero total de opciones encontradas.
     */
    public int dropdownSize(By locator) {
        Select dropdown = new Select(Find(locator));
        return dropdown.getOptions().size();
    }

    /**
     * Obtiene la cantidad total de opciones disponibles en un menú desplegable usando un XPath String.
     * 
     * @param xpath Localizador XPath del select.
     * @return El número entero total de opciones encontradas.
     */
    public int dropdownSize(String xpath) {
        Select dropdown = new Select(Find(xpath));
        return dropdown.getOptions().size();
    }

    /**
     * Obtiene el texto visible de todas las opciones presentes dentro de un menú desplegable.
     * 
     * @param locator Localizador By del dropdown.
     * @return Lista de Strings con los textos visibles de las opciones.
     */
    public List<String> getDropdownValues(By locator) {
        Select dropdown = new Select(Find(locator));
        List<WebElement> dropdownOptions = dropdown.getOptions();
        List<String> values = new ArrayList<>();
        for (WebElement option : dropdownOptions) {
            values.add(option.getText());
        }
        return values;
    }

    /**
     * Obtiene el texto visible de todas las opciones de un menú desplegable usando un XPath String.
     * 
     * @param xpath Localizador XPath del dropdown.
     * @return Lista de Strings con los textos de las opciones.
     */
    public List<String> getDropdownValues(String xpath) {
        Select dropdown = new Select(Find(xpath));
        List<WebElement> dropdownOptions = dropdown.getOptions();
        List<String> values = new ArrayList<>();
        for (WebElement option : dropdownOptions) {
            values.add(option.getText());
        }
        return values;
    }
}
