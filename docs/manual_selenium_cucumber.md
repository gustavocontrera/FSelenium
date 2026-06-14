# Guía Definitiva y Manual de Automatización de Pruebas: Selenium, Java y Cucumber

Este manual es una guía de referencia de nivel senior para el diseño, implementación y mantenimiento de suites de automatización. Combina la teoría detrás de cada tecnología con ejemplos de código completos y estructurados basados en los módulos didácticos y patrones de producción que se encuentran distribuidos a lo largo del directorio `src/test/java/varios` del framework.

---

## Índice
1. [Arquitectura de una Solución BDD](#1-arquitectura-de-una-solución-bdd)
2. [Selenium WebDriver API Deep Dive](#2-selenium-webdriver-api-deep-dive)
   - [Localizadores y Buenas Prácticas con XPath y CSS](#localizadores-y-buenas-prácticas-con-xpath-y-css)
   - [El superpoder de `normalize-space()`](#el-superpoder-de-normalize-space)
   - [Interacción con Elementos y Menús Desplegables](#interacción-con-elementos-y-menús-desplegables)
   - [Acciones Avanzadas del Mouse y Teclado (`Actions`)](#acciones-avanzadas-del-mouse-y-teclado-actions)
   - [Manejo de Múltiples Pestañas y Ventanas](#manejo-de-múltiples-pestañas-y-ventanas)
   - [Manejo de Marcos (iFrames)](#manejo-de-marcos-iframes)
   - [Interacción con Alertas Nativas del Navegador](#interacción-con-alertas-nativas-del-navegador)
   - [Ejecución de Código JavaScript (`JavascriptExecutor`)](#ejecución-de-código-javascript-javascriptexecutor)
3. [TestNG en Automatización Profesional](#3-testng-en-automatización-profesional)
   - [Aserciones Fuertes y Suaves (`Hard` y `Soft` Asserts)](#aserciones-fuertes-y-suaves-hard-y-soft-asserts)
   - [Anotaciones de Ciclo de Vida y Flujo de Ejecución](#anotaciones-de-ciclo-de-vida-y-flujo-de-ejecución)
   - [Pruebas Parametrizadas con `@DataProvider`](#pruebas-parametrizadas-con-dataprovider)
   - [Generación Dinámica de Tests con `@Factory`](#generación-dinámica-de-tests-con-factory)
   - [Monitoreo e Intercepción mediante Listeners (`ITestListener`)](#monitoreo-e-intercepción-mediante-listeners-itestlistener)
4. [Gestión de Datos Externos y Datos de Prueba Moficados](#4-gestión-de-datos-externos-y-datos-de-prueba-moficados)
   - [Lectura de Archivos CSV con OpenCSV](#lectura-de-archivos-csv-con-opencsv)
   - [Lectura de Hojas de Excel (.xlsx) con Apache POI](#lectura-de-hojas-de-excel-xlsx-con-apache-poi)
   - [Generación de Datos Aleatorios Realistas con JavaFaker](#generación-de-datos-aleatorios-realistas-con-javafaker)
5. [Cucumber BDD en Detalle](#5-cucumber-bdd-en-detalle)
   - [Escribir Gherkin Profesional](#escribir-gherkin-profesional)
   - [Mapeo e Inyección de Variables (Cucumber Expressions & Regex)](#mapeo-e-inyección-de-variables-cucumber-expressions-regex)
   - [Manejo de Tablas de Datos (`DataTables`)](#manejo-de-tablas-de-datos-datatables)
   - [Ciclos de Vida de Cucumber (Hooks) y Reporte de Pantallazos en Fallos](#ciclos-de-vida-de-cucumber-hooks-y-reporte-de-pantallazos-en-fallos)
6. [Design Patterns: Page Object Model (POM) vs Page Factory](#6-design-patterns-page-object-model-pom-vs-page-factory)
   - [Aproximación Clásica POM (con Localizadores Dinámicos)](#aproximación-clásica-pom-con-localizadores-dinámicos)
   - [Aproximación con Page Factory (`@FindBy`)](#aproximación-con-page-factory-findby)
   - [Cuándo usar cada patrón](#cuándo-usar-cada-patrón)

---

## 1. Arquitectura de una Solución BDD

Una automatización robusta con Cucumber y Selenium divide las responsabilidades en capas independientes. Esto asegura que si el diseño de la aplicación cambia, el impacto se aísla principalmente en la capa de páginas, dejando intactos los escenarios de negocio.

```
┌─────────────────────────────────────────────────────────────┐
│ 1. Capa de Negocio: Gherkin (.feature)                      │
│    (Define QUÉ hace la aplicación usando Given/When/Then)   │
└──────────────┬──────────────────────────────────────────────┘
               │ (Mapeo por Expresiones)
               ▼
┌─────────────────────────────────────────────────────────────┐
│ 2. Capa de Pasos: Step Definitions (.java)                  │
│    (Orquesta las llamadas y ejecuta las Validaciones/Asserts)│
└──────────────┬──────────────────────────────────────────────┘
               │ (Instanciación / Invocación de Métodos)
               ▼
┌─────────────────────────────────────────────────────────────┐
│ 3. Capa de Páginas: Page Objects (.java)                    │
│    (Encapsula selectores HTML y las acciones de Selenium)   │
└──────────────┬──────────────────────────────────────────────┘
               │ (Hereda funcionalidades)
               ▼
┌─────────────────────────────────────────────────────────────┐
│ 4. Capa Base: Base Page / Drivers (.java)                  │
│    (Centraliza esperas explícitas y control del WebDriver)  │
└─────────────────────────────────────────────────────────────┘
```

---

## 2. Selenium WebDriver API Deep Dive

### Localizadores y Buenas Prácticas con XPath y CSS
Encontrar el elemento correcto en el DOM de forma única es crucial para evitar excepciones de tipo `NoSuchElementException`.

*   **CSS Selectors:** Son más rápidos en la renderización y más legibles para los desarrolladores.
    ```java
    By.cssSelector("#login-btn");                  // Por ID
    By.cssSelector(".submit-form.active");         // Por clases compuestas
    By.cssSelector("input[name='email']");         // Por atributos
    By.cssSelector("div.container > ul > li:first-child"); // Navegación descendente directa
    ```
*   **XPath:** Es el único motor que permite navegación bidireccional (ir al elemento padre o hermano) y buscar elementos a partir de su texto.
    ```java
    By.xpath("//button[contains(@class,'btn') and text()='Guardar']"); // Condición compuesta
    By.xpath("//input[@id='username']/parent::div");                // Buscar el nodo padre
    By.xpath("//label[text()='Edad']/following-sibling::input");     // Buscar elemento hermano
    ```

### El superpoder de `normalize-space()`
Muchos sitios web modernos inyectan espacios extras o saltos de línea dentro del código HTML al estructurarlo. Si usas `text()='Texto'` el localizador fallará si hay un espacio de más. `normalize-space()` limpia los espacios en blanco delanteros y traseros, y simplifica los espacios internos duplicados a uno solo.

**Ejemplo práctico:**
Si el HTML es: `<a href="/signup">   Registrarte  gratis  </a>`
```java
// FALLARÁ por causa de las sangrías y espacios:
By.xpath("//a[text()='Registrarte gratis']"); 

// FUNCIONARÁ correctamente gracias a la limpieza del string:
By.xpath("//a[normalize-space()='Registrarte gratis']"); 
```

### Interacción con Elementos y Menús Desplegables
Para interactuar con elementos básicos de formulario y dropdowns estándar `<select>`:

```java
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

WebElement inputName = driver.findElement(By.id("first-name"));
inputName.clear();               // Buena práctica: Limpiar antes de escribir
inputName.sendKeys("Alejandro");

// Manejo de Dropdown Nativo (<select>)
WebElement dropdown = driver.findElement(By.id("country-selector"));
Select selectCountry = new Select(dropdown);
selectCountry.selectByValue("AR");              // Atributo value
selectCountry.selectByVisibleText("Argentina"); // Texto visual del usuario
selectCountry.selectByIndex(1);                 // Indice base 0
```

### Acciones Avanzadas del Mouse y Teclado (`Actions`)
Para eventos de mouse avanzados que no se pueden simular con un `.click()` o `.sendKeys()` plano.

```java
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;

Actions builder = new Actions(driver);

// 1. Mouse Hover (posicionar el puntero sobre un elemento)
WebElement subMenu = driver.findElement(By.id("category-menu"));
builder.moveToElement(subMenu).build().perform();

// 2. Drag and Drop (arrastrar y soltar)
WebElement source = driver.findElement(By.id("draggable-box"));
WebElement target = driver.findElement(By.id("dropzone"));
builder.dragAndDrop(source, target).build().perform();

// 3. Double Click y Clic Derecho (Context Click)
WebElement btn = driver.findElement(By.id("double-click-area"));
builder.doubleClick(btn).contextClick(btn).build().perform();

// 4. Escribir en Mayúsculas manteniendo apretada la tecla SHIFT
WebElement searchField = driver.findElement(By.name("q"));
builder.moveToElement(searchField)
       .click()
       .keyDown(Keys.SHIFT)
       .sendKeys("búsqueda en mayúsculas")
       .keyUp(Keys.SHIFT)
       .build().perform();
```

### Manejo de Múltiples Pestañas y Ventanas
Cada pestaña o ventana que abre el navegador tiene un identificador alfanumérico único asignado por el WebDriver llamado `Window Handle`.

```java
// Obtener el identificador de la pestaña actual
String originalTab = driver.getWindowHandle();

// Ejecutar una acción que abra una nueva pestaña...
driver.findElement(By.linkText("Políticas de Privacidad")).click();

// Obtener la colección de todas las pestañas abiertas
Set<String> allTabs = driver.getWindowHandles();

// Iterar y cambiar el control de Selenium al nuevo tabulador
for (String tabId : allTabs) {
    if (!tabId.equals(originalTab)) {
        driver.switchTo().window(tabId); // Transferencia de control
        break;
    }
}

System.out.println("URL de la nueva pestaña: " + driver.getCurrentUrl());
driver.close(); // Cierra únicamente la pestaña activa

// Volver al flujo y control de la pestaña original
driver.switchTo().window(originalTab);
```

### Manejo de Marcos (iFrames)
Un iFrame es un documento HTML incrustado dentro de otro documento HTML. Selenium no puede ver ni interactuar con los elementos que están dentro de un iFrame a menos que se cambie explícitamente a ese contexto.

```java
// Cambiar al iFrame usando su ID o Nombre del elemento HTML
driver.switchTo().frame("frame-publicitario-1");

// O encontrarlo primero como un WebElement
WebElement iframeElement = driver.findElement(By.cssSelector("iframe.clase-frame"));
driver.switchTo().frame(iframeElement);

// Ahora podemos interactuar con los elementos internos
driver.findElement(By.id("boton-cerrar-publicidad")).click();

// CRÍTICO: Regresar al cuerpo principal del documento HTML
driver.switchTo().defaultContent();
```

### Interacción con Alertas Nativas del Navegador
Las alertas JavaScript nativas (como `alert()`, `confirm()` o `prompt()`) no forman parte del HTML tradicional y requieren una gestión a nivel de sistema por parte del driver.

```java
// Esperar e interactuar con la alerta
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
wait.until(ExpectedConditions.alertIsPresent());

// Mapear la alerta
org.openqa.selenium.Alert alert = driver.switchTo().alert();

System.out.println("Texto de la alerta: " + alert.getText());

alert.accept();  // Hace clic en el botón "Aceptar"
// alert.dismiss(); // Hace clic en el botón "Cancelar"
// alert.sendKeys("Texto"); // Escribe en alertas que solicitan texto (Prompt)
```

### Ejecución de Código JavaScript (`JavascriptExecutor`)
Útil cuando Selenium no logra disparar eventos nativos por interferencias de diseño o para realizar operaciones de scroll avanzadas.

```java
import org.openqa.selenium.JavascriptExecutor;

JavascriptExecutor js = (JavascriptExecutor) driver;

// Realizar scroll hacia abajo hasta que un elemento sea visible
WebElement targetElement = driver.findElement(By.id("terminos-condiciones"));
js.executeScript("arguments[0].scrollIntoView(true);", targetElement);

// Hacer clic forzado (ignora capas superpuestas u overlays)
WebElement hiddenBtn = driver.findElement(By.cssSelector(".btn-checkout"));
js.executeScript("arguments[0].click();", hiddenBtn);

// Retornar un valor desde el navegador
String pageTitle = (String) js.executeScript("return document.title;");
```

---

## 3. TestNG en Automatización Profesional

TestNG es un framework de pruebas avanzado que provee control total sobre la inicialización, ejecución y verificación de los tests.

### Aserciones Fuertes y Suaves (`Hard` y `Soft` Asserts)

*   **Hard Assert (`org.testng.Assert`):** Detiene la prueba de inmediato si falla.
    ```java
    Assert.assertEquals(resultadoActual, "Exitoso", "Mensaje en caso de falla");
    ```
*   **Soft Assert (`org.testng.asserts.SoftAssert`):** Almacena los errores internamente y continúa la ejecución. Se debe invocar `.assertAll()` para evaluar las aserciones acumuladas al final de la prueba.
    ```java
    SoftAssert soft = new SoftAssert();
    soft.assertTrue(driver.getCurrentUrl().contains("dashboard"), "Error de URL");
    soft.assertEquals(driver.getTitle(), "Mi Dashboard", "Error de Título");
    // El test no fallará en las líneas anteriores. Fallará exactamente aquí:
    soft.assertAll();
    ```

### Anotaciones de Ciclo de Vida y Flujo de Ejecución
TestNG controla el orden de ejecución de los métodos de prueba y configuraciones de soporte mediante anotaciones:

```
@BeforeSuite     -> Se ejecuta una vez antes de todas las pruebas del Suite XML.
  @BeforeTest    -> Se ejecuta antes de cualquier clase de prueba listada dentro de la etiqueta <test>.
    @BeforeClass -> Se ejecuta una vez antes de que empiece el primer método de test de la clase actual.
      @BeforeMethod -> Se ejecuta antes de CADA método de prueba (@Test).
        @Test        -> El caso de prueba en ejecución.
      @AfterMethod  -> Se ejecuta después de CADA método de prueba (@Test).
    @AfterClass  -> Se ejecuta una vez después de que finalicen todos los métodos de test de la clase.
  @AfterTest     -> Se ejecuta después de que finalicen todas las clases de prueba.
@AfterSuite      -> Se ejecuta una vez después de finalizadas todas las pruebas del suite.
```

### Pruebas Parametrizadas con `@DataProvider`
Permite inyectar múltiples conjuntos de datos en un único método de prueba de manera dinámica. Esto es ideal para probar formularios con múltiples combinaciones de datos (correctos, incorrectos, límites, etc.).

```java
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TestDataDriven {

    // 1. Definición del DataProvider
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return new Object[][] {
            {"usuario_valido@test.com", "Password123", true},
            {"usuario_invalido@test.com", "WrongPassword", false},
            {"", "Password123", false}
        };
    }

    // 2. Vinculación del test con el DataProvider
    @Test(dataProvider = "loginData")
    public void testLoginForm(String email, String password, boolean expectedResult) {
        System.out.println("Probando ingreso de: " + email);
        loginPage.login(email, password);
        Assert.assertEquals(loginPage.isLoginSuccessful(), expectedResult);
    }
}
```

### Generación Dinámica de Tests con `@Factory`
A diferencia de `@DataProvider` (que ejecuta el mismo método de test muchas veces con diferentes argumentos), la anotación `@Factory` permite crear múltiples instancias de una clase de pruebas completa a tiempo de ejecución, pasándole diferentes parámetros al constructor de la clase. Es útil para ejecutar una suite completa en diferentes navegadores.

```java
// 1. Clase de prueba parametrizada desde el constructor
public class MultiBrowserTest {
    private String browser;

    public MultiBrowserTest(String browser) {
        this.browser = browser;
    }

    @Test
    public void executeScenario() {
        System.out.println("Ejecutando pruebas en: " + browser);
        // Inicializar controlador para el navegador 'browser' y ejecutar lógica de test
    }
}

// 2. Clase Factory que genera dinámicamente las ejecuciones
import org.testng.annotations.Factory;

public class TestFactory {
    @Factory
    public Object[] generateInstances() {
        return new Object[] {
            new MultiBrowserTest("Chrome"),
            new MultiBrowserTest("Firefox"),
            new MultiBrowserTest("Edge")
        };
    }
}
```

### Monitoreo e Intercepción mediante Listeners (`ITestListener`)
Los Listeners permiten interceptar eventos de la prueba (inicio, éxito, falla, skip) para disparar acciones adicionales automáticamente, como capturar pantallas en caso de fallos.

```java
package utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        System.out.println(">>> Suite Iniciada: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(">>> Iniciando Test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(">>> Test Exitoso: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.err.println(">>> Test FALLIDO: " + result.getName());
        // Aquí puedes obtener el driver del contexto y tomar una captura de pantalla
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println(">>> Suite Finalizada: " + context.getName());
    }
}
```
*Para registrar el Listener, puedes usar la anotación `@Listeners(utils.TestListener.class)` sobre tu clase de test, o declararlo en el archivo `testng.xml`:*
```xml
<listeners>
    <listener class-name="utils.TestListener" />
</listeners>
```

---

## 4. Gestión de Datos Externos y Datos de Prueba Moficados

### Lectura de Archivos CSV con OpenCSV
Es común almacenar contraseñas, URLs y nombres de usuario en archivos `.csv` para modularizar los datos de prueba y no escribirlos directamente en el código.

```java
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVHelper {
    public static List<String[]> readCSVData(String filePath) {
        List<String[]> records = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                records.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }
}
```

### Lectura de Hojas de Excel (.xlsx) con Apache POI
La librería Apache POI es muy utilizada en el ecosistema Java para leer archivos Excel complejos.

```java
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;

public class ExcelReader {
    public static Object[][] getExcelData(String filePath, String sheetName) {
        Object[][] data = null;
        try (FileInputStream file = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {
            
            XSSFSheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

            data = new Object[rowCount - 1][colCount]; // Excluyendo cabecera

            for (int i = 1; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    data[i - 1][j] = sheet.getRow(i).getCell(j).toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
```

### Generación de Datos Aleatorios Realistas con JavaFaker
Evita el uso de cadenas estáticas fijas en las pruebas de registro de usuarios (que fallan en la segunda ejecución porque los correos electrónicos o números de teléfono ya existen en la base de datos). JavaFaker genera datos realistas y dinámicos en cada ejecución.

```java
import com.github.javafaker.Faker;

public class FakerUsage {
    public static void main(String[] args) {
        Faker faker = new Faker();

        String email = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().fullAddress();
        String jobTitle = faker.job().title();

        System.out.println("Email: " + email);
        System.out.println("Nombre: " + firstName + " " + lastName);
        System.out.println("Dirección: " + address);
        System.out.println("Cargo: " + jobTitle);
    }
}
```

---

## 5. Cucumber BDD en Detalle

### Escribir Gherkin Profesional
Gherkin debe centrarse en el **comportamiento del negocio**, no en la implementación del software. Evita escribir pasos técnicos como "Hago clic en el botón de ID 'btn-save'" o "Escribo 'test@test.com' en el campo de texto".

*   **Mal ejemplo (Técnico e inestable):**
    ```gherkin
    Dado que abro Chrome en el sitio
    Cuando hago clic en la etiqueta input con id "username"
    Y ingreso "admin" en el input
    Y presiono el boton "Aceptar"
    Entonces la url debe contener "/home"
    ```
*   **Buen ejemplo (Declarativo de negocio):**
    ```gherkin
    Dado que el usuario accede al portal de inicio de sesión
    Cuando ingresa credenciales de administrador válidas
    Entonces es redirigido a la página de inicio
    ```

### Mapeo e Inyección de Variables (Cucumber Expressions & Regex)
Cucumber permite inyectar variables en los métodos utilizando expresiones.

```java
// Usando Cucumber Expressions (Legible)
@When("ingresa al curso {string} que cuesta {int} dólares")
public void ingresarCurso(String curso, int precio) {
    System.out.println("Curso: " + curso + " - Costo: " + precio);
}

// Usando Expresión Regular (Regex) para soportar variaciones lingüísticas
// Captura tanto "el cliente selecciona" como "un usuario selecciona"
@When("^(?:el cliente|un usuario) selecciona? (?:el plan|la opción) \"([^\"]*)\"$")
public void seleccionarOpcion(String opcion) {
    registroPage.seleccionarPlan(opcion);
}
```

### Manejo de Tablas de Datos (`DataTables`)
Cuando un paso necesita un conjunto de datos estructurado en filas y columnas, puedes pasar una tabla que Cucumber convertirá automáticamente a estructuras de Java (como `List`, `Map` o tipos personalizados).

```gherkin
Y el usuario ingresa la información de registro:
  | nombre   | apellido | pais      |
  | Carlos   | Gómez    | Argentina |
  | Ana      | Silva    | Chile     |
```
```java
import io.cucumber.datatable.DataTable;
import java.util.List;
import java.util.Map;

@When("el usuario ingresa la información de registro:")
public void ingresarInformacion(DataTable dataTable) {
    // Convierte la tabla a una lista de mapas (clave: cabecera, valor: celda)
    List<Map<String, String>> filas = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> fila : filas) {
        String nombre = fila.get("nombre");
        String apellido = fila.get("apellido");
        String pais = fila.get("pais");

        System.out.println("Registrando a: " + nombre + " " + apellido + " de " + pais);
    }
}
```

### Ciclos de Vida de Cucumber (Hooks) y Reporte de Pantallazos en Fallos
Los Hooks controlan los procesos antes y después de cada escenario. Se pueden usar etiquetas (`tags`) para ejecutar hooks específicos de forma condicional.

```java
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ScenarioHooks {

    @Before("@RequireDatabase")
    public void setupDatabase() {
        System.out.println("Inicializando conexión a la base de datos...");
    }

    @After
    public void cleanUpAndScreenshot(Scenario scenario) {
        if (scenario.isFailed()) {
            // Capturar pantalla de forma binaria
            final byte[] screenshot = ((TakesScreenshot) BasePage.driver).getScreenshotAs(OutputType.BYTES);
            // Incrustar el archivo adjunto en los reportes (HTML, Extent, etc.)
            scenario.attach(screenshot, "image/png", "Evidencia de Fallo");
        }
        System.out.println("Escenario finalizado. Estado: " + scenario.getStatus());
    }
}
```

---

## 6. Design Patterns: Page Object Model (POM) vs Page Factory

Existen dos formas populares de modelar páginas web en Selenium. Ambas buscan separar la lógica de la prueba de los elementos de la interfaz.

### Aproximación Clásica POM (con Localizadores Dinámicos)
En esta aproximación, la clase de página almacena los selectores como cadenas de texto simples (`String`) y resuelve la búsqueda dinámicamente llamando al driver a través de un método auxiliar de la clase base. 

Esto permite usar variables dinámicas en el XPath y simplifica el uso de esperas explícitas estructuradas.

```java
// 1. Clase Base
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement findElement(String xpath) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
    }

    public void click(String xpath) {
        findElement(xpath).click();
    }
}

// 2. Clase de Página
public class ProductPage extends BasePage {
    // Localizador dinámico parametrizado
    private String productCardXpath = "//div[@class='product-card'][descendant::h3[text()='%s']]//button";

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void addProductToCart(String productName) {
        String xpathFinal = String.format(productCardXpath, productName);
        click(xpathFinal); // Búsqueda y clic dinámicos con espera
    }
}
```

### Aproximación con Page Factory (`@FindBy`)
Este patrón utiliza anotaciones de Java y la inicialización de elementos a través de la clase `PageFactory`. El inicializador asocia los elementos dinámicamente mediante Proxies de Java, buscando los elementos en el DOM únicamente cuando se interactúa con ellos ("Lazy Loading").

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPageFactory {
    
    private WebDriver driver;

    // Localización estática mediante anotaciones
    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "input.btn_action")
    private WebElement loginButton;

    public LoginPageFactory(WebDriver driver) {
        this.driver = driver;
        // CRÍTICO: Inicializar los elementos decorados con la anotación @FindBy
        PageFactory.initElements(driver, this);
    }

    public void login(String user, String pass) {
        usernameInput.sendKeys(user);
        passwordInput.sendKeys(pass);
        loginButton.click();
    }
}
```

### Cuándo usar cada patrón

| Característica | POM Clásico (Strings / By) | Page Factory (`@FindBy`) |
| :--- | :--- | :--- |
| **Localizadores Dinámicos** | **Soportado nativamente** utilizando `String.format()` para modificar el XPath dinámicamente antes de buscarlo. | **Complejo**. Las anotaciones `@FindBy` requieren valores constantes definidos en tiempo de compilación. |
| **Inicialización de elementos** | Bajo demanda (se buscan cuando se llama a un método). | Todos los elementos declarados se configuran e inicializan en bloque a través de `initElements()`. |
| **Control de Esperas** | Sencillo y consistente. Permite aplicar esperas explícitas antes de retornar el elemento. | Más complejo de sincronizar. Se requiere configurar la carga retardada con `AjaxElementLocatorFactory` o usar esperas manuales sobre las variables de tipo `WebElement`. |
| **Recomendación** | Recomendado para frameworks con aplicaciones SPA (Single Page Applications) altamente dinámicas y complejas. | Ideal para sitios estáticos o formularios sencillos con elementos fijos y definidos. |
