package com.omc.api.barcelona.test;

import io.cucumber.datatable.DataTable;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import vinid.api.rest.RestMethod;
import vinid.api.rest.RestRequest;
import vinid.api.rest.RestResponse;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;
import java.io.FileNotFoundException;

@ScenarioScoped
public class TeamPattern {
    private RestRequest request;
    private String hostname = "http://localhost:8080";
    private String path = "/team/";
    private String method = "POST";
    private RestResponse response;

    @When("I send request with pattern {string}")

    public void iSendRequestWithPattern(String pattern) {

        request = new RestRequest(hostname, path + pattern, RestMethod.getMethod(method));
        response = request.send();

    }

    @Then("I verify status code {int} after request")
    public void iVerifyStatusCode(int expectedCode) {
        Assert.assertEquals(expectedCode, response.extract().statusCode());

    }

    @And("I verify  response body of request otp API")

    public void iVerifyResponseBodyOfRequestOtpAPI(DataTable dataTable) {
        Map<String, String> map = dataTable.asMap(String.class, String.class);
        Set<String> keys = map.keySet();
        for (String k : keys) {
            String jsonpath = k;
            String expectedValue = map.get(k);
            String actualValue = response.extract().jsonPath().getString("message");
            Assert.assertEquals(expectedValue, actualValue);
        }
    }
}


