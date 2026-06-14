# Guía de Generación Automática de Reportes Allure

Esta guía documenta paso a paso cómo se implementó la autogeneración del reporte HTML de Allure para que funcione automáticamente después de cada ejecución de pruebas desde el `TestRunner`, organizándolo de manera limpia dentro de la carpeta `test-output`.

## 1. El Problema Original
Por defecto, la integración de Allure con Cucumber y JUnit (`io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm`) **únicamente** produce resultados crudos (archivos `.json` y `.txt`) en la carpeta `allure-results` en la raíz del proyecto. Estos archivos no son legibles por humanos y no incluyen la interfaz gráfica, lo cual ensucia el directorio raíz del repositorio si no se configura de forma aislada.

El objetivo fue:
- Centralizar todos los archivos generados en una ubicación ordenada (`test-output/`).
- Evitar que estos archivos basura temporales se suban al repositorio Git.
- Automatizar la compilación a reporte HTML interactivo directamente desde el entorno de ejecución de Java (Runner).

## 2. Requisitos Previos (Prerrequisitos)
La solución implementada aprovecha el ecosistema de **Node.js**.
- **Node.js y `npx`**: Deben estar instalados en el sistema y disponibles en la línea de comandos. Se utilizó `npx` (Node Package Execute) porque permite ejecutar el CLI de Allure directamente desde los repositorios de npm de manera temporal, **sin necesidad de descargar e instalar Allure de forma global** en Windows.

## 3. Redirección y Estructura de Salida
Para evitar ensuciar la raíz del proyecto, decidimos almacenar tanto los datos crudos como el HTML compilado dentro de la carpeta `test-output/`.

### A. Crear archivo `allure.properties`
Creamos el archivo `allure.properties` en la carpeta de recursos de prueba (`src/test/resources/allure.properties`) con la propiedad encargada de desviar los resultados crudos:
```properties
allure.results.directory=test-output/allure-results
```

### B. Configuración de Exclusiones en `.gitignore`
Para asegurarnos de que nada de esto se suba a Git, agregamos exclusiones al archivo `.gitignore`:
```gitignore
# Driver and report folder outputs
test-output/

# Allure root folders (just in case they are generated at the root)
allure-results/
allure-report/
```

*(Nota: Como todo se guarda ahora dentro de `test-output/`, la regla general `test-output/` es suficiente para ignorar tanto los resultados como los reportes).*

## 4. Implementation en `TestRunner.java`
La solución de automatización se basó en inyectar un método de "teardown" global utilizando la anotación `@AfterClass` de JUnit 4. Esto garantiza que el código se ejecute exactamente **una vez**, justo después de que todos los escenarios de Cucumber hayan finalizado.

### Código Añadido:
Se añadieron los imports necesarios y el bloque de ejecución de consola dentro de la clase `TestRunner`:

```java
import org.junit.AfterClass;
import java.io.IOException;

// ... (código existente del Runner)

public class TestRunner {
    
    @AfterClass
    public static void generateReport() {
        try {
            System.out.println("Generando reporte HTML de Allure...");
            // Ejecución silenciosa del comando npx apuntando a las nuevas rutas en test-output/
            ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", 
                "npx -y allure-commandline generate test-output/allure-results --clean -o test-output/allure-report"
            );
            builder.redirectErrorStream(true);
            Process process = builder.start();
            process.waitFor();
            System.out.println("Reporte de Allure generado con éxito en la carpeta 'test-output/allure-report'.");
        } catch (IOException | InterruptedException e) {
            System.err.println("Error al generar el reporte de Allure: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

### Explicación del Comando de Consola:
- `cmd.exe /c`: Invoca la terminal de Windows para que ejecute el siguiente comando y luego termine.
- `npx -y`: Llama a la herramienta de ejecución de paquetes Node de manera automática.
- `allure-commandline generate test-output/allure-results`: Comando oficial de Allure para transformar los resultados crudos de nuestra nueva ruta.
- `--clean -o test-output/allure-report`: Limpia reportes anteriores y guarda el nuevo reporte HTML en `test-output/allure-report`.

## 5. Cómo Usarlo
1. Ejecuta tu suite de pruebas desde el IDE haciendo clic derecho en `TestRunner.java` > **Run 'TestRunner'**.
2. Al finalizar, verás en la consola el texto *"Generando reporte HTML de Allure..."*.
3. Refresca la estructura del proyecto en tu IDE (Click derecho > Reload from Disk).
4. Ve a la carpeta `test-output/allure-report/` en tu proyecto.
5. Abre el archivo `index.html` en tu navegador favorito para visualizar los reportes gráficos de tu ejecución.
