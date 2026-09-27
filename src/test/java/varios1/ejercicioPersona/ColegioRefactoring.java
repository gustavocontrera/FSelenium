package varios1.ejercicioPersona;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ColegioRefactoring {

    private static List<Profesor> listaProfesores = new ArrayList<>();
    private static List<Alumno> listaAlumnos = new ArrayList<>();
    private static List<Persona> listaPersonas = new ArrayList<>();

    public static void main(String args[]){
        Scanner input = new Scanner(System.in);

        Persona profe1 = new Profesor();
        Persona profe2 = new Profesor("Juan Lopez", 123, 9000);

        agregarElementoAListaPersonas(profe1);
        agregarElementoAListaPersonas(profe2);

        Persona a1 = new Alumno("Ana Martinez", 123456, "Matematica");
        Persona a2 = new Alumno("Andres Gonzalez", 2222, "Informatica");

        agregarElementoAListaPersonas(a1);
        agregarElementoAListaPersonas(a2);

        imprimirListaPersonas();

        Profesor profeFisica = new Profesor("Analia Rowen", 9999, 8000);
        Profesor profeProgramacion = new Profesor("Joaquin Tun", 2323, 9000);

        agregarProfesorAListaProfes(profeFisica);
        agregarProfesorAListaProfes(profeProgramacion);

        System.out.println("**** PROFESORES *****");
        boolean encontrarNombre = buscarProfesor("Analia Rowen");

        int cantidad = Utilities.solicitarNumero("Cuantos alumnos quiere ingresar?");
        /*try {
            cantidad = input.nextInt();
            System.out.println("Ingrese " + cantidad +" alumnos");
        } catch (Exception ex){
            System.out.println("Ha ocurrido un error, no se ingresan alumnos");
        }*/

        for (int i = 0 ; i < cantidad ; i++ ){
            Alumno alumnoNuevo = crearAlumno();
            agregarAlumnoAListaAlumnos(alumnoNuevo);
            System.out.println("Se ha agregado a " + alumnoNuevo.getNombre() + " al sistema");
        }

        imprimirListaAlumnos();
    }

    public static void imprimirListaAlumnos(){
        System.out.println("*** LISTA ALUMNOS ***");
        for (Alumno estudiante : listaAlumnos){
            System.out.println(estudiante);
        }
    }

    public static void agregarElementoAListaPersonas(Persona p){
        listaPersonas.add(p);
    }

    public static void agregarProfesorAListaProfes(Profesor profe){
        listaProfesores.add(profe);
    }

    public static void agregarAlumnoAListaAlumnos(Alumno alumnoNuevo){
        listaAlumnos.add(alumnoNuevo);
    }

    public static void imprimirListaPersonas(){
        if (listaPersonas.isEmpty() == false ) {
            System.out.println("****** LISTA PERSONAS ******");
            for (int i = 0; i < listaPersonas.size(); i++){
                System.out.println(listaPersonas.get(i));
            }
        } else {
            System.out.println("La lista se encuentra vacia...");
        }
    }

    public static boolean buscarProfesor(String unNombre){
        boolean encontrarNombre = false;
        for (Profesor profe : listaProfesores){
            if (profe.getNombre().equals(unNombre)){
                System.out.println("El profesor/a se encuentra en la lista");
                encontrarNombre = true;
            }
        }

        if (encontrarNombre == false){
            System.out.println("El nombre no esta en la lista!!");
        }

        return encontrarNombre;
    }

    public static Alumno crearAlumno(){
        Scanner input = new Scanner(System.in);

        String nombre = Utilities.solicitarTexto("Ingrese el nombre del alumno");
        int dni = Utilities.solicitarNumero("Ingrese el DNI del alumno");
        String curso = Utilities.solicitarTexto("Ingrese el curso del alumno");

        Alumno alumno = new Alumno(nombre, dni, curso);
        return alumno;
    }
}