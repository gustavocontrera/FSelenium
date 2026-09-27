package varios2.clase113.CSV;

public class Persona {

    String nombre;
    String apellido;
    String email;

    public Persona(String unNombre, String unApellido, String unEmail){
        this.nombre = unNombre;
        this.apellido = unApellido;
        this.email = unEmail;
    }

    public String getNombre(){
        return this.nombre;
    }
    public String getApellido(){
        return this.apellido;
    }

    public String getEmail(){
        return this.email;
    }

    public String toString(){
        return "Nombre: " + this.nombre + " " + this.apellido + " y su email es " + this.email;
    }
}
