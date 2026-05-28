# Manual de Estrategias de Localizadores en Selenium

Este manual detallado cubre todas las estrategias de localización disponibles en Selenium WebDriver, con un enfoque en aplicaciones prácticas y código en Java. En la automatización de pruebas, la elección correcta de un localizador es crucial para la estabilidad y el mantenimiento a largo plazo de tus scripts.

---

## 1. Introducción a los Localizadores
Un "Locator" o Localizador es un método que le indica a Selenium cómo encontrar un elemento web específico dentro del DOM (Document Object Model) de una página HTML. WebDriver utiliza la interfaz `By` para definir la estrategia de búsqueda.

> **Regla de Oro:** Un buen localizador debe ser único, descriptivo y poco propenso a cambiar cuando la interfaz gráfica reciba actualizaciones menores.

---

## 2. Estrategias Básicas de Localización

### 2.1 Por ID (`By.id`)
Es la forma más rápida y segura de ubicar un elemento, ya que el estándar HTML indica que un ID debe ser único dentro de la página.

**Ejemplo HTML:**
```html
<input type="text" id="username_field" name="usuario" class="input-form">
```
**Uso en Java:**
```java
By locatorId = By.id("username_field");
driver.findElement(locatorId).sendKeys("admin");
```

### 2.2 Por Name (`By.name`)
Útil cuando los elementos (especialmente inputs de formularios) tienen un atributo `name`. A diferencia del ID, el `name` puede no ser único (ej. radio buttons). Selenium devolverá el primer elemento que coincida.

**Uso en Java:**
```java
By locatorName = By.name("usuario");
driver.findElement(locatorName).sendKeys("admin");
```

### 2.3 Por Class Name (`By.className`)
Busca elementos basándose en el valor del atributo `class`. No es recomendable si la clase es muy genérica (ej. `btn-primary`), ya que devolverá el primer botón que encuentre, que podría no ser el que buscas. **Nota:** No soporta clases múltiples separadas por espacios.

**Ejemplo HTML:**
```html
<button class="btn btn-success submit-btn">Enviar</button>
```
**Uso en Java:**
```java
By locatorClass = By.className("submit-btn");
driver.findElement(locatorClass).click();
```

### 2.4 Por Tag Name (`By.tagName`)
Localiza elementos por el nombre de la etiqueta HTML (`a`, `input`, `button`, `div`). Es muy útil para extraer colecciones de elementos.

**Uso en Java (Extraer todos los enlaces):**
```java
List<WebElement> enlaces = driver.findElements(By.tagName("a"));
System.out.println("Total de enlaces en la página: " + enlaces.size());
```

### 2.5 Por Link Text (`By.linkText`) y Partial Link Text (`By.partialLinkText`)
Exclusivos para etiquetas de anclaje `<a>`. Buscan por el texto visible del enlace.
- `linkText` busca una coincidencia exacta.
- `partialLinkText` busca una subcadena (muy útil si el texto del enlace cambia dinámicamente).

**Ejemplo HTML:**
```html
<a href="/login">Haz clic aquí para Iniciar Sesión</a>
```
**Uso en Java:**
```java
By exacto = By.linkText("Haz clic aquí para Iniciar Sesión");
By parcial = By.partialLinkText("Iniciar Sesión");
```

---

## 3. Selectores Avanzados: CSS Selectors (`By.cssSelector`)
Los selectores CSS son extremadamente rápidos en la ejecución nativa del navegador. Su sintaxis es idéntica a la que usan los desarrolladores frontend en sus hojas de estilo.

### Sintaxis Básica CSS
- **ID:** Se usa `#` -> `#username_field`
- **Class:** Se usa `.` -> `.submit-btn`
- **Tag:** Se usa el nombre directo -> `input`

### Sintaxis por Atributos
```java
// Busca un input cuyo atributo 'placeholder' sea 'Email'
By cssAtributo = By.cssSelector("input[placeholder='Email']");
```

### Comodines en CSS (Expresiones Regulares Básicas)
- **Empieza con (`^=`):** `input[id^='user_']` (Encontrará `user_123`, `user_name`)
- **Termina con (`$=`):** `input[id$='_field']` (Encontrará `username_field`, `password_field`)
- **Contiene (`*=`):** `button[class*='success']` (Encontrará `btn-success-login`)

### Combinadores CSS
- **Hijo Directo (`>`):** `div.panel > p` (Párrafos que son hijos inmediatos del div)
- **Descendiente (espacio):** `div.panel p` (Cualquier párrafo dentro del div, sin importar la profundidad)
- **Hermanos (`+` o `~`):** `h1 + p` (El primer párrafo inmediatamente después de h1)

