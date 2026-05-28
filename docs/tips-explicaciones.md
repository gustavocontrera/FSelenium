# Explicaciones, Tips y Buenas Prácticas

Este documento centraliza los conocimientos clave para entender cómo funciona la arquitectura de pruebas híbridas en Java y cómo sacarle el máximo provecho al diseño.

---

## 1. El Flujo de Ejecución de Cucumber (BDD)
Cuando ejecutas una prueba orientada a comportamiento (BDD) mediante el archivo `TestRunner`, el framework realiza el siguiente flujo:

1. **Feature File (`.feature`)**: Cucumber lee el escenario escrito en Gherkin (texto en lenguaje natural).
2. **Hooks (`Hooks.java`)**: Se dispara el método `@Before`. Aquí el `DriverManager` arranca un nuevo navegador exclusivo para este escenario de manera totalmente aislada.
3. **Step Definitions (`steps/*.java`)**: Cucumber mapea la frase Gherkin con su método en Java. Ejemplo: `Given I navigate to...` ejecuta la función correspondiente en la clase Steps.
4. **Page Objects (`pages/*.java`)**: Los métodos en los Steps interactúan con las páginas. Las páginas encapsulan las interacciones directas de Selenium.
5. **Hooks (Fin)**: Terminan los pasos, y se dispara el método `@After`. Si el test falló, saca una captura de pantalla. Finalmente, destruye la sesión del navegador.

---

## 2. El Patrón Page Object Model (POM) con Herencia
El POM es un patrón de diseño que evita la duplicación de código y permite que, si la interfaz de usuario cambia, solo tengamos que actualizar un único lugar en nuestro código.

En `FSeleniumIA` implementamos el patrón con **Herencia**:
*   Tenemos una clase `BasePage.java` que posee el WebDriver y métodos comunes (como `clickElement`, `write`, esperas implícitas, interacciones con Dropdowns).
*   Cada nueva página (ej: `PaginaRegistro.java`) debe **heredar** (`extends BasePage`) de esta clase padre.
*   *Beneficio didáctico*: No tienes que re-escribir toda la lógica de esperas ni interactuar directo con el WebDriver en cada página. Solo te concentras en definir los localizadores (XPaths, CSS Selectors) y acciones de la vista.

---

## 3. Manejo de Esperas: La Regla de Oro
Selenium ofrece esperas para sincronizar el código con la velocidad del navegador.

> ❌ **Tip Crítico:** **NUNCA utilices `Thread.sleep(5000);`**.
> Esta función congela el hilo de Java estáticamente por 5 segundos enteros, incluso si el elemento ya cargó en el segundo 1. Esto infla drásticamente el tiempo total de tu suite de pruebas.

✔️ **Uso Correcto (Esperas Explícitas)**:
El `BasePage` de este framework ya utiliza `WebDriverWait`. Cuando usas un método como `clickElement`, internamente el `BasePage` evalúa si el elemento está presente y clickeable usando `ExpectedConditions`. En cuanto aparece, avanza; si no, espera hasta un `timeout` definido. 

---

## 4. Consejos de Depuración en IntelliJ
Si un test de automatización está fallando y no sabes por qué, en lugar de agregar "prints", usa el Debugger:
1. Pon un punto de interrupción (red dot) en la línea de código donde crees que está el error (ej: en el Step o en la Acción del Page Object).
2. Haz clic derecho sobre la prueba y en lugar de "Run", elige **"Debug"**.
3. El navegador se abrirá, y la ejecución se pausará justo en tu línea de código. 
4. Puedes utilizar la consola "Evaluate Expression" (Alt + F8) en IntelliJ para probar localizadores XPath en tiempo real sobre el navegador pausado.
