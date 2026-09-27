package varios1.ejercicioPersona;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utilities {

    public static int solicitarNumero(String mensaje){
        Scanner input = new Scanner(System.in);
        System.out.println(mensaje);
        int numero = 0;
        try {
            numero = input.nextInt();
        } catch (InputMismatchException ex){
            System.out.println("Se esperaba un numero!!");
        } catch ( Exception ex) {
            System.out.println("Ha ocurrido un error, se ha asignado el 0");
        }

        return numero;
    }

    public static String solicitarTexto(String mensaje) {
        Scanner input = new Scanner(System.in);
        System.out.println(mensaje);
        String texto = input.next();
        return texto;
    }


}
