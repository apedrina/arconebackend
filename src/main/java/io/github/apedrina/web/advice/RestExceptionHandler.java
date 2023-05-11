package io.github.apedrina.web.advice;

import io.github.apedrina.web.controller.StudentController;
import io.github.apedrina.web.model.StudentRequest;
import io.github.apedrina.web.model.StudentResponse;
import io.github.apedrina.web.model.error.GenericException;
import io.github.apedrina.web.model.error.StudentBusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(basePackageClasses = {StudentController.class})
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String EC_NOT_FOUND = "Estabelecimento não encontrado";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        StudentRequest studentRequest = (StudentRequest) request.getAttribute(StudentController.STUDENT_REQUEST, RequestAttributes.SCOPE_REQUEST);
        final List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(e -> {
            errors.add(e.getDefaultMessage());

        });
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setStatusDetails(errors.toString());
        studentResponse.setStatus(status.toString());

        return new ResponseEntity<>(studentResponse, status);

    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<StudentResponse> handleAdapterGenericExceptionException(GenericException ex, WebRequest request) {
        StudentRequest studentRequest = (StudentRequest) request.getAttribute(StudentController.STUDENT_REQUEST, RequestAttributes.SCOPE_REQUEST);
        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setStatus("500");

        if (ex.getMessage().equalsIgnoreCase(EC_NOT_FOUND)) {
            studentResponse.setStatusDetails(EC_NOT_FOUND);

            return new ResponseEntity<>(studentResponse, HttpStatus.BAD_REQUEST);

        } else {
            studentResponse.setStatusDetails(ex.getMessage());

            return new ResponseEntity<>(studentResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @ExceptionHandler(StudentBusinessException.class)
    public ResponseEntity<StudentResponse> handleStudentBussinesException(StudentBusinessException ex, WebRequest request) {
        StudentRequest studentRequest = (StudentRequest) request.getAttribute(StudentController.STUDENT_REQUEST, RequestAttributes.SCOPE_REQUEST);

        StudentResponse studentResponse = new StudentResponse();
        studentResponse.setStatusDetails(ex.getMessage());
        studentResponse.setStatus("400");

        return new ResponseEntity<>(studentResponse, HttpStatus.BAD_REQUEST);

    }

}