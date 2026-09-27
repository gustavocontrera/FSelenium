package varios2.clase110.ejemplo5;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListeners implements ISuiteListener {

    public void onStart(ISuite suite) {
        System.out.println("comenzo la ejecucion de la Suite name : " + suite.getName());
    }

    public void onFinish(ISuite suite) {
        System.out.println("terminó la ejecucion de la Suite name : " + suite.getName());
    }
}
