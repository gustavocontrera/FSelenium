package varios1.clase03;

public class Metodos1 {

    public static void main(String[] args) {
        bienvenida();
        System.out.println("En ejecución...");
        mostrarSuma(4,5);
        //El valor de la variable doble es igual a lo que retorne el metodo obtenerDoble, pasándole un 10 como parámetro
        int doble = obtenerDoble(10);
        System.out.println("El doble obtenido es de " + doble);

        int multiplicacion = multiplicar(3, doble);
        System.out.println("La multiplicación es: " + multiplicacion);

        finalizar();
    }

    public static int multiplicar(int num3, int num4) {
        return num3 * num4;
    }

    //Escribir un metodo que reciba un número y retorne su doble
    public static int obtenerDoble(int num) {
        return num * 2;
    }

    public static void mostrarSuma(int num1, int num2) {
        int suma = num1 + num2;
        System.out.println("La suma es: " + suma);
    }

    public static void bienvenida() {
        System.out.println("Bienvenidos al Programa!!");
    }

    public static void finalizar() {
        System.out.println("El programa ha finalizado!");
    }


}
