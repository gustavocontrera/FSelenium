package varios1.clase05;

import java.util.Scanner;

public class Automotora {

    public static void main(String[] args) {
        System.out.println("Iniciando programa de Automotora...");

        System.out.println("Creando camiones...");

        varios1.clase05.Camion c1 = new Camion("rosa", 5896, 1920);
        varios1.clase05.Camion c2 = new Camion("rosa", 8963, 1930);
        varios1.clase05.Camion c3 = new Camion("negro", 8745, 2010);
        varios1.clase05.Camion c4 = new Camion("violeta", 2589, 2016);

        System.out.println("El color del camion c1 es " + c1.getColor());
        System.out.println("La chapa del camion c1 es " + c1.getChapa());
        System.out.println("Le toca revision a c1? " + c1.leTocaRevision());

        Scanner input = new Scanner(System.in);

        System.out.println("Ingrese el color del camion c1: ");
        String color = input.next();
        System.out.println("Actualizando el color del camion c1...");
        c1.setColor(color);
        System.out.println("El color del camion c1 es " + c1.getColor());

        System.out.println("Ingrese la chapa del camion c1: ");
        int chapa = input.nextInt();
        System.out.println("Actualizando la chapa del camion c1...");
        c1.setChapa(chapa);

        System.out.println("Ingrese el año de compra del camion c1: ");
        int anioCompra = input.nextInt();
        System.out.println("Actualizando el año de compra del camion c1...");
        c1.setAnioCompra(anioCompra);

        System.out.println("El color del camion c4 es " + c4.getColor());
        System.out.println("La chapa del camion c4 es " + c4.getChapa());

        c4.setColor("Marrón");

        System.out.println("***** DATOS DE LOS CAMIONES *******");
        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2);
        System.out.println("c3: " + c3);
        System.out.println("c4: " + c4.toString());

        System.out.println("El camion c1, tiene el mismo color que c2? " + c1.tieneMismoColor(c2));

    }
}