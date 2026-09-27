package varios1.clase04;

import java.util.Scanner;

public class Arreglos2 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);


        int[] ventaSemana = new int[7];
        for(int i = 0; i < ventaSemana.length; i++) {
            System.out.println("Ingrese lo vendido el dia " + i);

            ventaSemana[i] = input.nextInt();
        }

        for(int i = 0; i < ventaSemana.length; i++) {
            System.out.println("El dia " + i + " se vendió $" + ventaSemana[i]);
        }

        int[] horasTrabajadas = new int[5];
        for(int j = 0; j < horasTrabajadas.length; j++) {
            System.out.println("Ingrese las horas que trabajó el dia " + j);
            horasTrabajadas[j] = input.nextInt();
        }

        for(int j = 0; j < horasTrabajadas.length; j++) {
            System.out.println("Se trabajó en el dia " + j + " " + horasTrabajadas[j] + " horas");
        }

    }
}