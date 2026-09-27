package varios2.clase110.ejemplo6;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class MyTests {

    @Test
    public void successTest() {
        Assert.assertTrue(true);
        Assert.assertEquals(2, 2);
    }

    @Test
    public void failureTest() {
        Assert.assertEquals(2, 3);
    }

    @Test
    public void skippedTest() {
        throw new SkipException("Skipped Test!!");
    }
}
