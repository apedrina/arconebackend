package io.github.apedrina.web.advice;

import io.github.apedrina.web.controller.StudentController;
import io.github.apedrina.web.controller.payload.response.ArcOneResponse;
import io.github.apedrina.web.model.error.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = {StudentController.class})
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ArcOneResponse> handleBusinessException(BusinessException ex, WebRequest request) {
        ArcOneResponse arcOneResponse = new ArcOneResponse();
        arcOneResponse.setStatusDetails(ex.getMessage());
        arcOneResponse.setStatus("400");

        return new ResponseEntity<>(arcOneResponse, HttpStatus.BAD_REQUEST);

    }

}