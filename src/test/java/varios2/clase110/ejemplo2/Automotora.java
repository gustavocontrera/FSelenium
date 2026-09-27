package varios2.clase110.ejemplo2;

public class Automotora {

    public static void main(String args[]){

        IVehiculo automovil1 = new Auto();
        automovil1.acelerar();
        automovil1.girar();
        automovil1.reversa();
        automovil1.velocidadMaxima();

        IVehiculo moto1 = new Moto();
        moto1.acelerar();
        moto1.girar();
        moto1.reversa();
        moto1.velocidadMaxima();

    }
}
