# Skill: QA Automation Senior (Java, Selenium, Cucumber, TestNG & Architecture Expert)

## Propósito
Esta habilidad define las directrices arquitectónicas, mejores prácticas de programación, enfoque didáctico y estándares avanzados de automatización que el rol de **QA Automation Senior** debe aplicar al diseñar, refactorizar, implementar y mantener el código del framework en Java (Selenium, Cucumber, TestNG y Allure). Su objetivo es garantizar la mantenibilidad, escalabilidad, reusabilidad y valor educativo del framework.

---

## Principios Fundamentales y Estándares de Código

### 1. Arquitectura Page Object Model (POM) y Limpieza
- **Aislamiento de Locators:** Todos los localizadores de elementos web (`By` o `String` XPath) DEBEN ser privados y encapsulados exclusivamente dentro de su clase respectiva en `pages/` (ej: `PaginaPrincipal`).
- **Paginación Agnóstica:** Las clases Page Object deben modelar exclusivamente la estructura y el comportamiento de la interfaz de usuario. No deben importar librerías de prueba (Cucumber o TestNG) ni contener código de ejecución (anotaciones `@Test`, `@Given` ni aserciones de prueba directas).
- **Herencia de BasePage:** Toda nueva página del sistema debe heredar de `BasePage` y utilizar su constructor por defecto para recibir la instancia del WebDriver y WebDriverWait correspondientes de forma transparente.
- **Paso Limpio en Steps y Tests:** Los archivos Step Definition (`steps/`) y clases de prueba (`tests/`) interactúan con la aplicación EXCLUSIVAMENTE llamando a métodos de las clases Page Object. Jamás deben invocar `driver.findElement()` ni usar `By.*` directamente.

### 2. Soporte Híbrido e Independencia del Framework de Pruebas
- La infraestructura de WebDriver (`DriverManager`) y los Page Objects deben ser 100% reutilizables tanto en escenarios BDD con Cucumber como en pruebas unitarias/funcionales puras de TestNG.
- **Para Cucumber:** El ciclo de vida del driver por escenario se gestiona en `Hooks.java` (mediante anotaciones `@Before` y `@After`).
- **Para TestNG:** La inicialización y cierre del driver se gestiona heredando de `BaseTest.java` por cada método de prueba.

### 3. Gestión de WebDriver Segura para Hilos (Thread-Safety)
- **Instanciación Centralizada:** Toda obtención y gestión de `WebDriver` debe canalizarse exclusivamente a través de `DriverManager.getDriver()`, el cual implementa `ThreadLocal<WebDriver>`. Bajo ninguna circunstancia se debe instanciar directamente un `ChromeDriver` u otro navegador fuera del gestor.
- **Prohibido Drivers Estáticos:** NUNCA declarar el `WebDriver` como una variable estática global (`static`) mutable entre clases de ejecución o utilidades.
- **Navegadores Dinámicos:** Utilizar `WebDriverManager` para la descarga y configuración automática del driver compatible con el navegador instalado en el entorno (`config.properties`), evitando alojar binarios ejecutables `.exe` en disco.

### 4. Flexibilidad y Resiliencia en Localizadores y Esperas
- **Objetos `By` y Dinamismo:** Priorizar el uso de identificadores estables (`By.id()`, `By.name()`, `By.cssSelector()`) sobre XPaths absolutos o frágiles. Aprovechar los métodos sobrecargados de `BasePage` que aceptan tanto objetos `By` como selectores XPath en formato `String`.
- **Estrategia de Esperas Explícitas:** Usar siempre esperas explícitas mediante `wait` de `BasePage` (`WebDriverWait` y `ExpectedConditions`).
- **REGLA DE ORO:** Está estrictamente PROHIBIDO el uso de esperas fijas (`Thread.sleep()`). Si un elemento tarda en cargarse, usa las condiciones esperadas de Selenium.

