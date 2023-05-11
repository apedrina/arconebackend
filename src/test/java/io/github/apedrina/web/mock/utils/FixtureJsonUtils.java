package io.github.apedrina.web.mock.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Slf4j
public class FixtureJsonUtils {

    public static ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json().build();

    }

    private static ObjectMapper objectMapper = objectMapper();

    public static String studentJson() {
        var request = FixtureUtils.createStudentRequest();

        try {
            return objectMapper.writeValueAsString(request);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);

        }

    }

}
