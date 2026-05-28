# Fundamentos de Java para QA Automation

Para dominar Selenium y automatizar pruebas como un Senior, primero debes comprender el idioma en el que estás hablando: Java. Este manual destila los conceptos clave de Java orientados exclusivamente a resolver los problemas del día a día en un framework de pruebas.

---

## 1. Programación Orientada a Objetos (POO)

Java es un lenguaje Orientado a Objetos. Todo gira en torno a Clases y Objetos.

### Clases vs Objetos
- **Clase:** Es un "plano" o plantilla. Por ejemplo, la clase `PaginaLogin`. Una clase define qué datos (variables) y qué acciones (métodos) tiene.
- **Objeto (Instancia):** Es la "casa" construida a partir de ese plano. Cuando en tu test escribes `new PaginaLogin()`, estás instanciando un objeto real en la memoria de la computadora que usarás para interactuar con el navegador.

```java
// El Plano (Clase)
public class Usuario {
    String nombre; // Atributo

    public void saludar() { // Método
        System.out.println("Hola, soy " + nombre);
    }
}

// La Construcción (Instanciación)
public class Prueba {
    public static void main(String[] args) {
        Usuario user1 = new Usuario(); // user1 es un Objeto
        user1.nombre = "Admin";
        user1.saludar();
    }
}
```

### Modificadores de Acceso (`public`, `private`, `protected`)
En automatización, **la encapsulación** es vital.

- `public`: Cualquier clase del proyecto puede verlo. Los métodos (acciones) de tus Page Objects como `iniciarSesion()` siempre deben ser `public` para que tus *Step Definitions* o Tests puedan usarlos.
- `private`: Solo la propia clase puede verlo. **Tus localizadores (XPath, ID) en un Page Object SIEMPRE deben ser `private`.** Ningún test debe interactuar directamente con un XPath; el test debe llamar al método público, y el método público usa el XPath privado.
- `protected`: Solo la propia clase y las clases que "heredan" de ella pueden verlo. 

---

## 2. Herencia y la palabra `super`

La herencia (palabra clave `extends`) permite que una clase "hija" herede todos los métodos y atributos de una clase "padre". Es la base del **Page Object Model**.

### El Problema
Todas tus páginas web necesitan inicializar `WebDriverWait`, necesitan interactuar con el mouse, etc. Escribir esa lógica de espera en las 50 páginas de tu aplicación sería una pesadilla.

### La Solución
Creas una clase padre `BasePage` y haces que las 50 páginas hereden de ella.

```java
// CLASE PADRE
public class BasePage {
    protected WebDriver driver; // protected para que las hijas lo vean
    
    // Constructor
    public BasePage() {
        this.driver = DriverManager.getDriver(); 
    }

    public void clickGenial(By locator) {
        // Lógica súper compleja de esperas antes de hacer clic...
        driver.findElement(locator).click();
    }
}

// CLASE HIJA
public class PaginaCarrito extends BasePage {
    private By btnPagar = By.id("pagar");

    // Al heredar, automáticamente heredas el constructor de BasePage
    // (A menos que declares uno y uses la palabra 'super()')
    
    public void realizarPago() {
        // clickGenial() no está escrito en PaginaCarrito, ¡pero lo podemos usar gracias a la herencia!
        clickGenial(btnPagar); 
    }
}
```

---

## 3. Estructuras de Control y Bucles

Iterar es el pan de cada día en QA, especialmente al interactuar con Tablas o Grillas web.

### Bucle `for` Clásico
Útil cuando necesitas llevar una cuenta numérica o interactuar con elementos mediante su índice (`[i]`).

```java
// Recorriendo filas de una tabla
List<WebElement> filas = driver.findElements(By.cssSelector("table tr"));
for (int i = 0; i < filas.size(); i++) {
    System.out.println("Fila " + i + ": " + filas.get(i).getText());
}
```

### Bucle `for-each` (Recomendado)
Más limpio y moderno cuando no te importa el índice numérico, solo quieres los elementos.

```java
List<WebElement> enlaces = driver.findElements(By.tagName("a"));
for (WebElement link : enlaces) {
    if (link.getText().contains("Borrar")) {
        link.click();
        break; // Detiene el bucle inmediatamente
    }
}
```

---

## 4. Colecciones de Datos (`List` vs `Set`)

Cuando le pides a Selenium múltiples elementos (usando `findElements`), te devuelve una Colección de tipo `List`.

- **`List` (Listas):** Ordenadas y permiten duplicados. Cada elemento tiene un índice (0, 1, 2...). 
  - *Uso QA:* `List<WebElement>` para extraer todos los resultados de una búsqueda.
  
- **`Set` (Conjuntos):** Desordenados y **no permiten duplicados**.
  - *Uso QA:* Crítico para manejar Múltiples Pestañas. `driver.getWindowHandles()` devuelve un `Set<String>` con los identificadores únicos de cada ventana abierta. Como cada pestaña es única, un `Set` garantiza que no haya IDs duplicados.

---

## 5. Excepciones: `try-catch-finally`

En Selenium, los elementos a veces no cargan, las conexiones fallan, o el diseño de la página cambia. Las Excepciones son "errores gritando por ayuda".

Si no capturas una excepción, tu suite de pruebas "explota" y se detiene la ejecución.

```java
public void clicSeguro(By locator) {
    try {
        // INTENTA hacer esto
        driver.findElement(locator).click();
        
    } catch (ElementClickInterceptedException e) {
        // SI FALLA por estar interceptado, haz esto (Plan B)
        System.out.println("El elemento está oculto. Usando JavaScriptExecutor...");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(locator));
        
    } finally {
        // Esto se ejecuta SIEMPRE, haya fallado o no.
        // Útil para cerrar conexiones a Bases de Datos o liberar memoria.
        System.out.println("Terminando intento de clic.");
    }
}
```

---

## 6. Variables Estáticas vs De Instancia (El peligro del `static WebDriver`)

Una variable normal (de Instancia) le pertenece **exclusivamente al objeto** que fue creado.
Una variable `static` le pertenece **a la Clase**, y por tanto es compartida universalmente en todo el programa.

### Por qué NO usar `public static WebDriver driver;`
Si declaras tu navegador como estático:
1. Escribes la prueba A y abre Chrome.
2. Comienza la prueba B en paralelo y abre otro Chrome. ¡Ups! Sobrescribió la variable compartida de la prueba A.
3. La prueba A intenta hacer clic, pero interactúa con la pestaña de la prueba B.
4. Todo colapsa.

### La Solución Senior: `ThreadLocal<WebDriver>`
Como viste en el `DriverManager.java` de este framework, usamos `ThreadLocal`. Es una variable especial de Java que le dice a la máquina virtual: *"Esta variable pertenece a toda la clase, PERO cada hilo de ejecución (Thread) tendrá una copia secreta e independiente de ella"*. Esto permite correr 50 pruebas en paralelo de forma segura.
