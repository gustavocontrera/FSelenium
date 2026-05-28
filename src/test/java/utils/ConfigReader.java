package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Lector de configuración para el framework de automatización.
 * Carga las propiedades del archivo config.properties de manera centralizada.
 * 
 * @author QA Automation Senior
 */
public class ConfigReader {

    // Instancia de Properties de Java para almacenar las propiedades cargadas
    private static Properties properties;

    // Bloque estático para cargar las propiedades al cargar la clase en memoria
    static {
        try {
            // Ruta relativa al archivo de configuraciones
            String filePath = "src/test/resources/config.properties";
            
            // Abrimos un stream de lectura para leer el archivo de configuración
            FileInputStream input = new FileInputStream(filePath);
            
            // Inicializamos la instancia de Properties
            properties = new Properties();
            
            // Cargamos las propiedades en memoria
            properties.load(input);
            
            // Cerramos el stream de entrada
            input.close();
        } catch (IOException e) {
            System.err.println("ERROR: No se pudo cargar el archivo de configuración 'config.properties'.");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Obtiene el valor de una propiedad a partir de su clave.
     * 
     * @param key Clave de la propiedad a buscar (ej: "baseUrl", "browser").
     * @return El valor de la propiedad como cadena de texto (String), o null si no se encuentra.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
