package varios1.ejercicioLibros;

public class Libro {

    //declaro los atributos de instancia
    private int isbn;
    private String titulo;
    private String autor;
    private int nroPaginas;

    //Constructor sin parámetros
    public Libro() {
        this.isbn = 0;
        this.titulo = "Sin Título";
        this.autor = "Desconocido";
        this.nroPaginas = 0;
    }

    //constructor con todos los parametros
    public Libro(int unIsbn, String unTitulo, String unAutor, int unNroPaginas) {
        this.isbn = unIsbn;
        this.titulo = unTitulo;
        this.autor = unAutor;
        this.nroPaginas = unNroPaginas;
    }

    //Metodos setters y getters
    public void setIsbn(int unIsbn) {
        this.isbn = unIsbn;
    }

    public int getIsbn() {
        return this.isbn;
    }

    public void setTitulo(String unTitulo) {
        this.titulo = unTitulo;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setAutor(String unAutor) {
        this.autor = unAutor;
    }

    public String getAutor() {
        return this.autor;
    }

    public void setNroPaginas(int unNroPaginas) {
        this.nroPaginas = unNroPaginas;
    }

    public int getNroPaginas() {
        return this.nroPaginas;
    }

    public String toString() {
        String datosDelLibro = "El libro " + this.titulo + " es del autor " + this.autor + ", tiene " + this.nroPaginas + " páginas y su ISBN es " + this.isbn;
        return datosDelLibro;
    }

}
