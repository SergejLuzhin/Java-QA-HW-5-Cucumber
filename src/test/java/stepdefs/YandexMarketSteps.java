package stepdefs;

import entities.Product;
import helpers.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.YandexMarketPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static config.Properties.testProperties;
import static helpers.RawOfStringsUnpacker.unpack;

public class YandexMarketSteps {

    private YandexMarketPage yandexMarketPage;

    @Given("пользователь открыл Яндекс Маркет")
    public void userOpensYandexMarket() {
        Driver.getWebDriver().get(testProperties.yandexMarketUrl());
        yandexMarketPage = new YandexMarketPage();
    }

    @When("он переходит в каталог")
    public void userOpensCatalog() {
        yandexMarketPage.clickOnCatalogButton();
    }

    @When("он наводит курсор на раздел {string}")
    public void userHoversOnCategory(String category) {
        yandexMarketPage.hoverOnCategoryInCatalog(category);
    }

    @When("он выбирает подкатегорию {string}")
    public void userChoosesSubcategory(String subcategory) {
        yandexMarketPage.clickOnSubcategoryInCatalog(subcategory);
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
        yandexMarketPage.clickBrandCheckbox(brands);
    }

    @When("дожидается прогрузки всех результатов поиска")
    public void userWaitsForProductsToLoad() {
        yandexMarketPage.scrollToBottomAndCollectAllProducts();
    }

    @Then("в результатах отображаются только смартфоны производителей {string} или их бренды {string}")
    public void correctProductsAfterFilters(String manufacturersRaw, String brandsRaw) {
        List<String> manufacturers = unpack(manufacturersRaw);
        List<String> brands = unpack(brandsRaw);
        List<String> combinedManufacturersAndBrands = new ArrayList<>(manufacturers);
        combinedManufacturersAndBrands.addAll(brands);

        List<String> wrongTitles = new ArrayList<>();

        for (Product product : yandexMarketPage.productsOnPage) {
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
