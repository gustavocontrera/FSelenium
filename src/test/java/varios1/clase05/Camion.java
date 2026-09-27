package varios1.clase05;

public class Camion {

    //Defino los atributos de instancia
    private String color;
    private int chapa;
    private int anioCompra;
    private int nroCamion;

    //Defino atributos de clase
    private static int CONTADOR_CAMIONES  = 1000;

    //constructor sin parámetros
    public Camion() {
        this.color = "Negro";
        this.chapa = 1234;
        this.anioCompra = 1990;
    }

    //constructor con un parámetro
    public Camion(String unColor) {
        this.color = unColor;
        this.chapa = 1234;
        this.anioCompra = 2021;
    }

    //constructor con dos parámetros
    public Camion(String unColor, int unaChapa, int unAnio) {
        this.color = unColor;
        this.chapa = unaChapa;
        this.anioCompra = unAnio;
        this.nroCamion = CONTADOR_CAMIONES;
        CONTADOR_CAMIONES++;
    }

    public void setChapa(int unaChapa) {
        this.chapa = unaChapa;
    }

    public int getChapa() {
        return this.chapa;
    }

    public void setColor(String unColor) {
        this.color = unColor;
    }

    public String getColor() {
        return this.color;
    }

    public void setAnioCompra(int unAño) {
        this.anioCompra = unAño;
    }

    public int getAnioCompra() {
        return this.anioCompra;
    }

    public int getNroCamion(){
        return getNroCamion();
    }

    public boolean leTocaRevision() {
        return this.anioCompra < 2015;
    }

    public boolean tieneMismoColor(Camion unCamion) {
        return this.getColor().equals(unCamion.getColor());
    }

    //metodo toString para mostrar los valores de los atributos del objeto
    public String toString() {
        String datosDelCamion = "El camion " + this.nroCamion + " es de color " + this.color + " y de chapa " + this.chapa + ". El año de compra es " + this.anioCompra;

        if (this.leTocaRevision()) {
            datosDelCamion = datosDelCamion + " y le toca revision!!";
        } else {
            datosDelCamion = datosDelCamion + " y NO le toca revision!!";
        }
        return datosDelCamion;
    }
}
