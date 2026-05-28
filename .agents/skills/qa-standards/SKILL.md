# Skill: Estándares de QA Automation en FSeleniumIA

## Propósito
Esta habilidad define los estándares y directrices técnicas obligatorias para el mantenimiento, extensión y diseño de pruebas automatizadas en este framework (Selenium, Java, Cucumber y TestNG). Su objetivo es garantizar la mantenibilidad, escalabilidad y reusabilidad del framework.

---

## 1. Diseño y Estructura del Framework

### Page Object Model (POM)
- **Aislamiento de Locators:** Todos los selectores (XPath, ID, CSS) de una página deben declararse de forma privada y encapsulada dentro de su clase respectiva (ej: `PaginaPrincipal`).
- **Agnósticos a la Ejecución:** Las clases Page Object deben modelar exclusivamente la estructura y el comportamiento de la interfaz de usuario. No deben contener código específico de Cucumber o TestNG (como anotaciones `@Test`, `@Given` ni aserciones de pruebas).
- **Herencia:** Toda nueva página del sistema debe heredar de `BasePage` y utilizar su constructor por defecto para recibir el WebDriver y el WebDriverWait correspondientes de forma transparente.

### Independencia del Framework de Pruebas (Híbrido)
- La infraestructura de WebDriver (`DriverManager`) y los Page Objects deben ser 100% reutilizables tanto en escenarios BDD con Cucumber como en pruebas unitarias puras de TestNG.
- Para Cucumber, la inicialización del driver se gestiona en `Hooks.java`.
- Para TestNG, la inicialización del driver se gestiona heredando de `BaseTest.java`.

---

## 2. Gestión de WebDriver y Esperas

### Thread-Safety (Seguridad para Hilos)
- **DriverManager:** El acceso a la instancia del driver debe realizarse siempre a través de `DriverManager.getDriver()`. Bajo ninguna circunstancia se debe instanciar directamente un `ChromeDriver` u otro navegador fuera del gestor.
- NUNCA declarar el `WebDriver` como una variable estática (`static`) dentro de las clases de ejecución o utilidades.

### Estrategia de Esperas (Waits)
- **Esperas Explícitas:** Se deben preferir esperas explícitas utilizando la instancia `wait` de `BasePage` (que emplea `WebDriverWait`).
- **Evitar Esperas Fijas:** Está estrictamente prohibido el uso de esperas fijas (`Thread.sleep()`). Si un elemento tarda en cargarse, usa las condiciones esperadas de Selenium (`ExpectedConditions`).

---

## 3. Centralización de Datos y Configuración

- **Configuraciones de Entorno:** URL base (`baseUrl`), navegador (`browser`) y tiempos de espera (`timeout`) deben configurarse centralizadamente en `src/test/resources/config.properties`.
- **Lector de Propiedades:** Utiliza siempre `ConfigReader.getProperty(clave)` para acceder a estos valores. No hardcodear URLs ni credenciales directamente en el código de automatización.

---

## 4. Estándares de Codificación y Documentación

- **Comentarios y Documentación:** Todos los nuevos métodos creados en el framework deben estar documentados con JavaDocs claros que detallen su función, parámetros de entrada y retorno.
- **Nomenclatura:**
  - Clases Page Object: `Pagina[Nombre]` o `[Nombre]Page`.
  - Clases Step Definitions: `[Nombre]Steps` o `[Nombre]StepDefinitions`.
  - Clases de Prueba TestNG: `[Nombre]Test` (ubicadas en el paquete `tests`).

---

## 5. Enfoque Didáctico y Educativo

Este framework también cumple un **rol educativo** para enseñar automatización con Selenium. Por lo tanto, al interactuar o agregar código:
- **Claridad sobre Complejidad:** Prioriza soluciones fáciles de entender. Evita la sobreingeniería o sintaxis oscura de Java si existe una alternativa más legible.
- **Autoexplicativo:** El código debe explicar "qué" hace, pero si implementas un patrón complejo, añade comentarios adicionales explicando "por qué" se hace de esa manera (por ejemplo, el uso de `ThreadLocal`).
- **Nuevos Manuales:** Cualquier concepto avanzado nuevo introducido en el framework debe ser acompañado por un documento explicativo en el directorio `docs/`.
