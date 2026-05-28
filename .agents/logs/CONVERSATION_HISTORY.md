# Historial de Conversaciones y Decisiones de Diseño: FSeleniumIA

Este archivo sirve como bitácora y diario de ingeniería continuo para registrar los resúmenes, discusiones de chat, respuestas a consultas y decisiones de diseño acordadas entre el usuario y la IA (como QA Automation Senior). Cualquier conversación futura o de otros chats debe ser anexada al final de este archivo para mantener la trazabilidad completa del framework.

---

## Sesión: Creación y Refactorización del Framework FSeleniumIA
**Fecha:** 19 y 24 de Mayo de 2026  
**Objetivo:** Crear un nuevo framework híbrido independiente a partir de `FrameworkSelenium` aplicando estándares avanzados de QA Automation y configurando las habilidades de la IA.

### 1. Decisiones de Arquitectura y Refactorizaciones Clave
*   **Thread-Safety (Seguridad para Hilos):** 
    *   *Problema original:* El driver en `BasePage` se declaraba estático e inicializaba en un bloque estático global, impidiendo ejecuciones paralelas seguras y levantando el navegador de inmediato.
    *   *Solución:* Se creó `DriverManager.java` encapsulando `ThreadLocal<WebDriver>`. El driver se obtiene y se cierra por cada hilo de ejecución de manera aislada.
*   **Independencia del Motor de Pruebas (Soporte Híbrido):**
    *   *Decisión:* El framework debe soportar tanto Cucumber (BDD) como TestNG (pruebas tradicionales) usando los mismos Page Objects.
    *   *Implementación:*
        *   Para Cucumber, el ciclo de vida del driver por escenario se controla en `Hooks.java` (métodos `@Before` y `@After`).
        *   Para TestNG tradicional, se creó la clase padre `BaseTest.java` para el setup y teardown automático por test.
*   **Flexibilidad en BasePage:**
    *   *Decisión:* Permitir mayor dinamismo de selectores.
    *   *Implementación:* Se sobrecargaron los métodos de acción de `BasePage` para aceptar tanto selectores XPath tradicionales en formato `String` (retrocompatibilidad) como objetos `By` (`By.id()`, `By.cssSelector()`, etc.).
*   **Centralización de Propiedades:**
    *   Se creó `config.properties` y `ConfigReader.java` para eliminar cadenas hardcodeadas (como la URL de Free Range Testers y los tiempos de espera) dentro del código fuente.

---

### 2. Respuestas a Consultas del Chat y Aclaraciones Técnicas

#### ¿Por qué no se copiaron carpetas fuera de `src/` como `.idea/`?
*   **Explicación:** La carpeta `.idea/` contiene las configuraciones específicas del entorno de trabajo de IntelliJ. Al importar el nuevo proyecto `FSeleniumIA` en tu computadora mediante **File -> Open**, IntelliJ lee el archivo de dependencias de Maven (`pom.xml`) y genera de forma automática y limpia esta carpeta. Copiar la carpeta `.idea/` desde otros proyectos puede arrastrar rutas locales incorrectas y provocar fallas en los módulos.

#### ¿Qué pasó con la carpeta `drivers/` y el archivo `chromedriver.exe` manual?
*   **Explicación:** En desarrollos modernos se considera una mala práctica (anti-patrón) guardar ejecutables de drivers dentro del proyecto, ya que se desactualizan rápidamente con la versión del navegador instalada en la máquina. Se sustituyó por la librería **`WebDriverManager`** en `DriverManager.java`, que de forma automática descarga y actualiza la versión del driver correspondiente en la caché local del usuario al correr las pruebas.

#### ¿Para qué sirve la carpeta `dataloader/`?
*   **Explicación:** Contiene los archivos de datos de prueba (`.csv` y `.xlsx`) que utilizaba el framework anterior. Al copiarse a la raíz del nuevo proyecto, permite persistir los escenarios basados en datos (Data-Driven Testing) usando librerías como OpenCSV y Apache POI.

---

### 3. Integración de Allure Reports
*   **Decisión:** Habilitar la autogeneración de reportes gráficos elegantes al correr las pruebas.
*   **Implementación:** 
    *   Se agregaron las dependencias de Allure (Cucumber y TestNG) y la de AspectJ Weaver al `pom.xml`.
    *   Se configuró el plugin de Allure en `TestRunner.java` (`"io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"`).
    *   Al correr cualquier prueba, los reportes se crean en `target/allure-results/`.
    *   Se puede ver el reporte HTML interactivo ejecutando en consola: `allure serve target/allure-results`.

---
*(Fin de la sesión del 2026-05-24. Agregar nuevos resúmenes a continuación en futuras interacciones)*
