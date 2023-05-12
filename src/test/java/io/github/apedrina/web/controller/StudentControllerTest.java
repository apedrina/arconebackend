package io.github.apedrina.web.controller;

import io.github.apedrina.web.controller.payload.request.LoginRequest;
import io.github.apedrina.web.controller.payload.request.SignupRequest;
import io.github.apedrina.web.controller.payload.response.ArcOneResponse;
import io.github.apedrina.web.controller.payload.response.JwtResponse;
import io.github.apedrina.web.mock.MockServerManager;
import io.github.apedrina.web.mock.MockUtilTest;
import io.github.apedrina.web.mock.utils.FixtureJsonUtils;
import io.github.apedrina.web.mock.utils.FixtureUtils;
import io.github.apedrina.web.vo.StudentVO;
import io.github.apedrina.web.vo.UserVO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.mockserver.model.JsonBody.json;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentControllerTest extends MockUtilTest {

    private static final String STUDENT_ADD_URI = "/arcone/student";

    @Autowired
    MockServerManager mockServerManager;

    UserVO register;

    JwtResponse jwtResponse;

    @Test
    public void Ashould_add_a_student() {
        SignupRequest signupRequest = SignupRequest.builder()
                .username("user1")
                .password("123456")
                .email("user1@gmail.com")
                .build();
        register = register(signupRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .username(signupRequest.getUsername())
                .password(signupRequest.getPassword())
                .build();
        jwtResponse = login(loginRequest);

        String uri = url + STUDENT_ADD_URI;
        StudentVO request = FixtureUtils.createStudentVO();
        request.setAddress("user1Address");

        HttpHeaders headers = createHeader(jwtResponse);
        HttpEntity httpEntity = new HttpEntity(request, headers);

        MockServerClient client = mockServerManager.getClient();
        client.when(HttpRequest.request()
                        .withMethod("POST")
                        .withPath(uri))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatus.ACCEPTED.value())
                        .withHeader(Header.header("Content-Type", "application/json"))
                        .withBody(""));


        ResponseEntity<ArcOneResponse> entity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<ArcOneResponse>() {
                });

        assertEquals(entity.getStatusCode(), HttpStatus.CREATED);

    }

    @Test(expected = HttpClientErrorException.class)
    public void Bshould_not_allowed_add_a_student_with_repetead_address() {
        String uri = url + STUDENT_ADD_URI;
        StudentVO request = FixtureUtils.createStudentVO();
        request.setAddress("user1Address");

        SignupRequest signupRequest = SignupRequest.builder()
                .username("user2")
                .password("123456")
                .email("user2@gmail.com")
                .build();
        register = register(signupRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .username(signupRequest.getUsername())
                .password(signupRequest.getPassword())
                .build();
        jwtResponse = login(loginRequest);

        HttpHeaders headers = createHeader(jwtResponse);
        HttpEntity httpEntity = new HttpEntity(request, headers);

        MockServerClient client = mockServerManager.getClient();
        client.when(HttpRequest.request()
                        .withMethod("POST")
                        .withPath(uri))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatus.ACCEPTED.value())
                        .withHeader(Header.header("Content-Type", "application/json"))
                        .withBody(""));


        ResponseEntity<ArcOneResponse> entity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<ArcOneResponse>() {
                });

    }

    @Test
    public void Cshould_retrieve_all_students() {
        String uri = url + STUDENT_ADD_URI;
        StudentVO request = FixtureUtils.createStudentVO();
        request.setAddress("addressAll");

        SignupRequest signupRequest = SignupRequest.builder()
                .username("user3")
                .password("123456")
                .email("user3@gmail.com")
                .build();
        register = register(signupRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .username(signupRequest.getUsername())
                .password(signupRequest.getPassword())
                .build();
        jwtResponse = login(loginRequest);

        HttpHeaders headers = createHeader(jwtResponse);
        HttpEntity httpEntity = new HttpEntity(request, headers);

        MockServerClient client = mockServerManager.getClient();
        client.when(HttpRequest.request()
                        .withMethod("GET")
                        .withPath(uri))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatus.ACCEPTED.value())
                        .withHeader(Header.header("Content-Type", "application/json"))
                        .withBody(json(FixtureJsonUtils.studentJson())));


        ResponseEntity<ArcOneResponse> entity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<ArcOneResponse>() {
                });

        assertEquals(entity.getStatusCode(), HttpStatus.CREATED);

    }


}