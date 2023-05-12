package io.github.apedrina.web.model.error;

public class BusinessException extends RuntimeException {

    public static final String AGE_NOT_ALLOWED = "The student should has more than 16 years old";
    public static final String NOT_UNIQUE_ADDRESS = "The address already exist in our DataBase";
    public static final String NOT_UNIQUE_COURSE = "The Course name should be unique";

    public static final String STUDENT_NOT_FOUND = "The Student not found";

    public BusinessException() {

    }

    public BusinessException(Throwable ex) {
        super(ex);

    }

    public BusinessException(String ex) {
        super(ex);

    }


}
