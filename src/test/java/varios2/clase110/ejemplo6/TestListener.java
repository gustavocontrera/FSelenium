package varios2.clase110.ejemplo6;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    public void onStart(ITestContext context) {
        System.out.println("@@@@  La ejecucion a comenzado!!");
    }

    public void onFinish(ITestContext context) {
        System.out.println("@@@@  La ejecucion ha finalizado!!");
    }

    public void onTestStart(ITestResult result) {
        System.out.println("@@@@  El test ha comenzado --> " + result.getName());
    }

    public void onTestSuccess(ITestResult result) {
        System.out.println("@@@@ Test passed!! --> " + result.getName());
    }

//    public void onTestFailure(ITestResult result) {
//        System.out.println("@@@@ Test failed!! --> " + result.getName() + " - " + result.getTestName());
//        ITestContext context = result.getTestContext();
//        WebDriver driver = (WebDriver) context.getAttribute("WebDriver");
//        String projectPath = System.getProperty("user.dir");
//        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//
//        try {
//            FileUtils.copyFile(scrFile, new File(projectPath + "/screenshots/" + result.getName() + "_" + System.currentTimeMillis() + ".png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void onTestSkipped(ITestResult result) {
        System.out.println("@@@@ Test skipped!!");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("@@@@ The test has failed more than expected!!");
    }

    public void onTestFailedWithTimeout(ITestResult result) {
        System.out.println("@@@@ Test failed due to timeout...!!");
        this.onTestFailure(result);
    }

}
