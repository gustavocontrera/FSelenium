# Curso Intensivo Paso a Paso: Selenium, Java y Cucumber BDD

Bienvenido al curso definitivo de automatización de pruebas integradas. Este manual está diseñado como un libro de texto guiado. Al finalizar, serás capaz de construir arquitecturas de QA sólidas y escalables.

> 📚 **Pre-requisito:** Antes de dominar la arquitectura, debes saber cómo interactuar con el HTML. Por favor, lee de forma obligatoria el [Manual de Localizadores](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/docs/manual-localizadores.md) adjunto en este framework.

---

## Módulo 1: Fundamentos y Configuración Inicial (Paso a Paso)

### 1.1 Entendiendo Selenium WebDriver
Selenium no "testea" nativamente. Selenium simplemente provee una API (Application Programming Interface) que se comunica directamente con el navegador utilizando el **Protocolo W3C WebDriver**. Cuando en Java escribimos `.click()`, Selenium traduce eso en un comando HTTP estandarizado que el navegador entiende y ejecuta.

### 1.2 Paso 1: Configurar el proyecto (Maven)
Maven es el corazón de la gestión en Java. En lugar de descargar librerías manualmente, usamos el archivo `pom.xml`.

**¿Cómo debe lucir un pom.xml básico?**
```xml
<dependencies>
    <!-- El núcleo de Selenium -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.23.1</version>
    </dependency>
    <!-- Motor de Pruebas -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.11.0</version>
        <scope>test</scope>
    </dependency>
    <!-- Gestor Automático de Navegadores -->
    <dependency>
        <groupId>io.github.bonigarcia</groupId>
        <artifactId>webdrivermanager</artifactId>
        <version>6.3.3</version>
    </dependency>
</dependencies>
```

### 1.3 Paso 2: Hola Mundo en Selenium (La forma rústica)
Antes de construir arquitecturas complejas, veamos cómo se abre una página de forma directa:

```java
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HolaMundoSelenium {
    public static void main(String[] args) {
        // 1. Descarga el driver adecuado para tu versión de Chrome
        WebDriverManager.chromedriver().setup();
        
        // 2. Levanta el navegador (Sesión)
        WebDriver driver = new ChromeDriver();
        
        // 3. Maximiza la ventana y navega
        driver.manage().window().maximize();
        driver.get("https://www.google.com");
        
        // 4. Imprime el título para verificar
        System.out.println("El título es: " + driver.getTitle());
        
        // 5. Destruye la sesión (Crítico para no consumir RAM infinita)
        driver.quit();
    }
}
```

---

## Módulo 2: Cucumber y el Diseño Basado en Comportamientos (BDD)

BDD acorta la brecha entre desarrolladores, QA y negocio (Product Owners). Las pruebas se escriben en lenguaje humano (Gherkin).

### 2.1 Escribiendo Escenarios (Archivos `.feature`)
```gherkin
Feature: Autenticación de Usuario
  Como cliente registrado
  Quiero iniciar sesión
  Para acceder a mi panel privado

  Scenario: Iniciar sesión exitosamente
    Given que navego a la página de login
    When ingreso el usuario "admin@test.com" y la clave "12345"
    Then el panel de control es visible
```

### 2.2 Paso a Paso: Mapeando Gherkin a Java (Step Definitions)
Cada oración del archivo `.feature` debe coincidir con un método en Java.

```java
public class LoginSteps {
    
    // El texto dentro de @Given debe coincidir EXACTAMENTE con el .feature
    @Given("que navego a la página de login")
    public void navegarAlLogin() {
        // Aquí interactuaremos con el navegador más adelante
        System.out.println("Navegando...");
    }

    // Pasando parámetros desde Gherkin (usando {string})
    @When("ingreso el usuario {string} y la clave {string}")
    public void ingresarCredenciales(String user, String password) {
        System.out.println("Login con: " + user + " y " + password);
    }
}
```

---

## Módulo 3: Profundización Absoluta en Page Object Model (POM)

### 3.1 El problema del Código Espagueti (Lo que NO debes hacer)
Imagina tener 50 pruebas que inician sesión así:

