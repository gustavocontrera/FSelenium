package varios1.clase02;

import java.util.Scanner;

public class EjercicioForYWhile2 {

    public static void main(String args[]){
        int num = -1;
        int suma = 0;
        int contador = 0;

        Scanner input = new Scanner(System.in);

        System.out.println("Ingrese numeros hasta ingresar un 0");

        while (num != 0) {
            System.out.println("Ingrese un numero");
            num = input.nextInt();

            if (num != 0) {
                suma = suma + num;
                contador = contador + 1;
            }
        }

        System.out.println("La suma total es " + suma );
        System.out.println("Cantidad de numeros ingresados: " + contador);
        System.out.println("El promedio es " + suma / contador);
        System.out.println("El programa ha finalizado!!");

        if (num > 10 || num < 7){
            System.out.println("El numero es mayor a 10 o menor a 7");
        } else {
            System.out.println("El numero esta entre 7 y 10 ");
        }

        for (int i = 0; i < 14 ; i = i + 1) {
            System.out.println("El valor de i es " + i);
        }
    }

}
