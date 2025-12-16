package hooks;

import helpers.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setUp() {
        Driver.create();
    }

    @After
    public void tearDown() {
        if (Driver.getWebDriver() != null) {
            Driver.getWebDriver().quit();
        }
    }
}