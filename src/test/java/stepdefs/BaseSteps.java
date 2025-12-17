package stepdefs;

import context.Context;
import context.ScenarioContext;
import org.openqa.selenium.WebDriver;

public class BaseSteps {
    /**
     * Класс-хранилище тестовых данных
     */
    public ScenarioContext context;

    /**
     * Хромдрайвер
     */
    public WebDriver chromeDriver;

    public BaseSteps() {
        this.context = ScenarioContext.getInstance();
        this.chromeDriver = (WebDriver) this.context.get(Context.DRIVER);
    }
}
