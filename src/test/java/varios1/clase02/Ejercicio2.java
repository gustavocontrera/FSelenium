package varios1.clase02;

import java.util.Scanner;

public class Ejercicio2 {

    public static void main(String[] args) {
        String nombre;
        int fechaNac;
        int edad;
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese su nombre");
        nombre = input.next();
        System.out.println("Ingrese su año de nacimiento");
        fechaNac = input.nextInt();
        edad = 2025 - fechaNac;
        System.out.println("Su nombre es " + nombre + " y su edad es " + edad);
    }

}