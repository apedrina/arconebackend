package io.github.apedrina.web.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    public static void start() {
        mockServer = ClientAndServer.startClientAndServer(9999);
        client = new MockServerClient("localhost", 9999);

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
        }
        return this.client;

    }

}
