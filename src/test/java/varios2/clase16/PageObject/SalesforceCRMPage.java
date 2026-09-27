package varios2.clase16.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SalesforceCRMPage extends BasePage{

    public SalesforceCRMPage (WebDriver remoteDriver){
        driver = remoteDriver;
    }

    public String getQueEsCRMText(){
        WebElement QueEsUnCrmH1 = driver.findElement(By.id("que-es-crm"));
        return QueEsUnCrmH1.getText();
    }

    public boolean buscarLink(String linkToSearch){
        boolean encontreLink = false;
        List<WebElement> listaLinks = driver.findElements(By.tagName("a"));
        for (WebElement link : listaLinks) {
            if (link.getText().isEmpty() == false) {
                if (link.getText().equals(linkToSearch)){
                    encontreLink = true;
                }
            }
        }

        return encontreLink;

    }


}
