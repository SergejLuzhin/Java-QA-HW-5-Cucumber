package context;

import java.util.HashMap;
import java.util.Map;

/**
 * Контекст сценария для хранения и обмена данными между шагами тестов.
 * <p>
 * Класс реализует единое хранилище объектов, используемых в рамках одного
 * сценария (например, WebDriver, Page Object, тестовые данные).
 * <p>
 * Реализован как Singleton, что гарантирует наличие одного общего экземпляра
 * контекста на протяжении выполнения сценария.
 *
 * @author Сергей Лужин
 */
public class ScenarioContext {

    private final Map<Context, Object> context;

    private static ScenarioContext INSTANCE;

    /**
     * Приватный конструктор.
     * <p>
     * Инициализирует внутреннее хранилище данных контекста.
     * Используется исключительно в рамках паттерна Singleton.
     *
     * @author Сергей Лужин
     */
    private ScenarioContext() {
        context = new HashMap<>();
    }

    /**
     * Возвращает единственный экземпляр {@link ScenarioContext}.
     * <p>
     * Если экземпляр ещё не был создан, он будет инициализирован
     * при первом вызове метода.
     *
     * @return singleton-экземпляр {@link ScenarioContext}
     *
     * @author Сергей Лужин
     */
    public static ScenarioContext getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ScenarioContext();
        }

        return INSTANCE;
    }

    /**
     * Сохраняет объект в контекст по заданному ключу.
     *
     * @param key   ключ контекста
     * @param value объект, который необходимо сохранить
     *
     * @author Сергей Лужин
     */
    public void put(Context key, Object value) {
        context.put(key, value);
    }

    /**
     * Возвращает объект из контекста по указанному ключу.
     * <p>
     * Метод использует приведение типов, поэтому предполагается,
     * что вызывающая сторона знает ожидаемый тип возвращаемого объекта.
     *
     * @param key ключ контекста
     * @param <T> ожидаемый тип возвращаемого объекта
     * @return объект из контекста, приведённый к указанному типу
     *
     * @author Сергей Лужин
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Context key) {
        return (T) context.get(key);
    }

    /**
     * Проверяет наличие значения в контексте по заданному ключу.
     *
     * @param key ключ контекста
     * @return {@code true}, если значение с таким ключом присутствует,
     *         иначе {@code false}
     *
     * @author Сергей Лужин
     */
    public boolean contains(Context key) {
        return context.containsKey(key);
    }
}
