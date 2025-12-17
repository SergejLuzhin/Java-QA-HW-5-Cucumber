package hooks;

import context.Context;
import context.ScenarioContext;
import helpers.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

/**
 * Hook-методы Cucumber для подготовки и завершения UI-сценариев.
 * <p>
 * Класс отвечает за жизненный цикл {@link WebDriver} для сценариев,
 * помеченных тегом {@code @ui}:
 * создаёт драйвер перед выполнением сценария и корректно завершает его после.
 * <p>
 * Экземпляр драйвера сохраняется в {@link ScenarioContext} по ключу {@link Context#DRIVER},
 * чтобы он был доступен в step definitions.
 *
 * @author Сергей Лужин
 */
public class Hooks {

    /**
     * Инициализирует {@link WebDriver} перед выполнением UI-сценариев.
     * <p>
     * Создаёт драйвер через {@link Driver#create()}, затем помещает его в
     * {@link ScenarioContext} по ключу {@link Context#DRIVER}.
     *
     * @author Сергей Лужин
     */
    @Before(value = "@ui")
    public void setUpWebDriver() {
        Driver.create();
        ScenarioContext context = ScenarioContext.getInstance();
        context.put(Context.DRIVER, Driver.getWebDriver());
    }

    /**
     * Завершает работу {@link WebDriver} после выполнения UI-сценариев.
     * <p>
     * Извлекает драйвер из {@link ScenarioContext} по ключу {@link Context#DRIVER}
     * и вызывает {@link WebDriver#quit()} для закрытия браузера и освобождения ресурсов.
     *
     * @author Сергей Лужин
     */
    @After(value = "@ui")
    public void quitWebDriver() {
        WebDriver driver = ScenarioContext.getInstance().get(Context.DRIVER);
        driver.quit();
    }
}