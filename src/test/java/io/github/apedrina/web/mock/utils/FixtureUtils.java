package io.github.apedrina.web.mock.utils;

import io.github.apedrina.web.controller.payload.request.LoginRequest;
import io.github.apedrina.web.controller.payload.request.SignupRequest;
import io.github.apedrina.web.model.Student;
import io.github.apedrina.web.vo.StudentVO;
import io.github.apedrina.web.vo.UserVO;

public class FixtureUtils {

    public static final String PASSWORD = "123456";
    public static final String ADDRESS = "1234 NW Bobcat Lane, St. Robert, MO 65584-5678";
    public static final String DATE = "1980-08-23";
    public static final String EMAIL = "test2@gmail.com";
    public static final String NAME = "Robertson";
    public static final String LAST_NAME = "Martin";
    public static final long PHONE = 2124567890l;
    public static final String USER_NAME = "test2";

    public static StudentVO createStudentVO() {
        return StudentVO.builder()
                .address(ADDRESS)
                .email(EMAIL)
                .name(NAME)
                .lastName(LAST_NAME)
                .phoneNumber(PHONE)
                .dateOfBirth(DATE)
                .build();

    }

    public static Student createStudent() {
        return Student.builder()
                .address(ADDRESS)
                .email(EMAIL)
                .name(NAME)
                .lastName(LAST_NAME)
                .phoneNumber(PHONE)
                .dateOfBirth(DATE)
                .build();


    }

    public static SignupRequest createSignupRequest() {
        return SignupRequest.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .username(USER_NAME)
                .build();
    }

    public static UserVO createUserVO() {
        return UserVO.builder()
                .email("test2@gmail.com")
                .password("123456")
                .username("test2")
                .build();
    }

    public static LoginRequest createLoginRequest() {
        return LoginRequest.builder()
                .password(PASSWORD)
                .username(USER_NAME)
                .build();
    }
}