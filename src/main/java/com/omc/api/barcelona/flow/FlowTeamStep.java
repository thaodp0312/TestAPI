package com.omc.api.barcelona.flow;

import com.google.inject.Inject;
import com.omc.api.barcelona.exception.*;
import com.omc.api.barcelona.single.*;
import io.restassured.path.json.JsonPath;
import org.springframework.http.HttpInputMessage;
import vinid.api.rest.RestResponse;

import java.util.List;

public class FlowTeamStep {
    @Inject
    private Player apiPlayer;
    @Inject
    private Team apiTeam;
    @Inject
    private Substitute apiSubstitute;
    @Inject
    private Finish apiFinish;
    @Inject
    private Availables apiAvailables;

    public RestResponse fullFlow(int times) throws Exception {


        RestResponse player = apiPlayer.listPlayer().send();
        if (player.extract().statusCode() != 200) {
            throw new PlayerException("get list failed  " + player.extract().statusCode());
        }

        RestResponse team = apiTeam.getTeam().send();
        if (team.extract().statusCode() != 200) {
            throw new TeamException("get team failed  " + team.extract().statusCode());
        }
        RestResponse available = apiAvailables.iGetAvailables().send();
        if (available.extract().statusCode() != 200) {
            throw new AvailableException("get available failed  " + available.extract().statusCode());
        }

        List<Integer> numbers = team.extract().jsonPath().getList("number");
        List<String> positions = available.extract().jsonPath().getList("position");

        for (int i = 0; i < times; i++) {
            RestResponse substitute = apiSubstitute.doSubstitute(numbers.remove(0), positions.remove(0)).send();
            if (substitute.extract().statusCode() != 200) {
                throw new SubstituteException("get substitute failed  " + substitute.extract().statusCode());
            }
        }
        RestResponse finish = apiFinish.getFinish().send();
        if (finish.extract().statusCode() != 200) {
            throw new FinishException("get finish failed  " + finish.extract().statusCode());
        }

        return finish;

    }

}
