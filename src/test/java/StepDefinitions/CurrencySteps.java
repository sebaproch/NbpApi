package StepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import org.helpers.ConversionHelpers;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class CurrencySteps {
    private static final String API_URL = "http://api.nbp.pl/api/exchangerates/tables/A";
    private Response response;
    private List<Map<String, Object>> rates;

    @Given("Pobierz kursy walut")
    public void fetchCurrencyRates() {
        response = given()
                .header("Accept", "application/json")
                .when()
                .get(API_URL)
                .then()
                .statusCode(200)
                .extract()
                .response();
        System.out.println(response.getBody().asString());
        rates = response.jsonPath().getList("[0].rates");
        Assert.assertNotNull(rates, "Rates list should not be null");
    }

    @Then("Wyswietl kurs dla waluty o kodzie {string}")
    public void displayCurrencyByCode(String code) {
        Map<String, Object> rate = rates.stream()
                .filter(r -> r.get("code").equals(code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Currency code not found: " + code));
        System.out.println("Currency " + code + " rate: " + rate.get("mid"));
    }

    @And("Wyswietl kurs dla waluty o nazwie {string}")
    public void displayCurrencyByName(String name) {
        Map<String, Object> rate = rates.stream()
                .filter(r -> r.get("currency").toString().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Currency name not found: " + name));

        System.out.println("Currency " + name);
    }

    @And("Wyswietl waluty o kursie powyzej {string}")
    public void displayCurrenciesAbove(String thresholdStr) {
        double threshold = Double.parseDouble(thresholdStr);
        List<Map<String, Object>> filteredRates = rates.stream()
                .filter(r -> {
                    Object midRate = r.get("mid");
                    Double rateValue = ConversionHelpers.convertToDouble(midRate);
                    return rateValue > threshold;
                }).toList();

        System.out.println("Currencies with rates above " + threshold + ":");
        filteredRates.forEach(r -> System.out.println(r.get("currency") + ": " + r.get("mid")));
    }

    @And("Wyswietl waluty o kursie ponizej {string}")
    public void displayCurrenciesBelow(String thresholdStr) {
        double threshold = Double.parseDouble(thresholdStr);
        List<Map<String, Object>> filteredRates = rates.stream()
                .filter(r -> {
                    Object midRate = r.get("mid");
                    Double rateValue = ConversionHelpers.convertToDouble(midRate);
                    return rateValue < threshold;
                }).toList();

        System.out.println("Currencies with rates below " + threshold + ":");
        filteredRates.forEach(r -> System.out.println(r.get("currency") + ": " + r.get("mid")));
    }
}

