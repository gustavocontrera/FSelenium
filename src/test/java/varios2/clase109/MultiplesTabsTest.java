package varios2.clase109;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MultiplesTabsTest {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://salesforce.com");
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void testSoftAsserts(){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(3 < 1, "Error: la condicion no se cumple");
        softAssert.assertEquals(4, 5, "Error: los numeros no son iguales");
        softAssert.assertTrue("hola".contains("x"), "Error: no se encontro la x en el texto");
        softAssert.assertNotEquals("hola", "hola", "Error: los textos son iguales!!");
        System.out.println("Fin del Test!!");
        softAssert.assertAll();
    }

    @Test
    public void registratrionTest() throws InterruptedException {

        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());

        String mainTabId = driver.getWindowHandle();
        System.out.println("---> pagina principal: " + mainTabId);
        driver.findElement(By.xpath("//*[@href='/mx/form/signup/freetrial-sales-pe/?d=70130000000EqoP']")).click();
        Thread.sleep(1000);

        Assert.assertTrue(driver.getCurrentUrl().contains("https://www.salesforce.com/mx/?ir=1"), "Error: se esperaba la URL https://www.salesforce.com/mx/?ir=1");
        Assert.assertTrue(driver.getTitle().contains("Marketing"), "Error: se esperaba que el titulo contenga la palabra Marketing");

        String registrationTabId = "";
        System.out.println("================");
        Set<String> allTabs = driver.getWindowHandles();
        for (String tabId: allTabs){
            System.out.println("---> " + tabId);
            if (tabId.equals(mainTabId) == false){
                driver.switchTo().window(tabId);
                System.out.println("***********");
                System.out.println(driver.getCurrentUrl());
                System.out.println(driver.getTitle());
                registrationTabId = tabId;
            }
        }

        Assert.assertTrue(driver.getCurrentUrl().contains("signup"), "Error: se esperaba que la URL contenga la palabra signup");
        Assert.assertTrue(driver.getTitle().contains("Prueba Gratuita"), "Error: se esperaba que el titulo contenga la palabra Prueba Gratuita");

        driver.findElement(By.name("UserFirstName")).sendKeys("Alan");
        driver.findElement(By.name("UserLastName")).sendKeys("Paulin");
        driver.findElement(By.name("UserTitle")).sendKeys("QA Analyst");
        driver.findElement(By.name("UserEmail")).sendKeys("Test@Test.com");

        HashMap<String, String> mapaTabs = new HashMap<>();
        mapaTabs.put("principal", mainTabId);
        mapaTabs.put("registration", registrationTabId);

        driver.switchTo().window(mapaTabs.get("principal"));
        Thread.sleep(3000);
        driver.switchTo().window(mapaTabs.get("registration"));
        Thread.sleep(3000);
        driver.switchTo().window(mapaTabs.get("principal"));
        Thread.sleep(3000);
        driver.switchTo().window(mapaTabs.get("registration"));
        Thread.sleep(3000);
    }
}
