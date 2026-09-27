package varios2.clase109;

import java.util.HashMap;
import java.util.Scanner;

public class EjemploMapas {

// Arreglos --> [ 4 ] [ 5 ] [ 2 ] [ 1 ]
// Listas --> Juan -> Maria -> Ana -> Andres

//**** MAPAS ******
//Estructura de almacenamiento que permite almacenar clave-valor
/*
<111, Persona>
<123, Juan Perez>
<222, Juan Andres>
*/

//ORDEN = tiempo que demora en encontrar un elemento....
// LISTA de N elementos --> el tiempo en encontrar a un elemento puede ser N
// MAPA de N elementos --> el tiempo en encontrar a un elemento es 1

    public static void main(String args[]){
        HashMap<Integer, String> mapaNombres = new HashMap<>();
        mapaNombres.put(111, "Analia Lopez");
        mapaNombres.put(222, "Juan Rodriguez");
        mapaNombres.put(132, "Joaquin Requena");

        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese un documento:");
        int documento = input.nextInt();

        String nombreEncontrado = mapaNombres.get(documento);

        if (nombreEncontrado == null){
            System.out.println("La persona no esta en el sistema");
        } else {
            System.out.println("La persona buscada es: " + nombreEncontrado);
        }
    }
}
