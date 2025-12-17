package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runner-класс для запуска Cucumber-тестов через JUnit.
 * <p>
 * Настраивает параметры выполнения Cucumber:
 * путь до feature-файлов, пакеты со step definitions и hook'ами,
 * используемые плагины для вывода и генерации отчётов, а также фильтрацию сценариев по тегам.
 *
 * @author Сергей Лужин
 */
@CucumberOptions(
        strict = false,
        // повышение читаемости вывода в консоли, заменяет нечитаемые символы
        monochrome = true,
        // плагины для форматирования вывода, отчетов
        plugin = {"pretty", "io.qameta.allure.cucumber5jvm.AllureCucumber5Jvm", "json:target/cucumber-report/report.json"},
        // указатель на корневой пакет, где лежат файлы .features
        features = "src/test/java/features",
        // указатель на корневой пакет, где лежат реализации шагов (stepdefs)
        glue = {"stepdefs", "hooks"},
        //Указатель тегов для запуска тестов. Префиксы ~/not исключают тест из списка запускаемых тестов, например ~@fail или not в зависимости от версиии cucumber;
        tags = "not @excluded"
)

@RunWith(Cucumber.class)
public class CucumberRunner {
}