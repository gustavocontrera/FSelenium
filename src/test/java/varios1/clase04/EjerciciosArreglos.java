package varios1.clase04;

import java.util.Scanner;

public class EjerciciosArreglos {

    public static void main(String[] args ){
        Scanner input = new Scanner(System.in);

        int [] arregloNumeros = new int[10];


        for (int i = 0; i < arregloNumeros.length; i++){
            System.out.println("Ingrese un numero: ");
            int num = input.nextInt();
            arregloNumeros[i] = num;
        }

        System.out.println("*** VALORES DEL ARREGLO ****");
        for (int i = 0; i < arregloNumeros.length; i++){
            System.out.println("pos " + i + " ==> " + arregloNumeros[i]);
        }

        boolean [] asientosLibres = new boolean[3];
        //asientosLibres[1] = true;
        //   [ false ]      [ true ]      [ false ]
        //    pos 0          pos 1          pos 2

        for (int i = 0; i < asientosLibres.length; i++){
            System.out.println("******** " + asientosLibres[i]);
        }
        boolean hayLugar = hayLugarDisponible(asientosLibres);

        if (hayLugar == true){
            System.out.println("Hay lugar en el helicoptero");
        } else {
            System.out.println("No hay asientos disponibles");
        }

        String [] diasDeLaSemana = new String[7];
        diasDeLaSemana[0]= "Lunes";
        diasDeLaSemana[1]= "Martes";
        diasDeLaSemana[2]= "Miercoles";
        diasDeLaSemana[3]= "Jueves";
        diasDeLaSemana[4]= "Viernes";
        diasDeLaSemana[5]= "Sabado";
        diasDeLaSemana[6]= "Domingo";

        int ventaTotal = 0;

        int [] ventasSemanales = new int [7];
        for (int i = 0; i < ventasSemanales.length; i++){
            System.out.println("Ingrese las ventas del dia " + diasDeLaSemana[i]);
            int num = input.nextInt();
            ventasSemanales[i] = num;
        }

        for (int i = 0; i < ventasSemanales.length; i++) {
            System.out.println("El dia " + diasDeLaSemana[i] + " se vendió $" + ventasSemanales[i] );
        }

        for (int i = 0; i < ventasSemanales.length; i++){
            ventaTotal = ventaTotal + ventasSemanales [i];
        }

        System.out.println("El total de ventas de la semana es " + ventaTotal);

        System.out.println("El promedio diario de ventas fue de " + ventaTotal / ventasSemanales.length);
    }

    public static boolean hayLugarDisponible(boolean [] asientosLibres){

        for (int i = 0; i < asientosLibres.length; i++){
            if (asientosLibres[i] == true){
                return true;
            }
        }
        return false;

    }

}