```java
// MAL EJEMPLO
driver.findElement(By.id("user_email")).sendKeys("test@test.com");
driver.findElement(By.id("user_password")).sendKeys("123");
driver.findElement(By.cssSelector("button[type='submit']")).click();
```
Si el desarrollador cambia el ID `user_email` a `email_address`, ¡tendrás que corregir 50 archivos!

### 3.2 Paso a Paso: Refactorizando al Patrón POM
El POM centraliza la página en un solo archivo.

**Paso 1: Crear la clase de la página (La Vista)**
```java
public class PaginaLogin {
    // 1. Encapsulamiento: Los localizadores son privados.
    private By emailInput = By.id("user_email");
    private By passwordInput = By.id("user_password");
    private By submitButton = By.cssSelector("button[type='submit']");
    
    // 2. WebDriver inyectado
    private WebDriver driver;
    
    public PaginaLogin(WebDriver driver) {
        this.driver = driver;
    }
    
    // 3. Acciones de negocio (Públicas)
    public void ingresarCredenciales(String email, String pass) {
        driver.findElement(emailInput).sendKeys(email);
        driver.findElement(passwordInput).sendKeys(pass);
        driver.findElement(submitButton).click();
    }
}
```

**Paso 2: Herencia (El nivel Senior)**
En nuestro framework `FSeleniumIA`, no queremos inyectar el `WebDriver` y llamar a `driver.findElement` mil veces. Creamos una **`BasePage`** que hace el trabajo sucio.

```java
// Así luce en FSeleniumIA (Elegante y Limpio)
public class PaginaLogin extends BasePage {
    private By emailInput = By.id("user_email");
    
    public void ingresarEmail(String email) {
        // write() es heredado de BasePage, ya incluye esperas explícitas automáticas.
        write(emailInput, email); 
    }
}
```

---

## Módulo 4: TestNG y Aserciones Avanzadas

### 4.1 Aserciones (Validando resultados)
Una prueba no es una prueba si no verifica algo. TestNG nos da aserciones poderosas.

```java
// Hard Assert: Si esto falla, la prueba se aborta INMEDIATAMENTE.
Assert.assertEquals(textoObtenido, "Bienvenido", "El texto no coincide.");
Assert.assertTrue(elemento.isDisplayed(), "El botón debería estar visible.");

// Soft Assert: Si falla, la prueba CONTINÚA y reporta todas las fallas al final.
SoftAssert softAssert = new SoftAssert();
softAssert.assertEquals(color, "Rojo");
softAssert.assertTrue(estaVisible);
softAssert.assertAll(); // Crítico: Si no llamas esto, la prueba siempre pasa.
```

### 4.2 Ejecución Parametrizada (@DataProvider)
Permite correr un test de TestNG múltiples veces con datos distintos.

```java
@DataProvider(name = "credenciales")
public Object[][] datosLogin() {
    return new Object[][] {
        {"admin@test.com", "1234"},
        {"user@test.com", "pass123"},
        {"bloqueado@test.com", "0000"}
    };
}

// El test se ejecutará 3 veces automáticamente
@Test(dataProvider = "credenciales")
public void testLogin(String user, String pass) {
    PaginaLogin login = new PaginaLogin();
    login.iniciarSesion(user, pass);
}
```

---

## Módulo 5: Interacciones Complejas en Selenium

### 5.1 Formularios: La Clase `Select`
Para interactuar con listas desplegables (Dropdowns) nativas `<select>`.

```java
WebElement dropdown = driver.findElement(By.id("paises"));
Select selectPais = new Select(dropdown);

// 3 formas de seleccionar:
selectPais.selectByVisibleText("Argentina"); // Lo que ve el usuario (Mejor)
selectPais.selectByValue("ARG");             // Por el atributo 'value' HTML
selectPais.selectByIndex(2);                 // La tercera opción (Poco seguro)
```

### 5.2 Estrategias de Espera (Sincronización)

> 🚨 **ERROR COMÚN:** Usar `Thread.sleep(5000)`. Congela todo y hace la ejecución muy lenta.

**Opción A: Implicit Wait (Global)**
```java
// Espera HASTA 10 segundos intentando buscar elementos. 
// Si aparece en el segundo 2, avanza inmediatamente.
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```

