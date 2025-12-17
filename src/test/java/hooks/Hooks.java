package hooks;

import context.Context;
import context.ScenarioContext;
import helpers.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before(value = "@ui")
    public void setUpWebDriver() {
        Driver.create();
        ScenarioContext context = ScenarioContext.getInstance();
        context.put(Context.DRIVER, Driver.getWebDriver());
    }

    @After(value = "@ui")
    public void quitWebDriver() {
        WebDriver driver = ScenarioContext.getInstance().get(Context.DRIVER);
        driver.quit();
    }
}