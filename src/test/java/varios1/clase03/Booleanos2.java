package varios1.clase03;

//ejercicio 15 Crear un metodo llamado esMayorDeEdad que retorne verdadero
// si y sólo si, el valor recibido por parámetro es mayor a 18.

import java.util.Scanner;

public class Booleanos2 {

    public static void main (String args[])
    {
        boolean esMayorDeEdad = false;
        Scanner input = new Scanner(System.in);

        System.out.println("Ingrese su edad");
        int edad = input.nextInt();
        esMayorDeEdad = esMayor(edad);
        mostrarSiLaPersonaEsMayorDeEdad(esMayorDeEdad);
    }

    public static boolean esMayor(int unaEdad){
        if (unaEdad >= 18) {
            return true;
        } else {
            return false;
        }
    }

    public static void mostrarSiLaPersonaEsMayorDeEdad(boolean esMayor){
        if (esMayor == true) {
            System.out.println("La persona es mayor de edad!!");
        } else {
            System.out.println("La persona es menor de edad!!");
        }

    }


}
