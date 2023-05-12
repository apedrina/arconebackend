package io.github.apedrina.web.mock;

import io.github.apedrina.web.controller.payload.request.LoginRequest;
import io.github.apedrina.web.controller.payload.request.SignupRequest;
import io.github.apedrina.web.controller.payload.response.JwtResponse;
import io.github.apedrina.web.mock.utils.FixtureJsonUtils;
import io.github.apedrina.web.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockserver.model.JsonBody.json;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MockUtilTest {

    @Autowired
    MockServerManager mockServerManager;

    @Value("${test.url}")
    public String url;

    @Autowired
    public RestTemplate restTemplate;

    @LocalServerPort
    public String port;

    @AfterClass
    public static void tearDown() {
        try {
            MockServerManager.tearDown();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @BeforeClass
    public static void setUp() {
        try {
            MockServerManager.start();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Before
    public void setUpEach() {
        url = url + ":" + port;
    }

    @Test
    public void init() {
        log.info("MockUtilTest init...");

        Assert.assertTrue(true);

    }

    public JwtResponse login(LoginRequest request) {
        String uri = url + "/api/auth/signin";

        HttpEntity httpEntity = new HttpEntity(request);

        MockServerClient client = mockServerManager.getClient();
        client.when(HttpRequest.request()
                        .withMethod("POST")
                        .withPath(uri))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatus.ACCEPTED.value())
                        .withHeader(Header.header("Content-Type", "application/json")));

        ResponseEntity<JwtResponse> entity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<JwtResponse>() {
                });


        assertEquals(entity.getStatusCode(), HttpStatus.OK);
        return entity.getBody();
    }


    public UserVO register(SignupRequest request) {
        String uri = url + "/api/auth/signup";

        HttpEntity httpEntity = new HttpEntity(request);

        MockServerClient client = mockServerManager.getClient();
        client.when(HttpRequest.request()
                        .withMethod("POST")
                        .withPath(uri))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatus.ACCEPTED.value())
                        .withHeader(Header.header("Content-Type", "application/json"))
                        .withBody(json(FixtureJsonUtils.userVO())));


        ResponseEntity<UserVO> entity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<UserVO>() {
                });

        assertEquals(entity.getStatusCode(), HttpStatus.OK);
        return entity.getBody();
    }

    public HttpHeaders createHeader(JwtResponse jwtResponse) {
        var headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtResponse.getToken());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

}
