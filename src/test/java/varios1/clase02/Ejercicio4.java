package varios1.clase02;

import java.util.Scanner;

public class Ejercicio4 {
    public static void main(String[] args) {
        int num1 = 0;
        int num2 = 0;
        int num3 = 0;

        Scanner input = new Scanner(System.in);

        System.out.println("Ingrese un numero");
        num1 = input.nextInt();
        System.out.println("Ingrese otro numero");
        num2 = input.nextInt();
        System.out.println("Ingrese el ultimo numero");
        num3 = input.nextInt();

        if (num1 == num2 || num1 == num3 || num2 == num3  ) {
            System.out.println("Hay Repetidos");
        }
        else {
            System.out.println("No Hay Repetidos");
        }

        if (num1 != num2 && num1 != num3 && num2 != num3) {
            System.out.println("Todos son diferentes");
        } else {
            System.out.println("Hay números repetidos");
        }

        System.out.println("El programa ha finalizado...");


    }
}
