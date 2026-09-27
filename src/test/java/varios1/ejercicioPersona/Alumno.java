package varios1.ejercicioPersona;

public class Alumno extends Persona {

    private String curso;

    public Alumno(String unNombre, int unDni, String unCurso) {
        super(unNombre, unDni);
        this.curso = unCurso;
    }

    public void setCurso(String unCurso) {
        this.curso = unCurso;
    }

    public String getCurso() {
        return this.curso;
    }

    public String toString() {
        return super.toString() + " y cursa " + this.curso;
    }
}
