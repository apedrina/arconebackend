package io.github.apedrina.web.mock.utils;

import io.github.apedrina.web.model.Student;
import io.github.apedrina.web.model.StudentRequest;

public class FixtureUtils {

    public static final String ADDRESS = "1234 NW Bobcat Lane, St. Robert, MO 65584-5678";
    public static final String DATE = "1980-08-23";
    public static final String EMAIL = "robertson@gmail.com";
    public static final String NAME = "Robertson";
    public static final String LAST_NAME = "Martin";
    public static final long PHONE = 2124567890l;

    public static StudentRequest createStudentRequest() {
        return StudentRequest.builder()
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
}