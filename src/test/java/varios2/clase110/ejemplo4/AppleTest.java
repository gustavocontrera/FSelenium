package varios2.clase110.ejemplo4;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners(value = InvokedMethodListener.class)
public class AppleTest {

    @BeforeMethod
    public void setup(){
        System.out.println("Esto es el setup!! @BeforeMethod - APPLE");
    }

    @Test
    public void test1(){
        System.out.println("Esto es el TEST1!!! @Test - APPLE");
    }

    @AfterMethod
    public void closeTest(){
        System.out.println("Esto es el fin!!! @AfterMethod - APPLE");
    }

    /*
    --> imprimir algo...
        @beforeMethod
    --> imprimir algo...

    --> imprimir algo...
        @test
    --> imprimir algo...

    --> imprimir algo...
        @afterMethod
    --> imprimir algo...

     */


}
