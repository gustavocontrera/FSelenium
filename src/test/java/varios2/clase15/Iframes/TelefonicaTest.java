package varios2.clase15.Iframes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Tools;

import java.util.List;

public class TelefonicaTest {

    public WebDriver driver;

    @BeforeMethod
    public void setup(){
        WebDriver driver = Tools.getDriver("https://www.telefonica.com/es/web/shareholders-investors");
        driver.manage().window().maximize();
    }

    @Test
    public void telefonicaTest() throws InterruptedException {
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
        //
        Thread.sleep(3000);
        System.out.println("Before switching... " + driver.getTitle());
        System.out.println("After switching... " + driver.getCurrentUrl());

        driver.switchTo().frame("euroland-ticker-es");

        List<WebElement> tabs = driver.findElements(By.className("Tab"));
        Assert.assertEquals(tabs.size(), 2, "Deberian haber dos tabs seleccionadas");

        for (WebElement tab: tabs) {
            System.out.println("====> " + tab.getText());
            if (tab.getText().equals("NYSE")){
                tab.click();
                System.out.println("****** Se hizo click en nyse ****");
                Thread.sleep(2000);
            }
        }
        Thread.sleep(3000);

        WebElement activeTab = driver.findElement(By.className("Tab_Active"));
        Assert.assertEquals(activeTab.getText(), "NYSE", "La tab NYSE deberia estar seleccionada...");

        List<WebElement> dataItemsList = driver.findElements(By.className("DataItem"));
        System.out.println("===> DATA ITEMS " + dataItemsList.size());

        List<WebElement> dataValuesList = driver.findElements(By.className("DataValue"));
        System.out.println("===> DATA ITEMS " + dataValuesList.size());


    }
}
