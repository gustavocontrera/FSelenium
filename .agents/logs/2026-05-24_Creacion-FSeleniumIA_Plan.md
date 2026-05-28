# Plan de Implementación: Creación del Framework FSeleniumIA (QA Automation Senior)

Este plan describe la estrategia para crear un nuevo proyecto separado llamado `FSeleniumIA` a partir de `FrameworkSelenium`, implementando estándares de QA Automation Senior (Thread-safety, configuraciones centralizadas, mejores prácticas de Page Object Model, Cucumber Hooks y control del ciclo de vida del WebDriver). También configuraremos la estructura de "Skills" del agente en el nuevo proyecto.

## User Review Required

> [!IMPORTANT]
> **Creación de Nuevo Proyecto:**
> Se creará la estructura completa en la ruta `d:\DRIVE\15-WORKSPACE - ESTUDIO - DATOS\IdeaProjects\FSeleniumIA`.
>
> **Directorio de Agentes y Skills:**
> Crearemos el directorio `.agents/skills/qa-standards/` y `.agents/logs/` dentro de `FSeleniumIA`.
>
> **Trazabilidad:**
> Guardaremos una copia de este plan y del walkthrough final en `d:\DRIVE\15-WORKSPACE - ESTUDIO - DATOS\IdeaProjects\FSeleniumIA\.agents/logs/` como `2026-05-24_Creacion-FSeleniumIA_Plan.md` y `2026-05-24_Creacion-FSeleniumIA_Walkthrough.md`.

---

## Análisis y Estructura Propuesta para FSeleniumIA

Para lograr un framework altamente escalable, reutilizable y profesional, implementaremos los siguientes cambios arquitectónicos al migrar y refactorear las clases desde `FrameworkSelenium`:

### 1. Documentación Explicativa de Métodos (Nueva Regla)
- **Propuesta:** Todos y cada uno de los métodos que se creen en este nuevo framework (`BasePage`, Utils, Steps, Tests, etc.) estarán debidamente comentados utilizando JavaDoc o comentarios claros de bloque. Esto asegurará que cualquier miembro del equipo (o una IA asistente) entienda exactamente el propósito, los parámetros esperados y el valor de retorno de cada función.

### 2. Soporte Híbrido (Con o Sin Cucumber)
- **Propuesta:** El framework estará diseñado para ser independiente del motor de pruebas. Esto significa que las herramientas, la gestión del WebDriver (`DriverManager`) y los Page Objects podrán utilizarse tanto en escenarios BDD (usando archivos `.feature` de Cucumber) como en scripts de pruebas puros (usando clases y anotaciones `@Test` de TestNG o JUnit) sin ninguna dependencia directa que los obligue a trabajar con Cucumber de forma exclusiva.

### 3. Gestión del WebDriver Segura para Hilos (Thread-Safe)
- **Antes (`FrameworkSelenium`):** En `BasePage.java`, el `driver` es `static` y se levanta en un bloque de inicialización estático. No permite ejecuciones paralelas y el navegador se abre tan pronto como se carga la clase.
- **Ahora (`FSeleniumIA`):** Crearemos una clase `DriverManager.java` en `utils` que utilizará `ThreadLocal<WebDriver>`. Esto asegura que si en el futuro decides correr pruebas en paralelo (con TestNG o Maven Surefire), cada hilo tendrá su propio navegador aislado.

### 4. Control de Ciclo de Vida por Escenario (Hooks y BaseTest)
- **Antes (`FrameworkSelenium`):** El navegador se inicia estáticamente al inicio de la carga del framework y se cierra únicamente al final de la suite en el `TestRunner` de JUnit.
- **Ahora (`FSeleniumIA`):** 
  - Para **Cucumber**: Configurar `Hooks.java` para inicializar el `driver` en un método `@Before` y cerrarlo de forma segura (`driver.quit()`) en un método `@After`.
  - Para **TestNG/JUnit**: Crearemos una clase `BaseTest.java` (o equivalente) de la que heredarán los scripts de prueba clásicos, encargándose de inicializar y cerrar el navegador por cada test utilizando el mismo `DriverManager`.

