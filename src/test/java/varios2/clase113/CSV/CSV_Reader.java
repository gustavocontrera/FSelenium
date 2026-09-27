package varios2.clase113.CSV;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSV_Reader {

    public static void main(String args[]) throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("dataloader/email.csv"));

        String[] arregloPersonas;

        List<Persona> listaPersonas = new ArrayList<>();

        while ((arregloPersonas = reader.readNext()) != null){
            for (int i = 0; i < arregloPersonas.length; i++){
                String persona = arregloPersonas[i];

                //separo los datos de la persona
                String [] datosPersona = persona.split(";");
                String email = datosPersona[0];
                String firstName = datosPersona[2];
                String lastName = datosPersona[3];

                //String fullName = firstName.concat(" ").concat(lastName);

                //Creo objeto pers del tipo Persona (le mando cada persona para que se agregue a la clase)
                Persona pers = new Persona(firstName, lastName, email);
                //System.out.println("----> " + pers);

                //Agrego las personas a la lista
                listaPersonas.add(pers);

            }
        }
        //Muestro todas las personas por consola
        listaPersonas.remove(0); //Remuevo la primera fila que son los títulos de la columna
        for (Persona p : listaPersonas){
            System.out.println("===> " + p);
        }
    }
}
