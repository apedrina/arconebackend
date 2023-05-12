package io.github.apedrina.web.advice;

import io.github.apedrina.web.controller.StudentController;
import io.github.apedrina.web.controller.payload.response.ArcOneResponse;
import io.github.apedrina.web.model.error.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice(basePackageClasses = {StudentController.class})
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(e -> {
            errors.add(e.getDefaultMessage());

        });
        ArcOneResponse arcOneResponse = new ArcOneResponse();
        arcOneResponse.setStatusDetails(errors.toString());
        arcOneResponse.setStatus(status.toString());

        return new ResponseEntity<>(arcOneResponse, status);

    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ArcOneResponse> handleBusinessException(BusinessException ex, WebRequest request) {
        ArcOneResponse studentResponse = new ArcOneResponse();
        studentResponse.setStatusDetails(ex.getMessage());
        studentResponse.setStatus("400");

        return new ResponseEntity<>(studentResponse, HttpStatus.BAD_REQUEST);

    }

}