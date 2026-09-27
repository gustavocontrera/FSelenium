package varios2.clase110.ejemplo4;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class InvokedMethodListener implements IInvokedMethodListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Antes de la invocacion de " + method.getTestMethod().getMethodName());
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Luego de la invocacion de " + method.getTestMethod().getMethodName());
    }

}
