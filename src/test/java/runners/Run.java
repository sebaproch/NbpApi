package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/getExchangeRatesFromNbp.feature",
        glue = {"StepDefinitions"},
        plugin = {"pretty", "html:target/cucumber-reports.html"}
)

public class Run extends AbstractTestNGCucumberTests {
}
