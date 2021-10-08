package com.omc.api.barcelona.single;

import com.google.inject.Inject;
import com.omc.api.barcelona.model.RequestInfo;
import vinid.api.rest.RestMethod;
import vinid.api.rest.RestRequest;

public class Player {


    private RestRequest request;

    @Inject
    private RequestInfo requestInfo;


    public RestRequest listPlayer() {

        request = new RestRequest("http://localhost:8080", "/players", RestMethod.GET);
            return request;
    }

}
