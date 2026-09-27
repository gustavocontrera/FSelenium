package varios1.clase04;

public class Arreglos1 {

    public static void main(String[] args) {

        int[] horas = new int[]{0, 0, 34, 29, 14};

        for(int pos = 0; pos < horas.length; ++pos) {
            System.out.println("Posicion " + pos + " --> " + horas[pos]);
        }

        String[] dias = new String[]{"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};

        for(int i = 0; i < dias.length; ++i) {
            System.out.println("Dia de la semana " + dias[i]);
        }

    }
}
