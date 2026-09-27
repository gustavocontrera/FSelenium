package varios2.clase115;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "D:\\Google Drive\\15-WORKSPACE - ESTUDIO - DATOS\\IdeaProjects\\SeleniumAcademy\\src\\test\\java\\clase115\\Features\\OutlinedScenarios.feature",
        glue = "clase115/StepDef",
        plugin = {"pretty", "html:target/cucumber-reports" },
        tags = "@compras"
)

public class runner {
}