---

## 4. Selectores Avanzados: XPath (`By.xpath`)
XPath (XML Path Language) permite navegar por toda la estructura del DOM en cualquier dirección (hacia arriba o hacia abajo). Es la herramienta más poderosa cuando no tienes IDs ni clases estables.

### XPath Absoluto vs Relativo
- **Absoluto:** Empieza desde la raíz `/html/body/div[1]/form/input`. **Nunca lo uses**, si cambia un solo div en el HTML, el test fallará.
- **Relativo:** Empieza en cualquier nodo de la página mediante `//`. Es el estándar de la industria.

### Sintaxis Básica XPath Relativo
Formato general: `//etiqueta[@atributo='valor']`
```java
By xpathBasico = By.xpath("//input[@name='usuario']");
By xpathMultiple = By.xpath("//input[@name='usuario' and @type='text']");
```

### Funciones de Texto en XPath
- **`text()`:** Coincidencia exacta del texto visible del nodo.
  ```java
  By xpathText = By.xpath("//button[text()='Enviar Formulario']");
  ```
- **`contains()`:** Muy potente para textos o atributos dinámicos (que cambian parcialmente).
  ```java
  // Busca un botón cuyo texto contenga "Enviar"
  By xpathContainsTxt = By.xpath("//button[contains(text(), 'Enviar')]");
  
  // Busca un div cuya clase contenga "active"
  By xpathContainsAttr = By.xpath("//div[contains(@class, 'active')]");
  ```
- **`starts-with()`:** Para elementos cuyos IDs o clases se generan dinámicamente, pero su inicio es constante.
  ```java
  By xpathStartsWith = By.xpath("//input[starts-with(@id, 'session_')]");
  ```

### Navegación por Ejes XPath (Axes)
Los ejes permiten buscar nodos relativos a otro nodo conocido.

1. **Localizar al Padre (`parent` o `..`)**
   ```java
   By xpathPadre = By.xpath("//input[@id='username']/parent::div");
   // Alternativa corta: "//input[@id='username']/.."
   ```
2. **Localizar Hermanos Posteriores (`following-sibling`)**
   ```java
   // Útil en tablas para encontrar el valor al lado de una etiqueta
   By xpathHermano = By.xpath("//td[text()='Total']/following-sibling::td");
   ```
3. **Localizar Ascendientes Lejanos (`ancestor`)**
   ```java
   // Busca la tabla (ancestor) que contiene la celda con el texto "Dato 1"
   By xpathAncestro = By.xpath("//td[text()='Dato 1']/ancestor::table");
   ```

---

## 5. CSS Selector vs XPath: ¿Cuál elegir?

| Característica | CSS Selector | XPath |
| :--- | :--- | :--- |
| **Rendimiento / Velocidad** | Ligeramente más rápido (optimizado por navegadores). | Ligeramente más lento. |
| **Legibilidad** | Corto, limpio y muy legible. | Tiende a ser más verboso y complejo. |
| **Flexibilidad** | Excelente, pero solo navega hacia abajo (DOM). | Superior. Permite navegar hacia arriba (Padres/Ancestros). |
| **Búsqueda por Texto** | No soportado nativamente (a menos que dependa de IDs/clases). | Altamente soportado (`text()`, `contains()`). |

> **Veredicto Senior:** Prioriza siempre **ID** o **Name**. Si no están disponibles, usa **CSS Selectors** por su velocidad y legibilidad. Usa **XPath** cuando necesites búsquedas complejas basadas en texto o navegar hacia elementos padre/hermanos.

---

## 6. Mejores Prácticas de QA (Framework FSeleniumIA)
1. **Atributos de Datos Personalizados (`data-testid`, `data-cy`):** Lo ideal es pedir a los desarrolladores que agreguen selectores exclusivos para pruebas.
   ```java
   By locatorTestId = By.cssSelector("[data-testid='login-button']"); // A prueba de balas
   ```
2. **No usar selectores autogenerados del navegador:** No copies el selector directamente del menú "Copy Selector" de Chrome DevTools si se ve así: `#root > div > div:nth-child(2) > form > input`. Son extremadamente frágiles.
3. **Evita la indexación estática en XPath:** Trata de evitar `//table/tr[4]/td[2]`. Si se agrega una fila nueva arriba, tu prueba se romperá. Usa referencias relativas al contenido, ej: `//td[text()='Juan']/following-sibling::td`.
