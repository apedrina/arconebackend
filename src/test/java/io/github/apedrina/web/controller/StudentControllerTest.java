package io.github.apedrina.web.controller;

import io.github.apedrina.web.mock.MockServerManager;
import io.github.apedrina.web.mock.MockUtilTest;
import io.github.apedrina.web.mock.utils.FixtureJsonUtils;
import io.github.apedrina.web.mock.utils.FixtureUtils;
import io.github.apedrina.web.model.StudentRequest;
import io.github.apedrina.web.model.StudentResponse;
import io.github.apedrina.web.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.mockserver.model.JsonBody.json;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest extends MockUtilTest {

    private static final String STUDENT_ADD_URI = "/arcone/student";

    @Autowired
    MockServerManager mockServerManager;

    @Autowired
    StudentService studentService;

    @Test
    public void should_retrieve_all_students() {
        String uri = url + STUDENT_ADD_URI;
        StudentRequest request = FixtureUtils.createStudentRequest();
        request.setAddress("XX");

        HttpEntity httpEntity = new HttpEntity(request);

        MockServerClient client = mockServerManager.getClient();
        client.when(HttpRequest.request()
                        .withMethod("GET")
                        .withPath(uri))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatus.ACCEPTED.value())
                        .withHeader(Header.header("Content-Type", "application/json"))
                        .withBody(json(FixtureJsonUtils.studentJson())));


        ResponseEntity<StudentResponse> entity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<StudentResponse>() {
                });

        assertEquals(entity.getStatusCode(), HttpStatus.CREATED);

    }

    @Test
    public void should_add_a_student() {
        String uri = url + STUDENT_ADD_URI;
        StudentRequest request = FixtureUtils.createStudentRequest();
        request.setAddress("XX");

        HttpEntity httpEntity = new HttpEntity(request);

        MockServerClient client = mockServerManager.getClient();
        client.when(HttpRequest.request()
                        .withMethod("POST")
                        .withPath(uri))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatus.ACCEPTED.value())
                        .withHeader(Header.header("Content-Type", "application/json"))
                        .withBody(""));


        ResponseEntity<StudentResponse> entity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<StudentResponse>() {
                });

        assertEquals(entity.getStatusCode(), HttpStatus.CREATED);

    }

    @Test(expected = HttpClientErrorException.class)
    public void should_not_allowed_add_a_student_with_repetead_address() {
        String uri = url + STUDENT_ADD_URI;
        StudentRequest request = FixtureUtils.createStudentRequest();

        HttpEntity httpEntity = new HttpEntity(request);

        MockServerClient client = mockServerManager.getClient();
        client.when(HttpRequest.request()
                        .withMethod("POST")
                        .withPath(uri))
                .respond(HttpResponse.response()
                        .withStatusCode(HttpStatus.ACCEPTED.value())
                        .withHeader(Header.header("Content-Type", "application/json"))
                        .withBody(""));


        ResponseEntity<StudentResponse> entity = this.restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<StudentResponse>() {
                });

    }


}