package varios2.clase16.Test;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SalesforceSimpleTest extends BaseTest {

    @Test
    public void salesforceFirstTest(){

        Assert.assertEquals(salesforceLandingPage.getPageTitle(), "Salesforce: CRM, Ventas, Marketing, Servicios y más", "Se esperaba otro titulo");
        Assert.assertEquals(salesforceLandingPage.getPageUrl(), "https://www.salesforce.com/mx/?ir=1", "Se esperaba otra URL");
        //WebElement elementoH1 = driver.findElement(By.tagName("h1"));

        Assert.assertEquals(salesforceLandingPage.getH1Text(),"Construye relaciones con los clientes que impulsen tu negocio", "Se esperaba otro h1" );

        boolean encontreTextoBuscado = salesforceLandingPage.searchText("¿Qué hay de nuevo en Salesforce?");
        /*List<WebElement> listaTextos = driver.findElements(By.className("header-text"));
        Assert.assertFalse(listaTextos.isEmpty());

        boolean encontreTextoBuscado = false;
        for (WebElement texto : listaTextos) {
            System.out.println("===> " + texto.getText());
            if (texto.getText().equals("¿Qué hay de nuevo en Salesforce?")){
                encontreTextoBuscado = true;
            }
        }*/
        Assert.assertTrue(encontreTextoBuscado);
    }

}
