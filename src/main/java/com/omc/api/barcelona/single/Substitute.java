package com.omc.api.barcelona.single;

import com.google.inject.Inject;
import com.omc.api.barcelona.model.RequestInfo;
import vinid.api.rest.RestMethod;
import vinid.api.rest.RestRequest;


public class Substitute {
    private RestRequest request;

    @Inject
    private RequestInfo requestInfo;


    public RestRequest doSubstitute(int number, String position) {

        request = new RestRequest("http://localhost:8080", "/substitute/"+number+"/"+position, RestMethod.POST);
        return request;
    }

}
