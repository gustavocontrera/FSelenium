package varios1.clase03;

import java.util.Scanner;

public class Booleanos1 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        System.out.println("Ingrese su edad");
        int edad = input.nextInt();

        if (edad >= 18) {
            System.out.println("Ud. es mayor de edad!!");
        } else {
            System.out.println("Ud. es menor de edad!!");
        }

        System.out.println("Ingrese su calificacion de 0 a 100");
        int calificacion = input.nextInt();

        if (calificacion <= 70) {
            System.out.println("Debe rendir el examen nuevamente");
        } else {
            System.out.println("Felicitaciones! Ha aprobado el curso!!");
        }

    }
}