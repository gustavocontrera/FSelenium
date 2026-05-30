# Fundamentos de Java y POO para QA Automation

Este manual está diseñado para llevarte paso a paso por los conceptos fundamentales de Java y la Programación Orientada a Objetos (POO), destilando los conceptos clave orientados exclusivamente a resolver los problemas del día a día en la automatización de pruebas con Selenium WebDriver.

---

## 1. Tipos de Datos y Estructuras de Control

Antes de interactuar con el navegador, es crucial dominar el lenguaje.

### 1.1 Tipos de Datos y Variables
En Java, cada variable debe tener un tipo declarado.
- **Primitivos:** `int` (enteros), `double` (decimales), `boolean` (verdadero/falso), `char` (carácter).
- **De Referencia:** `String` (texto), `List`, objetos de clases personalizadas.

```java
String url = "https://www.ejemplo.com";
int timeoutMilisegundos = 5000;
boolean esVisible = true;
```

### 1.2 Estructuras de Control y Bucles
Iterar es el pan de cada día en QA, especialmente al interactuar con Tablas o Grillas web.
- **if / else:** Para tomar decisiones basadas en condiciones.
- **Bucle `for` Clásico:** Útil cuando necesitas interactuar con elementos mediante su índice.
- **Bucle `for-each` (Recomendado):** Más limpio cuando solo quieres iterar sobre los elementos.

```java
// Ejemplo iterando sobre una lista de WebElements
List<WebElement> botones = driver.findElements(By.className("btn"));
for(WebElement boton : botones) {
    if(boton.getText().equals("Aceptar")) {
        boton.click();
        break; // Detiene el bucle inmediatamente
    }
}
```

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 2. Programación Orientada a Objetos (POO)

La POO nos permite modelar las páginas web y nuestros tests como "objetos", haciendo el código más mantenible.

### 2.1 Clases vs Objetos
- **Clase:** Es un "plano" o plantilla. Por ejemplo, la clase `PaginaLogin`.
- **Objeto (Instancia):** Es la "casa" construida a partir de ese plano. Cuando en tu test escribes `new PaginaLogin()`, estás instanciando un objeto real.

### 2.2 Encapsulamiento y Modificadores de Acceso
Ocultar los detalles internos y mostrar solo lo necesario.
- `public`: Cualquier clase del proyecto puede verlo. Los métodos (acciones) como `iniciarSesion()` siempre deben ser `public`.
- `private`: Solo la propia clase puede verlo. **Tus localizadores (XPath, ID) SIEMPRE deben ser `private`.**
- `protected`: Solo la clase y las que "heredan" de ella pueden verlo.

### 2.3 Herencia y la palabra `super`
La herencia (`extends`) permite que una clase herede métodos de una clase "padre". Es la base del **Page Object Model**.
En Selenium, creamos una `BasePage` que contenga métodos comunes (esperas, inicialización del driver) para que las demás páginas hereden de ella.

```java
// CLASE PADRE
public class BasePage {
    protected WebDriver driver;
    
    public BasePage(WebDriver driver) {
        this.driver = driver; 
    }
}

// CLASE HIJA
public class HomePage extends BasePage {
    public HomePage(WebDriver driver) {
        super(driver); // Invoca al constructor del padre
    }
}
```

### 2.4 Polimorfismo
Capacidad de usar una misma interfaz para diferentes tipos subyacentes.
- **En Selenium:** `WebDriver` es una interfaz. Podemos inicializarla como `ChromeDriver`, `FirefoxDriver`, y el resto del código no cambia.

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 3. Colecciones de Datos (`List` vs `Set`)

Cuando le pides a Selenium múltiples elementos (usando `findElements`), te devuelve una Colección de tipo `List`.

- **`List` (Listas):** Ordenadas y permiten duplicados. Cada elemento tiene un índice (0, 1, 2...). 
  - *Uso QA:* `List<WebElement>` para extraer resultados de una búsqueda.
  
- **`Set` (Conjuntos):** Desordenados y **no permiten duplicados**.
  - *Uso QA:* Crítico para manejar Múltiples Pestañas. `driver.getWindowHandles()` devuelve un `Set<String>` con los identificadores únicos de cada ventana abierta.

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 4. Excepciones: `try-catch-finally`

En Selenium, los elementos a veces no cargan, las conexiones fallan, o el diseño de la página cambia. Si no capturas una excepción, tu suite de pruebas se detiene.

```java
public void clicSeguro(By locator) {
    try {
        // INTENTA hacer esto
        driver.findElement(locator).click();
        
    } catch (ElementClickInterceptedException e) {
        // SI FALLA por estar interceptado, haz esto (Plan B)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(locator));
        
    } finally {
        // Esto se ejecuta SIEMPRE, haya fallado o no.
        System.out.println("Terminando intento de clic.");
    }
}
```

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 5. Variables Estáticas vs De Instancia

Una variable normal le pertenece **exclusivamente al objeto** que fue creado. Una variable `static` le pertenece **a la Clase** y es compartida universalmente.

### Por qué NO usar `public static WebDriver driver;`
Si declaras tu navegador como estático y corres pruebas en paralelo, el hilo 1 y el hilo 2 chocarán compartiendo el mismo navegador. Todo colapsa.
**La Solución Senior:** Usar `ThreadLocal<WebDriver>`. Esto permite que cada hilo de ejecución tenga una copia secreta e independiente del driver.

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 6. Buenas Prácticas (SOLID, DRY, KISS) y Métodos Eficientes

### 6.1 DRY (No te repitas) y KISS (Mantenlo simple)
Si copias y pegas código, algo está mal. Extrae la lógica repetida a métodos o clases base. Nombra variables de forma descriptiva.

### 6.2 Principios SOLID aplicados a QA
- **Single Responsibility:** Un Page Object solo debe representar una página web. No debe contener lógica de testeo ni aserciones (Asserts). Las aserciones van en la clase Test.
- **Open/Closed:** El código debe estar abierto a extensión pero cerrado a modificación.

### 6.3 Métodos "Wrappers" (Envoltorios)
En lugar de usar los métodos nativos de Selenium en crudo, "envuélvelos" en métodos propios en tu `BasePage` para agregar esperas inteligentes.

```java
public void escribirTexto(By locator, String texto) {
    WebElement elemento = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    elemento.clear();
    elemento.sendKeys(texto);
}
```

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 7. Ejercicios Prácticos

### Ejercicio 1: Clases y Encapsulamiento
**Objetivo:** Crear una clase `Usuario` para representar datos de prueba, con propiedades privadas `nombreUsuario` y `password`, un constructor y métodos Getters.

### Ejercicio 2: Refactorización
**Objetivo:** Crear un método en una clase `BasePage` llamado `public void clickear(By localizador)` que incluya una espera explícita `WebDriverWait` antes de hacer el clic real.

### Ejercicio 3: Implementando POM Completo
**Objetivo:** Crear una clase `GoogleSearchPage` que herede de `BasePage`, definiendo los localizadores de forma privada y exponiendo un método público `buscarTexto(String texto)`.

> Dominar Java es el verdadero salto de calidad entre grabar pruebas (Record & Playback) y ser un Ingeniero de Automatización.

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>
