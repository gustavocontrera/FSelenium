package varios2.clase114;

import org.testng.annotations.Factory;

public class ExecutionFactory {

    @Factory
    public Object[] executionFactoryTest(){

        return new Object[] {
                new SpotifyTest(Constants.CHROME_DRIVER_NAME),
                new SpotifyTest(Constants.FIREFOX_DRIVER_NAME),
                new SpotifyTest(Constants.IE_DRIVER_NAME)
        };
    }

}
