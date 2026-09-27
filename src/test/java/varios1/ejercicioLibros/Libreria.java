package varios1.ejercicioLibros;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Libreria {

    public static void main(String[] args) {

        System.out.println("Iniciando programa de Libreria...");
        System.out.println("Creando libros...");
        List<Libro> listaLibros = new ArrayList<>();

        for(int i = 0; i < 2; ++i) {
            System.out.println("Ingrese los datos del nuevo Libro: ");
            String tituloDelLibro = getStringDelUsuario("Ingrese el titulo del libro");
            String autorDelLibro = getStringDelUsuario("Ingrese el Autor del libro");
            int nroPagLibro = getNumero("Ingrese las Páginas del libro");
            int nroDeIsbn = getNumero("Ingrese el ISBN del libro");
            Libro nuevoLibro = new Libro(nroDeIsbn, tituloDelLibro, autorDelLibro, nroPagLibro);
            listaLibros.add(nuevoLibro);
        }

        imprimirListaLibros(listaLibros);

    }

    public static void imprimirListaLibros(List<Libro> listaLibros) {
        System.out.println("Los Libros de la lista son: ");
        Iterator var1 = listaLibros.iterator();

        while(var1.hasNext()) {
            Libro libro = (Libro) var1.next();
            System.out.println(libro);
        }

    }

    public static String getStringDelUsuario (String unTexto){
        Scanner input = new Scanner(System.in);
        System.out.println(unTexto);
        String texto = input.next();
        return texto;
    }

    public static int getNumero(String unTexto) {
        Scanner input = new Scanner(System.in);
        System.out.println(unTexto);
        try {
            int num = input.nextInt();
            return num;
        } catch (Exception var3) {
            System.out.println("Error: Se esperaba un numero!!");
            return 0;
        }
    }

}
