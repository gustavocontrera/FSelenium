# Explicaciones, Tips y Buenas Prácticas

En esta sección recopilamos los consejos más importantes para la supervivencia diaria en la automatización de pruebas con FSelenium.

## 1. El Flujo de Ejecución de Cucumber (BDD)
Cuando ejecutas una prueba BDD, la magia ocurre en tres capas:
1. **El Feature:** Escrito en Gherkin (`Given`, `When`, `Then`). Es el idioma de negocio.
2. **El Step Definition:** Código Java que enlaza el Gherkin con tus métodos técnicos mediante anotaciones como `@When("el usuario hace clic")`.
3. **El Page Object:** Donde reside realmente la interacción de Selenium (`driver.findElement(...)`).
> **Tip:** ¡Nunca pongas un `driver.findElement` dentro de un Step Definition! Eso rompe la arquitectura limpia.

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 2. El Patrón Page Object Model (POM) con Herencia
Todo Page Object debe extender de `BasePage`. ¿Por qué?
Porque `BasePage` ya tiene el `WebDriver` instanciado y todos los métodos genéricos (como `click()`, `escribir()`) envueltos en esperas explícitas automáticas.
- **Malo:** `driver.findElement(By.id("login")).click();`
- **Senior:** `click(botonLogin);` // Heredado de BasePage, incluye WebDriverWait internamente.

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 3. Manejo de Esperas: La Regla de Oro
**Jamás uses `Thread.sleep(5000);`.**
Eso congela tu programa sin importar si el elemento cargó en el segundo 1. Multiplica eso por 100 pruebas y tendrás horas de tiempo muerto.
Usa **Esperas Explícitas (`WebDriverWait`)**:
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dinamico")));
```
Esto esperará *hasta* 10 segundos, pero si carga al segundo 2, avanza instantáneamente.

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>

---

## 4. Consejos de Depuración en IntelliJ
Cuando recibes un `NoSuchElementException`:
1. ¿El elemento está dentro de un `iframe`? Si es así, debes usar `driver.switchTo().frame(...)` antes de buscarlo.
2. ¿Se abrió una nueva pestaña? Usa `driver.getWindowHandles()` para cambiar el foco.
3. Si la prueba va muy rápido, usa el modo **Debug** de IntelliJ poniendo un punto de interrupción (punto rojo) al lado de la línea conflictiva para frenar la ejecución y revisar la web en tiempo real.

<br><p align="right"><a href="#indice-general">⬆️ Volver al Índice General</a></p>
