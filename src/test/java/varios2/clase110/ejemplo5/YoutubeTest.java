package varios2.clase110.ejemplo5;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class YoutubeTest {

    @BeforeMethod
    public void setup(){
        System.out.println("Esto es el setup!! @BeforeMethod");
    }

    @Test
    public void test1(){
        System.out.println("Esto es el TEST1!!! @Test");
    }

    @AfterMethod
    public void closeTest(){
        System.out.println("Esto es el fin!!! @AfterMethod");
    }

}
