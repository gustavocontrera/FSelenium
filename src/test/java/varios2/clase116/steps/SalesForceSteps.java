package varios2.clase116.steps;

import varios2.clase116.pages.RegistrationPage;
import varios2.clase116.pages.LandingPage;
import varios2.clase116.pages.PrivacyPage;
import varios2.clase116.utilities.Constants;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.util.concurrent.TimeUnit;

public class SalesForceSteps {

    //variables de clase...
    WebDriver driver;
    public LandingPage landingPage;
    public RegistrationPage registrationPage;
    public PrivacyPage privacyPage;

    @Given("estoy en la pagina de inicio")
    public void estoy_en_la_pagina_de_inicio() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://www.salesforce.com/");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        landingPage = new LandingPage(driver);
    }

    @When("ingreso a la seccion de registrarse")
    public void ingreso_a_la_seccion_de_registrarse() {

        Assert.assertTrue(landingPage.getPageTitle().contains("Marketing"), "Error: el titulo no contiene el texto Marketing");
        Assert.assertTrue(landingPage.getPageUrl().contains("www.salesforce.com"), "Error: la url es incorrecta");

        registrationPage = landingPage.goToRegistrationPage();

        Assert.assertTrue(registrationPage.getPageTitle().contains("Prueba Gratuita"), "Error: el titulo no contiene el texto Prueba gratuita");
        Assert.assertTrue(registrationPage.getPageUrl().contains("signup"), "Error: la url no contiene la palabra signup");
    }

    @Then("puedo completar los campos de registro")
    public void puedo_completar_los_campos_de_registro() {

        registrationPage.completeRegistrationFields();

        Assert.assertTrue(registrationPage.getPageTitle().contains("Prueba Gratuita"), Constants.TITLE_ERROR_MSG);
        Assert.assertTrue(registrationPage.getPageUrl().contains("signup"), "Error: la url no contiene la palabra signup");

    }

    @Then("encuentro el titulo {string}")
    public void encuentro_el_titulo(String string) {
        String h1Text = landingPage.getH1Text();
        Assert.assertTrue(h1Text.contains("Construye relaciones"));
    }

    @When("realizo click en el link Privacidad")
    public void realizo_click_en_el_link_privacidad() {
        privacyPage = landingPage.goToPrivacyPage();
        Assert.assertEquals(privacyPage.getPageTitle(), Constants.PRIVACY_PAGE_TITLE, Constants.TITLE_ERROR_MSG);
    }

    @Then("un titulo debe ser Privacidad")
    public void un_titulo_debe_ser_privacidad() {
        Assert.assertTrue(privacyPage.getH1Text().contains("Privacidad"));
    }

}
