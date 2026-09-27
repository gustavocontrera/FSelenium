package varios2.clase110.ejemplo1;

public class Demo implements MyInterface {

    public void metodo1() {
        System.out.println("Este es el método 1");
    }

    public void metodo2() {
        System.out.println("Este es el método 2");
    }

    public static void main(String arg[]) {
        MyInterface obj = new Demo();
        obj.metodo1();
        obj.metodo2();
    }
}
