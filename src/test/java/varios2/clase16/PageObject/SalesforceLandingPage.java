package varios2.clase16.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class SalesforceLandingPage extends BasePage {

    public SalesforceLandingPage (WebDriver remoteDriver){
        driver = remoteDriver;
    }

    public SalesforceCRMPage goToCRMPage() throws InterruptedException {
        WebElement elementoCRM = driver.findElement(By.xpath("//*[@href='/mx/crm/']"));
        Thread.sleep(2000);
        elementoCRM.click();
        SalesforceCRMPage nextPage = new SalesforceCRMPage(driver);
        return nextPage;
    }

    /*List<WebElement> listaHref = driver.findElements(By.xpath("//*[@href='/mx/crm/']"));
        for (WebElement boton: listaHref) {
            System.out.println("Button: " + boton.getText());
        }
        listaHref.get(2).click();

     */


    public SalesforceRegistrationPage goToRegistrationPage(){
        driver.navigate().to("https://www.salesforce.com/mx/form/signup/freetrial-sales-pe/?d=70130000000EqoP");
        SalesforceRegistrationPage nextPage = new SalesforceRegistrationPage(driver);
        return nextPage;
    }

    public String getH1Text(){
        WebElement elementoH1 = driver.findElement(By.tagName("h1"));
        return elementoH1.getText();
    }

    public boolean searchText(String searchText) {
        List<WebElement> listaTextos = driver.findElements(By.className("header-text"));
        Assert.assertFalse(listaTextos.isEmpty());

        boolean encontreTextoBuscado = false;
        for (WebElement texto : listaTextos) {
            System.out.println("===> " + texto.getText());
            if (texto.getText().equals(searchText)){
                encontreTextoBuscado = true;
            }
        }
        return encontreTextoBuscado;
    }
}
