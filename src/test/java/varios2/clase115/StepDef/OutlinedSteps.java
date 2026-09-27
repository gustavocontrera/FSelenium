package varios2.clase115.StepDef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class OutlinedSteps {
    Integer saldo = 0;

    @Given("tengo {int} dolares")
    public void tengo_dolares(Integer valorInicial) {
        saldo = valorInicial;
    }

    @When("gasto {int} dolares")
    public void gasto_dolares(Integer valorCompra) {
        saldo = saldo - valorCompra;
    }

    @Then("me sobran {int} dolares")
    public void me_sobran_dolares(Integer valorEsperado) {
        Assert.assertEquals(saldo, valorEsperado, "Error: se esperaba otro valor final");
    }

    @Given("que logueado en un sitio web")
    public void que_logueado_en_un_sitio_web() {

    }

    @Given("me encuentro en la pagina principal")
    public void me_encuentro_en_la_pagina_principal() {

    }

    @When("busco el nombre de usuario {string}")
    public void busco_el_nombre_de_usuario(String string) {

    }

    @Then("recibo un mensaje indicando {string}")
    public void recibo_un_mensaje_indicando(String string) {

    }
}