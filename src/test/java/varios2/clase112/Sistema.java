package varios2.clase112;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Sistema {

    //   [ 111 ]             [222]
    //   [  pers1 ]        [ pers2 ]


    //  < 111 , pers1 >        < 222 , pers2 >
    //  < test@test.com , pers1 >        < ana@ana.com , pers2 >


    //  < 123 ,  empleado5001    >         < 333, empleado5004 >
    //  < empleado5001 , +59894233344>     < empleado5004, +54923423423423 >


    //lista de clase Sistema...
    private static List<Persona> listaPersonas = new ArrayList<>();
    private static List<String> listaNombres = new ArrayList<>();
    private static List<String> listaEmails = new ArrayList<>();
    private static HashMap<String, Persona> mapaPersonas = new HashMap<>();

    private static HashMap<String, Persona> mapaEmailsPersonas = new HashMap<>();

    public static void main(String args[]){

        //arreglo local al metodo main...
        String [] arregloPersonas = DataHelper.DATA.split(";");

        procesarDatosPersonales(arregloPersonas);

        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese un email");
        String anEmail = input.next();
        System.out.println("---> " + mapaEmailsPersonas.get(anEmail));

        imprimirPersonas();

        imprimirMapaPersonas();

    }

    private static void procesarDatosPersonales(String [] personas){
        for (int i = 0; i < personas.length; i++) {
            String [] datosPersona = personas[i].split(",");
            String doc = datosPersona[0];
            String nombre = datosPersona[1];
            //String ciudad = datosPersona[2];
            String pais = datosPersona[3];
            String email = datosPersona[4];

            Persona p = new Persona(nombre, doc, email, pais);

            listaPersonas.add(p);
            listaNombres.add(p.getNombre());
            listaEmails.add(p.getEmail());

            mapaPersonas.put(p.getDocumento(), p);
            mapaEmailsPersonas.put(p.getEmail(), p);

            System.out.println("Se ha agregado a la persona " + p.getNombre() );
        }
    }

    public static void imprimirPersonas(){
        System.out.println("******* PERSONAS ********");

        for (Persona p : listaPersonas){
            System.out.println(p);
        }
    }

    public static void imprimirMapaPersonas(){
        for (Persona p : mapaPersonas.values()){
            System.out.println(p);
        }
    }

}
