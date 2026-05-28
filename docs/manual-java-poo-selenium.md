# Manual Completo: Java, POO, Buenas Prácticas y Automatización con Selenium

Este manual está diseñado para llevarte paso a paso por los conceptos fundamentales de Java y la Programación Orientada a Objetos (POO), integrándolos con las mejores prácticas y su aplicación directa en la automatización de pruebas con Selenium WebDriver.

---

## 1. Fundamentos de Java para Automatización

Antes de interactuar con el navegador, es crucial dominar el lenguaje en el que escribimos las instrucciones.

### 1.1 Tipos de Datos y Variables
En Java, cada variable debe tener un tipo declarado.
- **Primitivos:** `int` (enteros), `double` (decimales), `boolean` (verdadero/falso), `char` (carácter).
- **De Referencia:** `String` (texto), `List`, objetos de clases personalizadas.

```java
String url = "https://www.ejemplo.com";
int timeoutMilisegundos = 5000;
boolean esVisible = true;
```

### 1.2 Estructuras de Control de Flujo
Nos permiten decidir qué código se ejecuta o repetirlo.
- **if / else:** Para tomar decisiones basadas en condiciones (ej. "Si el botón existe, haz clic").
- **for / while:** Para iterar sobre colecciones de elementos (ej. "Revisar todos los enlaces de una tabla").

```java
// Ejemplo iterando sobre una lista de WebElements
List<WebElement> botones = driver.findElements(By.className("btn"));
for(WebElement boton : botones) {
    if(boton.getText().equals("Aceptar")) {
        boton.click();
        break;
    }
}
```

---

## 2. Programación Orientada a Objetos (POO) en Automatización

La POO nos permite modelar las páginas web y nuestros tests como "objetos", haciendo el código más mantenible, reutilizable y escalable.

### 2.1 Clases y Objetos
- **Clase:** Es la plantilla (ej. un Page Object que representa la página de Login).
- **Objeto:** Es la instancia de la clase (ej. la página de Login con la que interactuamos en un test específico).

### 2.2 Pilares de la POO

#### A. Encapsulamiento
Ocultar los detalles internos y mostrar solo lo necesario.
- **En Selenium:** Ocultamos los localizadores (By) y el WebDriver dentro de la clase de la página. Exponemos métodos públicos como `iniciarSesion(usuario, password)`.

```java
public class LoginPage {
    // Encapsulados (privados)
    private WebDriver driver;
    private By txtUsuario = By.id("user");
    private By txtPassword = By.id("pass");
    private By btnLogin = By.id("loginBtn");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Método expuesto (público)
    public void iniciarSesion(String usuario, String password) {
        driver.findElement(txtUsuario).sendKeys(usuario);
        driver.findElement(txtPassword).sendKeys(password);
        driver.findElement(btnLogin).click();
    }
}
```

#### B. Herencia
Crear nuevas clases basadas en clases existentes.
- **En Selenium:** Crear una `BasePage` o `BaseTest` que contenga métodos comunes (esperas, inicialización del driver) para que las demás clases hereden de ella y no repitamos código.

```java
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
}

public class HomePage extends BasePage {
    // Hereda 'driver', 'wait' y el método 'click'
    public HomePage(WebDriver driver) {
        super(driver);
    }
}
```

#### C. Polimorfismo
Capacidad de usar una misma interfaz para diferentes tipos subyacentes.
- **En Selenium:** WebDriver es una interfaz. Podemos inicializarla como `ChromeDriver`, `FirefoxDriver`, etc., y el resto del código no cambia.

```java
WebDriver driver;
if (browser.equals("chrome")) {
    driver = new ChromeDriver();
} else {
    driver = new FirefoxDriver();
}
driver.get("https://..."); // El mismo método funciona para cualquier navegador
```

#### D. Abstracción
Enfocarse en qué hace un objeto en lugar de cómo lo hace. Interfaces y clases abstractas nos ayudan a definir "contratos".

---

## 3. Buenas Prácticas de Programación (SOLID, DRY, KISS)

### 3.1 DRY (Don't Repeat Yourself - No te repitas)
Si copias y pegas código, algo está mal. Extrae la lógica repetida a métodos o clases base.
- **Malo:** Escribir `Thread.sleep(2000)` antes de cada clic.
- **Bueno:** Crear un método centralizado `clickSeguro(By locator)` que maneje las esperas implícitas o explícitas.

### 3.2 KISS (Keep It Simple, Stupid - Mantenlo simple)
El código debe ser fácil de leer y entender por otros (y por ti mismo en el futuro).
- Nombra variables y métodos de forma descriptiva (ej. `btnAceptar` en lugar de `b1`).

### 3.3 Principios SOLID (Aplicados a QA)
- **S - Single Responsibility (Responsabilidad Única):** Un Page Object solo debe representar una página web o un componente. No debe contener lógica de testeo ni aserciones (Asserts). Las aserciones van en la clase Test.
- **O - Open/Closed (Abierto/Cerrado):** El código debe estar abierto a extensión (ej. agregar nuevas páginas) pero cerrado a modificación (no romper lo que ya funciona al agregar algo nuevo).

---

## 4. Creación y Uso de Métodos Eficientes en Selenium

El uso correcto de métodos mejora drásticamente la estabilidad de las pruebas.

