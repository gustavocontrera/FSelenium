package varios1.ejerciciosVarios;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ManejoExcepciones {

    public static void main (String args[]){

        System.out.println("Bienvenido!!");

        Scanner input = new Scanner(System.in);

        int [] arregloVentas = new int[4];
        System.out.println("===========");
        arregloVentas[0] = 4;
        arregloVentas[1] = 2;
        arregloVentas[2] = 3;
        arregloVentas[3] = 7;

        System.out.println("Ingrese la posicion a consultar: ");
        try {
            int pos = input.nextInt();
            System.out.println("----> " + arregloVentas[pos]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("La ultima posicion vale: " + arregloVentas[arregloVentas.length - 1]);
        } catch (Exception ex){
            System.out.println("Ha ocurrido una excepcion, consulta a un admin");
        }

        try {
            System.out.println("Ingrese su nombre: ");
            String nombre = input.next();
            System.out.println("Hola, " + nombre);
            // int          4
            // String      "4"

            System.out.println("Ingrese el dividendo");
            int dividendo = input.nextInt();
            System.out.println("Ingrese el divisor");
            int divisor = input.nextInt();

            int division = dividendo / divisor;

            System.out.println("El resultado de la division es " + division);
        } catch (ArithmeticException ex) {
            System.out.println("No se puede hacer este calculo");
        } catch (InputMismatchException ex){
            System.out.println("Error, Ud. no ingreso un numero!!");
        } catch (Exception ex){
            System.out.println("Ha ocurrido un error, contacte a su administrador.");
        }

        System.out.println("Hasta luego!!");

    }

}