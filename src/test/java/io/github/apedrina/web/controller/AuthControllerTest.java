package io.github.apedrina.web.controller;

import io.github.apedrina.web.controller.payload.request.LoginRequest;
import io.github.apedrina.web.controller.payload.response.JwtResponse;
import io.github.apedrina.web.mock.MockServerManager;
import io.github.apedrina.web.mock.MockUtilTest;
import io.github.apedrina.web.mock.utils.FixtureUtils;
import io.github.apedrina.web.vo.UserVO;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthControllerTest extends MockUtilTest {

    @Autowired
    MockServerManager mockServerManager;

    UserVO register;

    @Test
    public void Ashould_register_an_user() {
        register = register(FixtureUtils.createSignupRequest());

        assertNotNull(register);

    }

    @Test
    public void Bshould_authenticateUser() {
        LoginRequest request = FixtureUtils.createLoginRequest();
        JwtResponse jwtResponse = login(request);

        assertNotNull(jwtResponse);

    }



}