### 5. Centralización de Configuraciones y Cero Hardcoding
- **Parámetros de Entorno:** URL base (`baseUrl`), navegador (`browser`) y tiempos de espera (`timeout`) deben configurarse centralizadamente en `src/test/resources/config.properties`.
- **Lector de Propiedades:** Utilizar siempre `ConfigReader.getProperty(clave)` para acceder a estos valores. No hardcodear URLs ni credenciales directamente en el código de automatización.
- **Datos de Prueba Externos:** Datos dinámicos, masivos o complejos deben ser administrados desde la carpeta `dataloader/` (`.csv`, `.xlsx`) y consumidos con librerías como OpenCSV o Apache POI.

### 6. Estándares de Codificación, Nomenclatura y Documentación (JavaDoc)
- **JavaDoc Obligatorio en Español:** Todos los métodos nuevos o modificados en las clases del framework (`BasePage`, `Pages`, `Utils`, `Steps`, `Tests`) deben contar con documentación JavaDoc detallada que explique:
  - Propósito general de la función.
  - Parámetros recibidos (`@param`).
  - Valor de retorno (`@return`), si aplica.
- **Convenciones de Nomenclatura:**
  - Clases Page Object: `Pagina[Nombre]` o `[Nombre]Page`.
  - Clases Step Definitions: `[Nombre]Steps` o `[Nombre]StepDefinitions`.
  - Clases de Prueba TestNG: `[Nombre]Test` (ubicadas en el paquete `tests`).
  - Clases de Utilidad: Nombres claros y específicos de propósito (`DriverManager`, `ConfigReader`, `Tools`).

### 7. Control del Ciclo de Vida y Reportes (Cucumber & TestNG)
- **Hooks de Cucumber:** Configurar `Hooks.java` (`@Before` y `@After`) para controlar el encendido/cierre de navegadores por escenario BDD.
- **Captura de Pantalla en Fallas:** En caso de fallas en pruebas UI, capturar la pantalla (`TakesScreenshot`) e incrustarla automáticamente tanto en el reporte HTML de Cucumber como en Allure Framework (`target/allure-results/`).
- **Aserciones Múltiples:** Para ejecuciones con múltiples comprobaciones visuales o validaciones en una misma pantalla, promover el uso de `SoftAssert` de TestNG ejecutando `assertAll()` al finalizar.

### 8. Enfoque Didáctico y Educativo
Este framework también cumple un **rol educativo** para enseñar y aprender automatización con Selenium. Por lo tanto:
- **Claridad sobre Complejidad:** Priorizar soluciones limpias y fáciles de comprender. Evitar la sobreingeniería o sintaxis oscura de Java si existe una alternativa más legible y didáctica.
- **Código Autoexplicativo:** El código debe expresar "qué" hace con claridad; si implementas un patrón avanzado o complejo, añade comentarios adicionales explicando el "por qué" se hace de esa manera (por ejemplo, el uso de `ThreadLocal` para concurrencia o `ExpectedConditions`).
- **Nuevos Manuales:** Cualquier concepto o herramienta avanzada que se introduzca en el framework debe acompañarse por un documento explicativo en el directorio `docs/`.

### 9. Respeto a Carpetas Locales y Experimentales
- Mantener la carpeta `src/test/java/varios` aislada como espacio experimental del usuario, no alterando sus utilidades a menos que se solicite explícitamente.

---

## Convenciones de Trazabilidad (Obligatorias)
Para mantener trazabilidad fuera del contexto efímero de la sesión:
- Al generar planes de implementación o walkthroughs, exportar siempre una copia al directorio `.agents/logs/`.
- Nomenclatura obligatoria: `YYYY-MM-DD_Nombre-Tarea_Plan.md` y `YYYY-MM-DD_Nombre-Tarea_Walkthrough.md`.
- Mantener actualizado el archivo `CONVERSATION_HISTORY.md` en `.agents/logs/` con cada decisión arquitectónica y resumen de sesión acordada con el usuario.
