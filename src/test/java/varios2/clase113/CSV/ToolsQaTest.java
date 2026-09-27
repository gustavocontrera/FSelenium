package varios2.clase113.CSV;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ToolsQaTest {

    public WebDriver driver;
    private static List<Persona> LISTA_PERSONAS = new ArrayList<>();
    private static int CONTADOR = 0;

    //crear una clase X con los datos que necesito
    //leer el archivo .csv
    //creo un objeto por cada fila del archivo
    //lo almaceno en una lista estatica
    //creo un test que tome los datos del objeto mediante getters
    //completo el formulario

    @BeforeTest
    public void loadDataFromFile() throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("dataloader/email.csv"));
        String[] arregloPersonas;

        while ((arregloPersonas = reader.readNext()) != null){
            for (int i = 0; i < arregloPersonas.length; i++){
                String persona = arregloPersonas[i];

                //separo los datos de la persona
                String [] datosPersona = persona.split(";");
                String email = datosPersona[0];
                String firstName = datosPersona[2];
                String lastName = datosPersona[3];

                //String fullName = firstName.concat(" ").concat(lastName);

                Persona pers = new Persona(firstName, lastName, email);
                //System.out.println("----> " + pers);

                LISTA_PERSONAS.add(pers);

            }
        }

        LISTA_PERSONAS.remove(0);
        for (Persona p : LISTA_PERSONAS){
            System.out.println("===> " + p);
        }
    }

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.toolsqa.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();
    }

    @Test(invocationCount = 4)
    public void registrationTest(){
        driver.navigate().to("https://www.toolsqa.com/selenium-training/#enroll-form");
        driver.manage().window().fullscreen();
        Persona p = LISTA_PERSONAS.get(CONTADOR);
        driver.findElement(By.name("firstName")).sendKeys(p.getNombre());
        driver.findElement(By.name("lastName")).sendKeys(p.getApellido());
        driver.findElement(By.name("email")).sendKeys(p.getEmail());

        CONTADOR++;
    }

}
