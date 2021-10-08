package com.omc.api.barcelona.test;

import com.google.inject.Inject;
import com.omc.api.barcelona.flow.FlowTeamStep;
import com.omc.api.barcelona.model.RequestInfo;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import vinid.api.rest.RestResponse;

public class FlowTeam {
    @Inject
    FlowTeamStep flowTeamStep;
    @Inject
    private RequestInfo requestInfo;

    private static RestResponse response;

    @When("I get and create and substitute {int} times and finish")

    public void iGetFlow(int times) throws Exception {
        response = flowTeamStep.fullFlow(times);
    }

    @Then("I verify status code {int}")
    public void iVerifyStatusCode(int expectedCode) {
        Assert.assertEquals(expectedCode, response.extract().statusCode());
    }

}
