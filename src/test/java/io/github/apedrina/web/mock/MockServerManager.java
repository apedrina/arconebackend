package io.github.apedrina.web.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.apedrina.web.mock.scenario.LocalServicesScenarios;
import io.github.apedrina.web.mock.scenario.Scenario;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MockServerManager {

    private static ClientAndServer mockServer;

    @Value("${mock.server.host}")
    private String host;

    @Value("${mock.server.port}")
    private int port;

    @Autowired
    private ObjectMapper mapper;

    private static MockServerClient client;

    private static List<Scenario> scenarios = Arrays.asList(new LocalServicesScenarios());

    public static void start() {
        mockServer = ClientAndServer.startClientAndServer(9999);
        client = new MockServerClient("localhost", 9999);
        startScenarios(client);

    }

    private static void startScenarios(MockServerClient client) {
        scenarios.forEach(s -> s.init(client));

    }

    public void shutdown() {
        mockServer.stop();
    }

    public static void tearDown() {
        mockServer.stop();
    }

    public MockServerClient getClient() {
        if (client == null) {
            client = new MockServerClient("localhost", 9999);
            startScenarios(client);
        }
        return this.client;

    }

    public void addScenario(Scenario scenario) {
        scenario.init(this.client);

    }

}
