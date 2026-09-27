package varios2.clase112;

public class Persona {

    String nombre;
    String documento;
    String email;
    String pais;

    public Persona(String unNombre, String unDoc, String unEmail, String unPais){
        this.nombre = unNombre;
        this.documento = unDoc;
        this.email = unEmail;
        this.pais = unPais;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getDocumento(){
        return this.documento;
    }

    public String getEmail(){
        return  this.email;
    }

    public String getPais(){
        return this.pais;
    }

    public String toString(){
        return this.nombre + " tiene documento " + this.documento + ". Vive en " + this.pais + " y su email es " + this.email;
    }
}
