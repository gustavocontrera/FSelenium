package varios2.clase112;

import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class StringsStandalone {

    public static void main(String args[]){

        String nombre1 = "Juan";
        String nombre2 = "Maria";
        String nombre3 = "Ana";

        String nombres = nombre1.concat(" ").concat(nombre2).concat(nombre3);

        System.out.println(nombres);

        String nombre = "Juan";
        String apellido1 = "Rodriguez";
        String apellido2 = "Gonzalez";

        String nombreCompleto = nombre.concat(" ").concat(apellido1);

        List listaEmails = new ArrayList<>();
        listaEmails.add("testing@test.com");
        listaEmails.add("testing@test.com");
        listaEmails.add("testing@testcom");
        validadorEmail(listaEmails);

        System.out.println("FIN");
    }

    public static void validadorEmail(List<String> listaEmails){
        for (String email : listaEmails) {
            if (email.endsWith(".com") == false){
                Assert.assertTrue(false, "Error: se encontró un email que no termina en .com");
            }

            if (email.contains("@") == false) {
                Assert.assertTrue(false, "Error: se encontró un email que no contiene @");
            }
        }

//        String email = "asdfasd";
//        if (email.contains("@") == false){
//            Assert.assertTrue(false, "Error: se encontró un email que no contiene un @");
//        }
    }

    //ABC-1234
    public static void validadorCredenciales(List<String> listaCredenciales){
        for (String credencial : listaCredenciales) {
            if (credencial.indexOf("-") != 3){
                Assert.assertTrue(false, "Error: formato invalido");
            }
        }
    }
}
