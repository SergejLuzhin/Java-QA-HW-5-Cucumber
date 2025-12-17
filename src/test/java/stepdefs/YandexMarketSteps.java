package stepdefs;

import context.Context;
import entities.Product;
import helpers.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.YandexMarketPage;

import java.util.ArrayList;
import java.util.List;

import static config.Properties.testProperties;
import static helpers.RawOfStringsUnpacker.unpack;

/**
 * Step Definitions для сценариев работы с Яндекс Маркетом.
 * <p>
 * Класс содержит реализации шагов Cucumber, описывающих пользовательские действия:
 * открытие сайта, навигацию по каталогу, установку фильтров и проверку результатов.
 * <p>
 * Использует {@link context.ScenarioContext} для хранения состояния сценария
 * и {@link pages.YandexMarketPage} как Page Object.
 *
 * @author Сергей Лужин
 */
public class YandexMarketSteps extends BaseSteps {

    /**
     * Открывает сайт Яндекс Маркета и сохраняет объект страницы
     * в {@link context.ScenarioContext}.
     *
     * @author Сергей Лужин
     */
    @Given("пользователь открыл Яндекс Маркет")
    public void userOpensYandexMarket() {
        chromeDriver.get(testProperties.yandexMarketUrl());
        context.put(Context.MARKET_PAGE, new YandexMarketPage());
    }

    /**
     * Переходит в каталог товаров Яндекс Маркета.
     *
     * @author Сергей Лужин
     */
    @When("он переходит в каталог")
    public void userOpensCatalog() {
        context.<YandexMarketPage>get(Context.MARKET_PAGE).clickOnCatalogButton();
    }

    /**
     * Наводит курсор на указанный раздел каталога.
     *
     * @param category название категории каталога
     *
     * @author Сергей Лужин
     */
    @When("он наводит курсор на раздел {string}")
    public void userHoversOnCategory(String category) {
        context.<YandexMarketPage>get(Context.MARKET_PAGE).hoverOnCategoryInCatalog(category);
    }

    /**
     * Выбирает подкатегорию внутри текущей категории каталога.
     *
     * @param subcategory название подкатегории
     *
     * @author Сергей Лужин
     */
    @When("он выбирает подкатегорию {string}")
    public void userChoosesSubcategory(String subcategory) {
        context.<YandexMarketPage>get(Context.MARKET_PAGE).clickOnSubcategoryInCatalog(subcategory);
    }

    /**
     * Проверяет, что была открыта страница выбранного раздела,
     * сверяя заголовок страницы с названием подкатегории.
     *
     * @param subcategory ожидаемое название подкатегории, которое должно содержаться в заголовке вкладки
     *
     * @author Сергей Лужин
     */
    @Then("открыта страница раздела {string}")
    public void correctSubcategoryPageIsOpened(String subcategory) {
        Assert.assertTrue(
                "Тайтл " + Driver.getWebDriver().getTitle() + " на сайте не соответствует категории " + subcategory,
                Driver.getWebDriver().getTitle().contains(subcategory));
    }

    /**
     * Выбирает производителей в фильтрах каталога.
     * <p>
     * Строка с брендами предварительно разбирается в список
     * с помощью {@link helpers.RawOfStringsUnpacker}.
     *
     * @param brandsRaw строка с перечнем брендов
     *
     * @author Сергей Лужин
     */
    @When("пользователь в фильтрах выбирает производителей {string}")
    public void userChoosesBrandInFilters(String brandsRaw) {
        List<String> brands = unpack(brandsRaw);
        context.<YandexMarketPage>get(Context.MARKET_PAGE).clickBrandCheckbox(brands);
    }

    /**
     * Дожидается полной загрузки результатов поиска,
     * прокручивая страницу вниз и собирая все товары.
     *
     * @author Сергей Лужин
     */
    @When("дожидается прогрузки всех результатов поиска")
    public void userWaitsForProductsToLoad() {
        context.<YandexMarketPage>get(Context.MARKET_PAGE).scrollToBottomAndCollectAllProducts();
    }

    /**
     * Проверяет, что в результатах поиска отображаются только товары,
     * относящиеся к заданным производителям или брендам.
     * <p>
     * Проверка выполняется по наличию названия бренда или производителя
     * в заголовке товара (без учёта регистра).
     *
     * @param manufacturersRaw строка с перечнем производителей
     * @param brandsRaw строка с перечнем брендов
     *
     * @author Сергей Лужин
     */
    @Then("в результатах отображаются только смартфоны производителей {string} или их бренды {string}")
    public void correctProductsAfterFilters(String manufacturersRaw, String brandsRaw) {
        List<String> manufacturers = unpack(manufacturersRaw);
        List<String> brands = unpack(brandsRaw);
        List<String> combinedManufacturersAndBrands = new ArrayList<>(manufacturers);
        combinedManufacturersAndBrands.addAll(brands);

        List<String> wrongTitles = new ArrayList<>();

        for (Product product : context.<YandexMarketPage>get(Context.MARKET_PAGE).productsOnPage) {
            boolean containsBrand = combinedManufacturersAndBrands.stream()
                    .map(String::toLowerCase)
                    .anyMatch(brand -> product.getTitle().toLowerCase().contains(brand));

            if (!containsBrand) {
                wrongTitles.add(product.getTitle());
            }
        }

        Assert.assertTrue(
                "Были найдены товары не соответствующие заданным брендам: " + wrongTitles,
                wrongTitles.isEmpty());
    }
}
