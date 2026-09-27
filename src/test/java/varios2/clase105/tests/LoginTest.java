package varios2.clase105.tests;

import varios2.clase105.utilities.Constants;
import varios2.clase105.pages.AuthenticationPage;
import varios2.clase105.pages.MyAccountPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void loginTest() throws InterruptedException {
        String emailAddress = "seleniumintermedio" + Math.random() + "test@gmail.com";

        EcommerceHelper helper = new EcommerceHelper(driver);
        MyAccountPage myAccountPage = helper.completeRegistration(emailAddress);

        //System.out.println( "el nombre actual es 1" + Constants.FAKER_FIRST_NAME + " " + Constants.FAKER_LAST_NAME);

        String accountName = myAccountPage.getAccountName();

        //Checking the account is created.....
        String fullName = Constants.FAKER_FIRST_NAME + " " + Constants.FAKER_LAST_NAME;
        Assert.assertEquals(accountName, fullName, "Se esperaba otro nombre!!");
        Assert.assertTrue(accountName.contains(Constants.FAKER_FIRST_NAME), "El primer nombre no se encontró");
        Assert.assertTrue(accountName.contains(Constants.FAKER_LAST_NAME), "El apellido no se encontró");
        Assert.assertEquals(myAccountPage.getLogoutBtnText(), "Sign out", "El link Signout deberia estar presente");

        //LOG OUT....
        AuthenticationPage authenticationPage = myAccountPage.clickOnLogoutBtn();

/*
        Assert.assertEquals(loginBtnMenu.getText(), "Sign in", "No se encontro el boton de Sign In");

        WebElement emailAddressLoginElement = driver.findElement(By.id("email"));
        emailAddressLoginElement.sendKeys(emailAddress);

        WebElement passwordElement = driver.findElement(By.id("passwd"));
        passwordElement.sendKeys(Constants.PASSWORD);

        //SIGN IN.....
        loginWithUserAndPasswordBtn.click();

        //Thread.sleep(4000);
        logoutElement = driver.findElement(By.className("logout"));

        nameElement = driver.findElement(By.xpath("(//*[@href='http://automationpractice.com/index.php?controller=my-account']/span)[1]"));
        System.out.println("---> " + nameElement.getText());

        fullName = Constants.FAKER_FIRST_NAME+ " " + Constants.FAKER_LAST_NAME;
        Assert.assertEquals(nameElement.getText(), fullName, "Se esperaba otro nombre!!");
        Assert.assertTrue(nameElement.getText().contains(Constants.FAKER_FIRST_NAME), "El primer nombre no se encontró");
        Assert.assertTrue(nameElement.getText().contains(Constants.FAKER_LAST_NAME), "El apellido no se encontró");
        Assert.assertEquals(logoutElement.getText(), "Sign out", "El link Signout deberia estar presente");
*/
        //driver.close();
    }

}