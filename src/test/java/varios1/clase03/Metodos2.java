package varios1.clase03;

import java.util.Scanner;

public class Metodos2 {

    public static void main(String[] args) {
        System.out.println("Bienvenidos al Programa!!");

        Scanner input = new Scanner(System.in);

        System.out.println("Ingrese un numero");
        int numeroIngresado = input.nextInt();

        esMayorA20(numeroIngresado);
        int doble = obtenerDoble(numeroIngresado);
        System.out.println("El doble es " + doble);
        
        System.out.println("Ingrese otro numero:");
        int numeroIngresado2 = input.nextInt();

        int multi = multiplicar(numeroIngresado, numeroIngresado2);
        System.out.println("La multiplicacion de ambos numeros ingresados es " + multi);

        int valorAbsMultiplicacion = valorAbsoluto(multi);
        System.out.println("El valor absoluto de la multiplicacion es " + valorAbsMultiplicacion);

        System.out.println("Hasta pronto!!");
    }

    public static int obtenerDoble(int num) {
        return num * 2;
    }

    public static void esMayorA20(int numero) {
        if (numero > 20) {
            System.out.println("El numero es mayor a 20!!");
        } else {
            System.out.println("El numero es menor a 20!!!");
        }

    }

    public static int multiplicar(int num1, int num2) {
        return num1 * num2;
    }

    /*public static int obtenerMayor(int numero1, int numero2) {
        return numero1 > numero2 ? numero1 : numero2;
    }*/

    /*public static boolean esMayorACero(int num) {
        return num > 0;
    }*/

    public static int valorAbsoluto(int num) {
        if (num < 0) {
            num = num * -1;
        }
        return (num);
    }
}