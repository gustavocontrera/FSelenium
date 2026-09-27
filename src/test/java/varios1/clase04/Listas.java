package varios1.clase04;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Listas {

    public static void main(String[] args) {

        List<String> listaNombres = new ArrayList();

        listaEstaVacia(listaNombres);

        System.out.println("Agregando elementos.....");
        listaNombres.add("Andres");
        listaNombres.add("Ana");
        listaNombres.add("Jose");

        listaEstaVacia(listaNombres);

        imprimirListaString(listaNombres);

        System.out.println("eliminando nombre: " + (String)listaNombres.get(1));
        listaNombres.remove(1);

        imprimirListaString(listaNombres);

        System.out.println("**************");

        List<Integer> listaHoras = new ArrayList();
        int num = -1;
        Scanner input = new Scanner(System.in);


        imprimirListaInteger(listaHoras);

        System.out.println("Ingrese numeros hasta ingresar el 0....");

        while(num != 0) {
            System.out.println("Ingrese un numero:");
            num = input.nextInt();
            listaHoras.add(num);
        }

        imprimirListaInteger(listaHoras);

        for(int i = 0; i < listaHoras.size(); i++) {
            System.out.println("Horas trabajadas: " + listaHoras.get(i));
        }

    }

    public static void listaEstaVacia(List<String> listaStrings) {
        if (listaStrings.isEmpty()) {
            System.out.println("La lista esta vacia");
        } else {
            System.out.println("La lista tiene elementos!");
        }

    }

    public static void imprimirListaString(List<String> listaStrings) {
        for(int i = 0; i < listaStrings.size(); i++) {
            System.out.println("En posicion " + i + " se encuentra almacenado " + (String)listaStrings.get(i));
        }
    }

    public static void imprimirListaInteger(List<Integer> listaInteger) {
        for(int i = 0; i < listaInteger.size(); i++) {
            System.out.println("En posicion " + i + " se encuentra almacenado " + (Integer)listaInteger.get(i));
        }
    }

}