**Opción B: Explicit Wait (Específico)**
La mejor práctica absoluta. Espera por una condición.
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
// Esperar hasta que un botón invisible se vuelva clickeable
WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
btn.click();
```

### 5.3 Simulación de Mouse: Clase `Actions`
Para arrastrar y soltar (Drag and Drop), mover el mouse por encima (Hover), etc.

```java
Actions actions = new Actions(driver);
WebElement arrastrable = driver.findElement(By.id("caja1"));
WebElement destino = driver.findElement(By.id("caja2"));

// Arrastrar y Soltar
actions.dragAndDrop(arrastrable, destino).perform();

// Mover el mouse por encima de un menú para que se despliegue
WebElement menu = driver.findElement(By.id("menu-desplegable"));
actions.moveToElement(menu).perform();
```

---

## Módulo 6: Estándares Arquitectónicos de un QA Senior

1. **Gestión de Hilos (Thread-Safety):** Si corres pruebas en paralelo, el hilo 1 y el hilo 2 chocarán si comparten una variable `static WebDriver driver`. Usamos `ThreadLocal<WebDriver>` en nuestra clase `DriverManager` para que cada hilo tenga su propia copia del navegador, aislada de las demás.
2. **Abstracción Total de URLs:** No debe haber ni un solo `http://...` en tu código Java. Se lee todo dinámicamente usando una clase custom como `ConfigReader.java` que extrae los datos de `config.properties`.
3. **Manejo de Errores Silencioso:** En los *Hooks* de Cucumber, si un escenario falla (`scenario.isFailed()`), inyectamos un bloque `try-catch` que toma un *Screenshot* nativo en bytes y lo pega en el reporte final automáticamente, sin detener abruptamente el flujo de limpieza.

---

# Apéndices del Framework (Crecimiento Futuro)

### Apéndice A: Data-Driven Avanzado
Para escenarios donde el `@DataProvider` no alcanza, el framework incluye la carpeta `dataloader/`. Usando la librería **Apache POI** para Excel o **OpenCSV** para archivos de texto, puedes cargar miles de filas para pruebas transaccionales masivas, inyectando esos valores a tus variables durante la inicialización de la prueba.

### Apéndice B: CI/CD y Headless Testing
Cuando se integran las pruebas en GitHub Actions o Jenkins, la máquina virtual no tiene pantalla. El navegador debe iniciar en "Headless Mode" (invisible).
```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless", "--window-size=1920,1080"); // Window size crítico para responsividad
driver = new ChromeDriver(options);
```

### Apéndice C: Estrategias de Debugging en IDE
Cuando recibes una excepción inentendible como `StaleElementReferenceException`:
1. El DOM se recargó y el WebElement que guardaste en una variable quedó "viejo".
2. **Solución:** Pon un *Breakpoint* (Punto de interrupción rojo) en IntelliJ, ejecuta en modo *Debug*, y usa *Evaluate Expression* para forzar una nueva búsqueda de ese elemento en vivo.

### Apéndice D: Integración de API (RestAssured)
Un framework Senior no automatiza precondiciones a través de la Interfaz Gráfica de Usuario (GUI) si es lento.
- **Caso:** Necesito probar cómo borrar un post. 
- **Malo:** Selenium navega a "Crear post", llena formularios, y luego va a borrarlo. (Tarda 1 minuto).
- **Senior:** Usa RestAssured (HTTP Client Java) en el `@Before` para enviar un JSON por POST, creando el artículo en la Base de Datos en milisegundos. Luego, abre Selenium directo en la URL del post y hace clic en Borrar.

### Apéndice E: Escalabilidad con Selenium Grid
Cuando 100 pruebas demoran demasiado en correr en una PC, usamos Grid. Levantas un Hub central y Nodos satélites (usualmente con contenedores Docker). En el código, sustituyes `new ChromeDriver()` por `new RemoteWebDriver(new URL("http://hub:4444"), options)`. Esto deriva las pruebas a las nubes.

### Apéndice F: El Patrón Factory
Para escalar el soporte de navegadores (Chrome, Edge, Firefox, Safari, Appium Mobile), en lugar de tener un `switch-case` interminable, se aplica el Patrón de Diseño Factory. Se crea una interfaz `BrowserFactory` y múltiples implementaciones (ej: `ChromeManager`, `FirefoxManager`). El framework decide en tiempo de ejecución qué manejador instanciar basándose en las variables de entorno.
