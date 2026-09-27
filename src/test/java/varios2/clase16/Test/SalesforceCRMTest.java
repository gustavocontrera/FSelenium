package varios2.clase16.Test;

import varios2.clase16.PageObject.SalesforceCRMPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SalesforceCRMTest extends BaseTest {

    @Test
    public void CRMTest() throws InterruptedException {
        //WebElement elementoCRM = driver.findElement(By.xpath("//*[@href='/mx/crm/']"));
        //elementoCRM.click();
        SalesforceCRMPage salesforceCRMPage = salesforceLandingPage.goToCRMPage();
        //WebElement QueEsUnCrmH1 = driver.findElement(By.id("que-es-crm"));
        //System.out.println("===> " + QueEsUnCrmH1.getText());
        String crmText = salesforceCRMPage.getQueEsCRMText();
        Assert.assertEquals(crmText, "¿Qué es CRM?", "Se esperaba otro texto!!");

        /*boolean encontreLinkPrivacidad = false;
        List<WebElement> listaLinks = driver.findElements(By.tagName("a"));
        for (WebElement link : listaLinks) {
            if (link.getText().isEmpty() == false) {
                System.out.println(" ** link:  " + link.getText());
                if (link.getText().equals("Privacidad")){
                    encontreLinkPrivacidad = true;
                }
            }
        }*/
        boolean encontreLinkPrivacidad = salesforceCRMPage.buscarLink("Privacidad");
        Assert.assertTrue(encontreLinkPrivacidad);
    }




}
