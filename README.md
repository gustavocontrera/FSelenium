# Manual de Automatización de Pruebas: FSeleniumIA

Este manual documenta las decisiones de diseño arquitectónico, el funcionamiento de los componentes y las guías de uso del framework híbrido **`FSeleniumIA`** (Selenium, Java, Cucumber y TestNG). 

> 🎓 **Propósito Educativo:** Este framework no solo está diseñado para la ejecución real de pruebas, sino también como una herramienta de aprendizaje. Hemos creado el directorio `docs/` en la raíz del proyecto, el cual contiene manuales detallados (como estrategias de localizadores) y tips explicativos para estudiar y comprender la arquitectura detrás de Selenium. Úsalo como tu guía de referencia técnica y didáctica.

---

## 1. Guía de Configuración e Importación en IntelliJ IDEA

### ¿Cómo abrir el proyecto por primera vez?
1. Abre **IntelliJ IDEA**.
2. Selecciona **File -> Open...** (o **Open** en el menú inicial).
3. Busca la ruta `d:\DRIVE\15-WORKSPACE - ESTUDIO - DATOS\IdeaProjects\FSeleniumIA` y haz clic en **OK**.
4. IntelliJ detectará el archivo descriptor de dependencias `pom.xml` y descargará de forma automática todas las librerías necesarias de Maven.

### Notas sobre directorios externos:
*   **`.idea/` (Configuración del IDE):** No necesitas copiar esta carpeta de otros proyectos. IntelliJ la genera automáticamente al abrir e importar el proyecto. Copiarla desde otros repositorios puede corromper las rutas relativas.
*   **`target/` y `out/` (Directorios de salida):** Se crean automáticamente tras la primera ejecución de código y contienen los compilados y reportes locales. Están excluidos de Git en el archivo `.gitignore`.

---

## 2. Gestión del ciclo de vida de WebDriver (Thread-Safe)

Para permitir que el framework ejecute pruebas concurrentes (ejecución paralela) de forma nativa en el futuro, hemos implementado una arquitectura desacoplada y libre de variables estáticas para los navegadores:

*   **`DriverManager.java`:** Utiliza `ThreadLocal<WebDriver>` para encapsular una instancia de navegador única para cada hilo de ejecución.
*   **Sin drivers en disco (`drivers/` descontinuada):** Ya no es necesario que guardes un ejecutable `chromedriver.exe` en tu repositorio. Hemos incorporado **`WebDriverManager`** en el gestor de drivers para descargar y configurar dinámicamente la versión compatible con el navegador instalado en la computadora del usuario.
*   **config.properties:** Centraliza el navegador deseado (`browser=chrome` o `firefox` o `edge`), la url del ambiente a testear (`baseUrl`) y las esperas en segundos (`timeout`).

---

## 3. Guía de Ejecución Híbrida

El framework es modular y te permite probar de dos maneras de forma coexistente:

### A. Ejecución BDD con Cucumber
- **Ubicación:** Los escenarios están descritos en lenguaje Gherkin en `src/test/resources/features/`.
- **Driver Lifecycle:** Controlado a nivel de escenario BDD por [Hooks.java](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/src/test/java/steps/Hooks.java). Abre el navegador en `@Before` y lo cierra en `@After`. Si el caso falla, automáticamente guarda una captura de pantalla y la adjunta al reporte HTML de Cucumber.
- **Runner:** Abre [TestRunner.java](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/src/test/java/runner/TestRunner.java) y haz clic en el botón de Play en IntelliJ.

### B. Ejecución tradicional con TestNG (Sin Cucumber)
- **Ubicación:** Las clases de prueba se ubican en `src/test/java/tests/`.
- **Driver Lifecycle:** Gestionado heredando de [BaseTest.java](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/src/test/java/tests/BaseTest.java). Abre y cierra el navegador por cada método `@Test`.
- **Runner:** Abre directamente tu clase de prueba (ej: [FreeRangeTest.java](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/src/test/java/tests/FreeRangeTest.java)) y presiona Play en la prueba de TestNG.

---

## 4. Gestión de Datos de Prueba (Dataloader)

El directorio `dataloader/` en la raíz contiene archivos estructurados con datos destinados a alimentar pruebas dinámicas:
- `email.csv`: Ejemplo de datos delimitados por punto y coma.
- `username-password-recovery-code.csv`: Datos de acceso y recuperación de usuarios.
- `sample.xlsx`: Planilla de Excel para flujos de datos complejos.

*Nota de Uso:* Puedes leer estos archivos usando librerías incluidas como **OpenCSV** y **Apache POI** desde tus clases en `utils` o directamente en las aserciones de tus pruebas.

---

## 5. Reportes Gráficos con Allure (Implementado)

Hemos integrado **Allure Framework** para brindarte reportes visuales de nivel empresarial, con soporte tanto para Cucumber como para TestNG.

### Generación automática de resultados:
Cada vez que corras las pruebas desde el `TestRunner` de Cucumber o ejecutes las pruebas de TestNG desde el IDE, Allure creará una carpeta llamada **`allure-results`** en el directorio `target/`.

### ¿Cómo ver el reporte gráfico interactivo?
Para ver los reportes consolidados en un panel HTML local en tu navegador:
1. Instala Allure Command Line en tu sistema (ej: mediante `npm install -g allure-commandline` o descargándolo de su sitio oficial).
2. Abre la terminal de tu sistema operativo en la carpeta raíz del proyecto `FSeleniumIA`.
3. Ejecuta el siguiente comando para procesar y levantar el servidor web con el reporte:
   ```bash
   allure serve target/allure-results
   ```
4. Se abrirá automáticamente una ventana en tu navegador por defecto con el panel interactivo de Allure detallando los pasos ejecutados, tiempos de carga y capturas de pantalla de los errores.
