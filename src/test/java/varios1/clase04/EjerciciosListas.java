package varios1.clase04;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EjerciciosListas {

    public static void main(String args[]){

        List<String> listaNombres = new ArrayList<String>();

        if (listaNombres.isEmpty() == true){
            System.out.println("La lista esta vacia");
        } else {
            System.out.println("La lista tiene elementos");
        }

        listaNombres.add("Juan");
        listaNombres.add("Maria");
        listaNombres.add("Ana");
        System.out.println("Agregando elementos....");

        if (listaNombres.isEmpty() == true){
            System.out.println("La lista esta vacia");
        } else {
            System.out.println("La lista tiene elementos");
        }

        Scanner input = new Scanner(System.in);
        for (int i = 0; i < 2; i++){
            System.out.println("Ingrese un nombre");
            String nombre = input.next();
            listaNombres.add(nombre);
        }

//        for (int i = 0; i < listaNombres.size(); i++){
//            System.out.println("--> "+listaNombres.get(i));
//        }

        //for each
        for (String nombre: listaNombres) {
            System.out.println("Nombre: " + nombre);
        }

        System.out.println("Ingrese el nombre a buscar");
        String nombreAbuscar = input.next();

        int posicion = listaNombres.indexOf(nombreAbuscar);

        if (posicion == -1){
            System.out.println(nombreAbuscar + " no esta en la lista!!");
        } else {
            System.out.println(nombreAbuscar + " esta en la posicion " + posicion);
        }

        System.out.println("Quieres eliminar a " + nombreAbuscar + " de la lista? (S/N)");
        String respuesta = input.next();

        if (respuesta.equals("S")){
            System.out.println("eliminando a " + nombreAbuscar);
            listaNombres.remove(posicion);
        } else{
            System.out.println("No se ha eliminado a ningun elemento");
        }

        for (int i = 0; i < listaNombres.size(); i++){
            System.out.println("--> "+listaNombres.get(i));
        }


    }
}