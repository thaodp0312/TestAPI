package com.omc.api.barcelona.single;

import com.google.inject.Inject;
import com.omc.api.barcelona.model.RequestInfo;
import vinid.api.rest.RestMethod;
import vinid.api.rest.RestRequest;

public class Availables {
    //    curl --location --request GET 'http://localhost:8080/teamgroup'
    @Inject
    private RequestInfo requestInfo;

    private String hostname = "http://localhost:8080";
    private String path = "/players/available";
    private String method = "GET";
    private RestRequest request;

    public RestRequest iGetAvailables() {
        request = new RestRequest(hostname, path, RestMethod.getMethod(method));
        return request;
    }
}