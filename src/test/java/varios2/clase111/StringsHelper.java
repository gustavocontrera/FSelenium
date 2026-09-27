package varios2.clase111;

public class StringsHelper {

    //                             nombre , edad, ciudad, pais ; nombre , edad, ciudad, pais
    private static String texto = "juan,23,Montevideo,Uruguay;ana,30,Rosario,Argentina;Jhon,20,Medellin,Colombia";

    public static void main(String args[]){
        boolean estaPresente = texto.contains("ana");
        System.out.println(estaPresente);

        String replacedString = texto.replace('a', 'A');
        System.out.println(replacedString);

        String[] personas = texto.split(";");
        System.out.println("La cantidad de personas es: " + personas.length);
        System.out.println(personas[0]);
        System.out.println(personas[1]);
        System.out.println(personas[2]);

        System.out.println("***********************");

        String [] datosPersona1 = personas[0].split(",");
        System.out.println(datosPersona1[0]);
        System.out.println(datosPersona1[1]);
        System.out.println(datosPersona1[2]);
        System.out.println(datosPersona1[3]);

        System.out.println("***********************");
        String [] datosPersona2 = personas[1].split(",");
        System.out.println(datosPersona2[0]);
        System.out.println(datosPersona2[1]);
        System.out.println(datosPersona2[2]);
        System.out.println(datosPersona2[3]);

        System.out.println("***********************");

        String [] datosPersona3 = personas[2].split(",");

        System.out.println("---> El nombre de la persona 3 es " + obtenerNombre(datosPersona3));
        System.out.println("---> La edad de la persona 3 es " + obtenerEdad(datosPersona3));
        System.out.println("---> La ciudad de la persona 3 es " + obtenerCiudad(datosPersona3));
        System.out.println("---> La pais de la persona 3 es " + obtenerPais(datosPersona3));

    }

    //nombre , edad, ciudad, pais
    public static String obtenerNombre(String[] datosPersona){
        return datosPersona[0];
    }

    public static String obtenerEdad(String[] datosPersona){
        return datosPersona[1];
    }

    public static String obtenerCiudad(String[] datosPersona){
        return datosPersona[2];
    }

    public static String obtenerPais(String[] datosPersona){
        return datosPersona[3];
    }


}
