package varios1.clase02;

import java.util.Scanner;

public class EjercicioForYWhile {

    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int num = 0;
        int contador = 0;
        int suma = 0;

        for (int i = 0; i <= 3; i = i + 1){
            System.out.println("Ingrese un numero: ");
            num = input.nextInt();
        }

        while (num >= 0) {
            System.out.println("Ingrese un numero: ");
            num = input.nextInt();
            if (num >= 0) {
                suma = suma + num;
                contador++;
            }
        }

        System.out.println("Ud. ingresó " + contador + " numeros!!");
        System.out.println("La suma es " + suma);

    }

}
