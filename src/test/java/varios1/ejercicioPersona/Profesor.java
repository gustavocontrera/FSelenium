package varios1.ejercicioPersona;

public class Profesor extends varios1.ejercicioPersona.Persona {

    private int salario;

    public Profesor() {
        super("Sin nombre", 0);
        this.salario = 50000;
    }

    public Profesor(String unNombre, int unDni, int unSalario) {
        super(unNombre, unDni);
        this.salario = unSalario;
    }

    public void setSalario(int unSalario) {
        this.salario = unSalario;
    }

    public int getSalario() {
        return this.salario;
    }

    public String toString() {
        return super.toString() + ". Su salario es " + this.salario;
    }
}
