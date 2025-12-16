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
import static pages.YandexMarketPage.*;

public class YandexMarketSteps {

YandexMarketPage yandexMarketPageBeforeSearch;
YandexMarketPage yandexMarketPageAfterSearch;

    @Given("пользователь открыл Яндекс Маркет")
    public void userOpensYandexMarket() {
        Driver.getWebDriver().get(testProperties.yandexMarketUrl());
        yandexMarketPageBeforeSearch = new YandexMarketPage();
    }

    @When("он переходит в каталог")
    public void userOpensCatalog() {
        yandexMarketPageBeforeSearch.clickOnCatalogButton();
    }

    @When("он наводит курсор на раздел {string}")
    public void userHoversOnCategory(String category) {
        yandexMarketPageBeforeSearch.hoverOnCategoryInCatalog(category);
    }

    @When("он выбирает подкатегорию {string}")
    public void userChoosesSubcategory(String subcategory) {
        yandexMarketPageBeforeSearch.clickOnSubcategoryInCatalog(subcategory);
    }

    @Then("открыта страница раздела {string}")
    public void correctSubcategoryPageIsOpened(String subcategory) {
        Assert.assertTrue(
                "Тайтл " + Driver.getWebDriver().getTitle() + " на сайте не соответствует категории " + subcategory,
                Driver.getWebDriver().getTitle().contains(subcategory));
    }

    @When("пользователь в фильтрах выбирает производителей {string}")
    public void userChoosesBrandInFilters(String brandsRaw) {
        List<String> brands = Arrays.stream(brandsRaw.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());

        yandexMarketPageBeforeSearch.clickBrandCheckbox(brands);
    }

    @When("дожидается прогрузки всех результатов поиска")
    public void userWaitsForProductsToLoad() {
        yandexMarketPageBeforeSearch.scrollToBottomAndCollectAllProducts();
    }

    @Then("в результатах отображаются только смартфоны производителей {string}")
    public void correctProductsAfterFilters(String brandsRaw) {
        List<String> brands = Arrays.stream(brandsRaw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        List<String> wrongTitles = new ArrayList<>();

        for (Product product : yandexMarketPageBeforeSearch.productsOnPage) {
            boolean containsBrand = brands.stream()
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