### 5. Configuraciones Centralizadas
- **Propuesta:** Crear un archivo `src/test/resources/config.properties` para centralizar la URL base (`baseUrl`), navegador por defecto (`browser`), y tiempos de espera (`timeout`).
- Crearemos un `ConfigReader.java` en `utils` para leer estos valores dinámicamente, eliminando URLs hardcodeadas en las clases Java.

### 6. Flexibilidad en BasePage
- **Antes (`FrameworkSelenium`):** Todos los métodos aceptan un `String locator` y usan estrictamente XPath (`By.xpath(locator)`).
- **Ahora (`FSeleniumIA`):** Rediseñaremos `BasePage` para trabajar directamente con objetos `By`. Esto permitirá usar selectores por ID, CSS, Nombre, etc., además de XPath, ofreciendo flexibilidad absoluta a las páginas hijas.

### 7. Estructura de Paquetes Correcta
- Separaremos las clases de manera coherente: `pages` para modelado web, `steps` para mapeo de Cucumber, y `tests` para las clases de ejecución directa en TestNG.

---

## Proposed Changes

### [NEW] Proyecto FSeleniumIA

Crearemos la estructura de directorios estándar de Maven:
`d:\DRIVE\15-WORKSPACE - ESTUDIO - DATOS\IdeaProjects\FSeleniumIA`
├── `pom.xml` (Dependencias actualizadas para Cucumber y TestNG independiente)
└── `src/test`
    ├── `java`
    │   ├── `pages` (Clases de Page Object Model agnósticas a Cucumber)
    │   ├── `runner` (Clase TestRunner para Cucumber)
    │   ├── `steps` (Cucumber Step Definitions y Hooks)
    │   ├── `tests` (Tests independientes usando TestNG, ej: `FreeRangeTest.java` y `BaseTest.java`)
    │   └── `utils` (DriverManager, ConfigReader, Tools)
    └── `resources`
        ├── `features` (Archivos de escenarios .feature)
        └── `config.properties` (Parámetros globales)

#### [NEW] [pom.xml](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/pom.xml)
Configuración de Maven asegurando la compatibilidad de Java y dependencias tanto de Cucumber como TestNG clásico.

#### [NEW] [config.properties](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/src/test/resources/config.properties)
Archivo de configuración clave-valor.

#### [NEW] [ConfigReader.java](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/src/test/java/utils/ConfigReader.java)
Lector centralizado de propiedades. (Con comentarios explicativos).

#### [NEW] [DriverManager.java](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/src/test/java/utils/DriverManager.java)
Implementación Thread-Safe de WebDriver usando `ThreadLocal`. (Con comentarios explicativos).

#### [NEW] [BasePage.java](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/src/test/java/pages/BasePage.java)
Refactorización de la clase base con soporte para `By` y agnóstica al test runner. (Totalmente comentada).

#### [NEW] [BaseTest.java](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/src/test/java/tests/BaseTest.java)
Clase padre para tests tradicionales sin Cucumber, encargada de la inyección del driver por test.

#### [NEW] [Paginas, Steps y Tests](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/src/test/java/)
Migración y adaptación del código existente.

#### [NEW] [SKILL.md](file:///d:/DRIVE/15-WORKSPACE%20-%20ESTUDIO%20-%20DATOS/IdeaProjects/FSeleniumIA/.agents/skills/qa-standards/SKILL.md)
Especificación de reglas y estándares de QA para el nuevo framework.

---

## Verification Plan

### Automated Tests
1. **Ejecución vía Cucumber**: Asegurar que `TestRunner` funcione correctamente y genere el reporte.
2. **Ejecución vía TestNG (Híbrido)**: Correr `FreeRangeTest` de manera directa como test de TestNG para confirmar que la infraestructura no dependa exclusivamente de Cucumber.

### Manual Verification
1. Comprobar la creación de las carpetas `.agents/skills/qa-standards/` y `.agents/logs/`.
2. Validar que la copia de este plan de implementación exista en la bitácora del nuevo proyecto.
3. Verificar la exhaustividad de los comentarios en todos los métodos de las clases generadas.
