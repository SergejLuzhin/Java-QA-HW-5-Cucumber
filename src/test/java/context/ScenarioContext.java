package context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private final Map<Context, Object> context;

    private static ScenarioContext INSTANCE;

    private ScenarioContext() {
        context = new HashMap<>();
    }

    /**
     * Реализован паттерн SingleTon для содержания едиснтвенного,уникального объекта класса
     * Это необходимо для обращения к единому хранилищу данных
     */
    public static ScenarioContext getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ScenarioContext();
        }

        return INSTANCE;
    }

    public void put(Context key, Object value) {
        context.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Context key) {
        return (T) context.get(key);
    }

    public boolean contains(Context key) {
        return context.containsKey(key);
    }
}
