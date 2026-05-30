# Manual de Estrategias de Localizadores en Selenium

Aprender a localizar elementos de manera precisa en el DOM (Document Object Model) es la habilidad más crítica para cualquier QA Automation. Si el localizador falla, la prueba falla.

## 1. Introducción a los Localizadores
Selenium interactúa con el navegador buscando elementos HTML utilizando estrategias denominadas localizadores (`Locators`). En Java, esto se maneja mediante la clase `By`.

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 2. Estrategias Básicas de Localización

1. **`By.id()` (La Mejor Opción):** El ID de un elemento HTML debe ser único en toda la página según los estándares web. Es el método más rápido y seguro.
   - *Ejemplo:* `<input id="correo">` -> `driver.findElement(By.id("correo"));`

2. **`By.name()` (Segunda Mejor Opción):** Muy común en campos de formularios. Generalmente son únicos, pero en grupos de "Radio Buttons" pueden repetirse.
   - *Ejemplo:* `<input name="password">` -> `driver.findElement(By.name("password"));`

3. **`By.className()`:** Útil para encontrar listas de elementos similares, pero peligroso para elementos únicos porque las clases CSS cambian constantemente o se reutilizan.
   - *Ejemplo:* `<button class="btn btn-primary">` -> `driver.findElement(By.className("btn-primary"));`

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 3. Selectores Avanzados: CSS Selectors (`By.cssSelector`)
Si no tienes ID ni Name, el CSS Selector es el rey de la velocidad. Se lee de izquierda a derecha.

- **Por ID:** Usando `#`. Ejemplo: `By.cssSelector("#correo")`
- **Por Clase:** Usando `.`. Ejemplo: `By.cssSelector(".btn-primary")`
- **Por Atributo Específico:** `[atributo='valor']`. Ejemplo: `By.cssSelector("input[type='submit']")`
- **Relaciones Hija Directa:** `div > p` (Encuentra el párrafo que está directamente dentro del div).
- **Subcadena (Contiene):** `[id*='dinamico']` (Encuentra un ID que contenga la palabra "dinamico", útil para IDs autogenerados como `dinamico_12345`).

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 4. Selectores Avanzados: XPath (`By.xpath`)
XPath es el lenguaje más poderoso, capaz de moverse hacia adelante, hacia atrás y navegar jerarquías complejas. Pero también es el más lento.

- **XPath Absoluto (NO USAR):** `/html/body/div[2]/form/input`. Si la interfaz cambia mínimamente, el XPath se rompe.
- **XPath Relativo (EXCELENTE):** `//input[@id='correo']`. Busca en todo el DOM sin importar dónde esté anidado.
- **Búsqueda por Texto (El superpoder de XPath):** `//button[text()='Ingresar']`. Localiza un botón por su texto visible.
- **Ejes (Axes):** Navegación relativa. Ej: `//td[text()='Juan']/following-sibling::td` (Encuentra la celda que está inmediatamente después de la celda que dice "Juan", útil para leer tablas web).

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 5. CSS Selector vs XPath: ¿Cuál elegir?
- **Velocidad:** CSS Selector es nativo del motor de los navegadores y es significativamente más rápido que XPath.
- **Legibilidad:** CSS suele ser más corto y limpio.
- **Poder:** Solo XPath puede localizar por texto o navegar "hacia arriba" (del hijo al padre). 
- **Veredicto:** Usa `ID/Name` siempre que existan. Si no, intenta usar `CSS`. Si necesitas ubicar algo por su texto o navegar relaciones complejas, usa `XPath`.

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 6. Mejores Prácticas de QA (Framework FSelenium)
En nuestro framework, todos los localizadores deben estar declarados en la parte superior de las clases de tipo `Page Object` como variables privadas:
```java
public class PaginaLogin extends BasePage {
    private By txtUsuario = By.id("user");
    private By btnIngresar = By.cssSelector(".btn-login");
    // ...
}
```
Esto garantiza que si el equipo de Frontend cambia el ID, solo debamos actualizar una sola línea en todo el proyecto.

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>
