package stepdefs;

import context.Context;
import context.ScenarioContext;
import org.openqa.selenium.WebDriver;

/**
 * Базовый класс для step definitions.
 * <p>
 * Инкапсулирует доступ к {@link ScenarioContext} и предоставляет общий {@link WebDriver},
 * извлекаемый из контекста по ключу {@link Context#DRIVER}. Наследники могут использовать
 * эти поля для выполнения шагов без повторяющегося кода инициализации.
 *
 * @author Сергей Лужин
 */
public class BaseSteps {
    /**
     * Контекст сценария, используемый как хранилище данных и общих объектов между шагами.
     */
    public ScenarioContext context;

    /**
     * Экземпляр {@link WebDriver}
     */
    public WebDriver chromeDriver;

    /**
     * Создаёт базовый объект шагов:
     * получает singleton-экземпляр {@link ScenarioContext} и извлекает {@link WebDriver}
     * из контекста.
     * <p>
     * Предполагается, что драйвер заранее был помещён в контекст в hook'ах (например, в {@code @Before}).
     *
     * @author Сергей Лужин
     */
    public BaseSteps() {
        this.context = ScenarioContext.getInstance();
        this.chromeDriver = (WebDriver) this.context.get(Context.DRIVER);
    }
}
