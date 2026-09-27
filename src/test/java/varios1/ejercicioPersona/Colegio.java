package varios1.ejercicioPersona;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Colegio {

    public static void main(String[] args) {

        List<String> nombres = new ArrayList();
        nombres.add("Juan");
        nombres.add("Ana");
        nombres.add("José");
        System.out.println("Tamaño de la lista" + nombres.size());

        for(int i = 0; i < nombres.size(); ++i) {
            System.out.println("--- " + nombres.get(i));
        }

        List<Persona> listaPersonas = new ArrayList();
        List<Alumno> listaAlumnos = new ArrayList();

        listaPersonasEstaVacia(listaPersonas);

        System.out.println("Agregando personas.....");
        Persona alumno1 = new Alumno("Juan Perez", 133333, "Cocina");
        Persona alumno2 = new Alumno("Ana Lopez", 44444, "Pintura");
        Persona profe1 = new Profesor("Jose Rodriguez", 90000, 30000);
        Persona profe2 = new Profesor("Maria Schmidt", 55555, 45000);

        listaPersonas.add(alumno1);
        listaPersonas.add(alumno2);
        listaPersonas.add(profe1);
        listaPersonas.add(profe2);

        listaPersonasEstaVacia(listaPersonas);

        imprimirListaPersonas(listaPersonas);

        listaAlumnos.add((Alumno)alumno1);
        listaAlumnos.add((Alumno)alumno2);

        imprimirListaAlumnos(listaAlumnos);

        for(int i = 0; i < 2; ++i) {
            System.out.println("Ingrese los datos del nuevo alumno: ");
            String nombreAlumno = getStringDelUsuario("Ingrese el nombre de la persona");
            int dniAlumno = getNumero("Ingrese el DNI de la persona");
            String materiaAlumno = getStringDelUsuario("Ingrese la materia que cursa");
            int calificacionAlumno = getNumero("Ingrese la calificacion del alumno:");

            Persona estudiante = new Alumno(nombreAlumno, dniAlumno, materiaAlumno);
            listaAlumnos.add((Alumno)estudiante);
        }

        imprimirListaAlumnos(listaAlumnos);

    }

    public static void listaPersonasEstaVacia(List<Persona> listaPersonas) {
        System.out.println("LISTA PERSONAS");
        if (listaPersonas.isEmpty()) {
            System.out.println("No hay personas en la lista...");
        } else {
            System.out.println("La cantidad de personas es " + listaPersonas.size());
        }

    }

    public static void imprimirListaAlumnos(List<Alumno> listaAlumnos) {
        System.out.println("Los alumnos del Sistema son: ");
        Iterator var1 = listaAlumnos.iterator();

        while(var1.hasNext()) {
            Alumno alumno = (Alumno)var1.next();
            System.out.println(alumno);
        }
    }

    public static void imprimirListaPersonas(List<Persona> listaPersonas) {
        System.out.println("Las personas del Sistema son: ");
        Iterator var1 = listaPersonas.iterator();

        while(var1.hasNext()) {
            Persona p = (Persona)var1.next();
            System.out.println(p);
        }

    }

    public static String getStringDelUsuario(String unTexto) {
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
