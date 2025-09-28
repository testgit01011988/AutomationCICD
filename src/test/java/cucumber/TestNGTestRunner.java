package cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
//cucumber->  TestNG, junit

@CucumberOptions(features="src/test/java/cucumber",glue="SeleniumFramework.stepDefinitions",
monochrome=true, tags= "@ErrorValidation", plugin= {"html:target/cucumber.html"})
public class TestNGTestRunner extends AbstractTestNGCucumberTests{

	
}
