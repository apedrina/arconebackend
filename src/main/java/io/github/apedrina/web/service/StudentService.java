package io.github.apedrina.web.service;

import io.github.apedrina.web.controller.StudentController;
import io.github.apedrina.web.model.Student;
import io.github.apedrina.web.model.StudentRequest;
import io.github.apedrina.web.model.StudentResponse;
import io.github.apedrina.web.model.error.StudentBusinessException;
import io.github.apedrina.web.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentResponse addUser(StudentRequest studentRequest) {
        if (studentRepository.findByAddress(studentRequest.getAddress()).size() > 0) {
            throw new StudentBusinessException(StudentBusinessException.NOT_UNIQUE_ADDRESS);

        }
        var student = Student.builder()
                .dateOfBirth(studentRequest.getDateOfBirth())
                .phoneNumber(studentRequest.getPhoneNumber())
                .lastName(studentRequest.getLastName())
                .name(studentRequest.getName())
                .address(studentRequest.getAddress())
                .build();

        studentRepository.save(student);

        return buildStudentResponse("User added with success");

    }

    public void validar(StudentRequest studentRequest) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<String> errors = new ArrayList<>();
        Set<ConstraintViolation<StudentRequest>> validate = validator.validate(studentRequest);
        if (validate.size() > 0) {
            for (ConstraintViolation<StudentRequest> constraintViolation : validate) {
                log.error(constraintViolation.getMessage());
                errors.add(constraintViolation.getMessage());

            }
            BeanPropertyBindingResult result = new BeanPropertyBindingResult(studentRequest, StudentController.STUDENT_REQUEST);
            SpringValidatorAdapter adapter = new SpringValidatorAdapter(validator);
            adapter.validate(studentRequest, result);

            if (result.hasErrors()) {
                throw new StudentBusinessException(errors.toString());

            }

        }

        validacoesAdicionais(studentRequest);

    }

    private boolean validacoesAdicionais(StudentRequest studentRequest) {
        if (!isValidFormat(studentRequest.getDateOfBirth())) {
            throw new StudentBusinessException("Date of birth not in valid format, valid patterns should be like: (yyyy-MM-dd)");

        }

        return true;

    }

    private static boolean isValidFormat(String value) {
        try {
            DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(value);
            return true;

        } catch (DateTimeParseException dtpe) {
            return false;
        }
    }

    private StudentResponse buildStudentResponse(String msg) {
        return StudentResponse.builder()
                .status("201")
                .statusDetails(msg)
                .build();

    }

}
