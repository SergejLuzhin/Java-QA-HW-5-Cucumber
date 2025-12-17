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

public class YandexMarketSteps extends BaseSteps {

    @Given("пользователь открыл Яндекс Маркет")
    public void userOpensYandexMarket() {
        chromeDriver.get(testProperties.yandexMarketUrl());
        context.put(Context.MARKET_PAGE, new YandexMarketPage());
    }

    @When("он переходит в каталог")
    public void userOpensCatalog() {
        context.<YandexMarketPage>get(Context.MARKET_PAGE).clickOnCatalogButton();

    }

    @When("он наводит курсор на раздел {string}")
    public void userHoversOnCategory(String category) {
        context.<YandexMarketPage>get(Context.MARKET_PAGE).hoverOnCategoryInCatalog(category);
    }

    @When("он выбирает подкатегорию {string}")
    public void userChoosesSubcategory(String subcategory) {
        context.<YandexMarketPage>get(Context.MARKET_PAGE).clickOnSubcategoryInCatalog(subcategory);
    }

    @Then("открыта страница раздела {string}")
    public void correctSubcategoryPageIsOpened(String subcategory) {
        Assert.assertTrue(
                "Тайтл " + Driver.getWebDriver().getTitle() + " на сайте не соответствует категории " + subcategory,
                Driver.getWebDriver().getTitle().contains(subcategory));
    }

    @When("пользователь в фильтрах выбирает производителей {string}")
    public void userChoosesBrandInFilters(String brandsRaw) {
        List<String> brands = unpack(brandsRaw);
        context.<YandexMarketPage>get(Context.MARKET_PAGE).clickBrandCheckbox(brands);
    }

    @When("дожидается прогрузки всех результатов поиска")
    public void userWaitsForProductsToLoad() {
        context.<YandexMarketPage>get(Context.MARKET_PAGE).scrollToBottomAndCollectAllProducts();
    }

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
