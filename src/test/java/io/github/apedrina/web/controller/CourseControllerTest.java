package io.github.apedrina.web.controller;

import io.github.apedrina.web.controller.payload.request.LoginRequest;
import io.github.apedrina.web.controller.payload.request.SignupRequest;
import io.github.apedrina.web.controller.payload.response.ArcOneResponse;
import io.github.apedrina.web.controller.payload.response.JwtResponse;
import io.github.apedrina.web.mock.MockServerManager;
import io.github.apedrina.web.mock.MockUtilTest;
import io.github.apedrina.web.mock.utils.FixtureJsonUtils;
import io.github.apedrina.web.mock.utils.FixtureUtils;
import io.github.apedrina.web.vo.CourseVO;
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
public class CourseControllerTest extends MockUtilTest {

    private static final String URI = "/arcone/course";

    @Autowired
    MockServerManager mockServerManager;

    UserVO register;

    JwtResponse jwtResponse;

    @Test
    public void Ashould_add_a_course() {
        SignupRequest signupRequest = SignupRequest.builder()
                .username("userCourse1")
                .password("123456")
                .email("usercourse1@gmail.com")
                .build();
        register = register(signupRequest);

        LoginRequest loginRequest = LoginRequest.builder()
                .username(signupRequest.getUsername())
                .password(signupRequest.getPassword())
                .build();
        jwtResponse = login(loginRequest);

        String uri = url + URI;
        CourseVO request = CourseVO.builder()
                .name("Test Course")
                .description("description")
                .build();

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


        ResponseEntity<CourseVO> entity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<CourseVO>() {
                });

        assertEquals(entity.getStatusCode(), HttpStatus.CREATED);

    }

    @Test(expected = HttpClientErrorException.class)
    public void Bshould_not_allowed_add_a_course_with_repetead_name() {
        String uri = url + URI;
        CourseVO request = CourseVO.builder()
                .name("Test Course")
                .description("description")
                .build();

        SignupRequest signupRequest = SignupRequest.builder()
                .username("userCourse2")
                .password("123456")
                .email("usercourse1@gmail.com")
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
    public void Cshould_retrieve_all_courses() {
        String uri = url + URI;
        StudentVO request = FixtureUtils.createStudentVO();
        request.setAddress("addressAll");

        SignupRequest signupRequest = SignupRequest.builder()
                .username("userCourse3")
                .password("123456")
                .email("usercourse3@gmail.com")
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