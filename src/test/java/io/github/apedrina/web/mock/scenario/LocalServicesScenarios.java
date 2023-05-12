package io.github.apedrina.web.mock.scenario;

import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

public class LocalServicesScenarios implements Scenario {

    @Override
    public void init(MockServerClient client) {

        client.when(HttpRequest.request()
                        .withMethod("POST")
                        .withPath("/"))
                .respond(HttpResponse.response()
                        .withStatusCode(200)
                        .withHeader(Header.header("Content-Type", "application/json"))
                        .withBody(""));//json(FixtureJsonUtils.responseEfetuarPushJson())));


    }

}
