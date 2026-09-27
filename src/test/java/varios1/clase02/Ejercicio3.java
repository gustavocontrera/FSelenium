package varios1.clase02;

import java.util.Scanner;

public class Ejercicio3 {

    public static void main(String[] args) {
        int num1;
        int num2;
        int num3;
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese un numero");
        num1 = input.nextInt();
        System.out.println("Ingrese otro numero");
        num2 = input.nextInt();
        System.out.println("Ingrese el ultimo numero");
        num3 = input.nextInt();
        if (num3 > 0) {
            System.out.println("El ultimo valor ingresado es mayor a 0");
        } if (num3 < 0) {
            System.out.println("El ultimo valor ingresado es menor o igual a 0");
        } else {
            System.out.println("El ultimo valor ingresado es igual a 0");
        }

        int suma = num1 + num2 + num3;
        System.out.println("La suma total es " + suma);
    }

}
