package io.github.apedrina.web.mock.scenario;

import org.mockserver.client.MockServerClient;

public interface Scenario {

    void init(MockServerClient mockServerClient);

}