### 4.1 Métodos "Wrappers" (Envoltorios)
En lugar de usar los métodos nativos de Selenium en crudo, "envuélvelos" en métodos propios para agregar logs, esperas inteligentes o manejo de excepciones.

**Ejemplo: Escribir texto con espera y limpieza previa.**

```java
/**
 * Escribe texto en un elemento, esperando a que sea visible y limpiando el campo previamente.
 * @param locator El localizador del elemento.
 * @param texto El texto a escribir.
 */
public void escribirTexto(By locator, String texto) {
    try {
        WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        elemento.clear();
        elemento.sendKeys(texto);
        System.out.println("Se escribió '" + texto + "' en el elemento: " + locator);
    } catch (Exception e) {
        System.err.println("Error al intentar escribir en " + locator + " - Detalle: " + e.getMessage());
        throw e; // Opcional: fallar la prueba o manejarlo
    }
}
```

### 4.2 Métodos que devuelven otros Page Objects (Fluent Page Object Model)
Cuando una acción en una página (ej. hacer clic en "Login") te lleva a otra página (ej. el Dashboard), el método debería devolver la instancia de la nueva página. Esto permite encadenar acciones.

```java
// En LoginPage.java
public DashboardPage hacerLoginExitoso(String user, String pass) {
    escribirTexto(txtUsuario, user);
    escribirTexto(txtPassword, pass);
    click(btnLogin);
    return new DashboardPage(driver);
}

// En el Test
LoginPage loginPage = new LoginPage(driver);
DashboardPage dashboard = loginPage.hacerLoginExitoso("admin", "1234");
dashboard.verificarBienvenida();
```

---

## 5. Ejercicios Prácticos (Paso a Paso)

### Ejercicio 1: Clases y Encapsulamiento (Java Básico)
**Objetivo:** Crear una clase `Usuario` que represente datos de prueba.
1. Crea una clase `Usuario`.
2. Añade propiedades privadas: `nombreUsuario` (String), `password` (String), `esAdmin` (boolean).
3. Crea un constructor para inicializar estos valores.
4. Genera los métodos "getters" (para obtener los valores) pero NO "setters" (haciendo que el objeto sea inmutable, una buena práctica para datos de prueba).

**Solución Ejercicio 1:**
```java
public class Usuario {
    private String nombreUsuario;
    private String password;
    private boolean esAdmin;

    public Usuario(String nombreUsuario, String password, boolean esAdmin) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.esAdmin = esAdmin;
    }

    public String getNombreUsuario() { return nombreUsuario; }
    public String getPassword() { return password; }
    public boolean isEsAdmin() { return esAdmin; }
}
```

### Ejercicio 2: Refactorización usando DRY y Métodos Wrappers
**Contexto:** Tienes este código repetitivo en tu clase:
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement btn1 = wait.until(ExpectedConditions.elementToBeClickable(By.id("b1")));
btn1.click();

WebElement btn2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("b2")));
btn2.click();
```

**Objetivo:** Crear un método en una clase `BasePage` que elimine esta repetición.
1. Crea la clase `BasePage`.
2. Escribe un método `public void clickear(By localizador)` que incluya la espera y el clic.
3. Úsalo.

**Solución Ejercicio 2:**
```java
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Método Wrapper reutilizable
    public void clickear(By localizador) {
        try {
            WebElement elemento = wait.until(ExpectedConditions.elementToBeClickable(localizador));
            elemento.click();
        } catch (TimeoutException e) {
            System.out.println("El elemento " + localizador + " no fue clickeable a tiempo.");
            throw e;
        }
    }
}
```

### Ejercicio 3: Implementando el patrón Page Object Model (POM) Completo
**Objetivo:** Modelar una página de búsqueda de Google.
1. Crea `GoogleSearchPage`.
2. Define los localizadores para la caja de búsqueda y el botón de "Buscar con Google".
3. Crea un método `buscarTexto(String texto)`.

**Solución Ejercicio 3:**
```java
public class GoogleSearchPage extends BasePage {
    // 1. Localizadores encapsulados
    private By cajaBusqueda = By.name("q");
    // Nota: en Google, a veces es mejor presionar ENTER que buscar el botón exacto, pero este es un ejemplo
    private By btnBuscar = By.name("btnK"); 

    // 2. Constructor
    public GoogleSearchPage(WebDriver driver) {
        super(driver); // Llama al constructor de BasePage
    }

    // 3. Métodos de Acción
    public void buscarTexto(String texto) {
        // Asumiendo que agregamos 'escribirTexto' a BasePage como en el punto 4.1
        escribirTexto(cajaBusqueda, texto);
        // Opcional: enviar la tecla Enter directamente si el botón está oculto
        driver.findElement(cajaBusqueda).sendKeys(Keys.ENTER); 
    }
}
```

---

## 6. Conclusión y Siguientes Pasos

Dominar Java y la Programación Orientada a Objetos es el verdadero salto de calidad entre ser alguien que graba pruebas (Record & Playback) y un verdadero Ingeniero de Automatización (SDET). 
Recuerda siempre:
- **Separa la lógica de la página (POM) de la lógica del test (Asserts).**
- **Crea envoltorios (wrappers) para las acciones comunes.**
- **No repitas código (DRY).**
- **Trata tu código de pruebas con el mismo respeto y calidad que el código de producción.**
