package varios2.clase105.tests;

import varios2.clase105.utilities.Constants;
import varios2.clase105.pages.AuthenticationPage;
import varios2.clase105.pages.CreateAccountPage;
import varios2.clase105.pages.LandingPage;
import varios2.clase105.pages.MyAccountPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class EcommerceHelper extends BaseTest {

    public WebDriver driver;

    public EcommerceHelper(WebDriver remoteDriver) {
        driver = remoteDriver;
        PageFactory.initElements(driver, this);
    }

    public MyAccountPage completeRegistration(String emailAddress) throws InterruptedException {

        landingPage = new LandingPage(driver);

        AuthenticationPage authenticationPage = landingPage.loginBtnClick();

        Assert.assertEquals(authenticationPage.getPageTitle(), Constants.AUTHENTICATION_PAGE_TITLE, "Se esperaba otro titlo!!");
        Assert.assertEquals(authenticationPage.getPageURL(), Constants.AUTHENTICATION_PAGE_URL, "Se esperaba otra URL!!");
        Assert.assertTrue(authenticationPage.getPageURL().contains("authentication"));
        Assert.assertEquals(authenticationPage.getAutenticationH1Text(), H1_AUTHENTICATION, "Se esperaba el h1: " + H1_AUTHENTICATION);

        authenticationPage.fillEmailAddress(emailAddress);

        CreateAccountPage createAccountPage = authenticationPage.clickOnCreateAccountBtn();

        Thread.sleep(6000);
        String populatedEmail = createAccountPage.getEmailValue();

        Assert.assertEquals(populatedEmail, emailAddress, "Los emails deberian de coincidir!!!");

        createAccountPage.fillRegistrationFields();

        //clicking on register button
        MyAccountPage myAccountPage = createAccountPage.clickOnRegisterAccountBtn();
        return myAccountPage;


    }


}
