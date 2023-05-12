package io.github.apedrina.web.service;

import io.github.apedrina.web.controller.StudentController;
import io.github.apedrina.web.controller.payload.response.ArcOneResponse;
import io.github.apedrina.web.model.Student;
import io.github.apedrina.web.model.error.BusinessException;
import io.github.apedrina.web.repository.StudentRepository;
import io.github.apedrina.web.vo.StudentVO;
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
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public ArcOneResponse addUser(StudentVO studentRequest) {
        if (studentRepository.findByAddress(studentRequest.getAddress()).size() > 0) {
            throw new BusinessException(BusinessException.NOT_UNIQUE_ADDRESS);

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

    public void validar(StudentVO studentRequest) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        List<String> errors = new ArrayList<>();
        Set<ConstraintViolation<StudentVO>> validate = validator.validate(studentRequest);
        if (validate.size() > 0) {
            for (ConstraintViolation<StudentVO> constraintViolation : validate) {
                log.error(constraintViolation.getMessage());
                errors.add(constraintViolation.getMessage());

            }
            BeanPropertyBindingResult result = new BeanPropertyBindingResult(studentRequest, StudentController.STUDENT_REQUEST);
            SpringValidatorAdapter adapter = new SpringValidatorAdapter(validator);
            adapter.validate(studentRequest, result);

            if (result.hasErrors()) {
                throw new BusinessException(errors.toString());

            }

        }

        validacoesAdicionais(studentRequest);

    }

    private boolean validacoesAdicionais(StudentVO studentRequest) {
        if (!isValidFormat(studentRequest.getDateOfBirth())) {
            throw new BusinessException("Date of birth not in valid format, valid patterns should be like: (yyyy-MM-dd)");

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

    private ArcOneResponse buildStudentResponse(String msg) {
        return ArcOneResponse.builder()
                .status("201")
                .statusDetails(msg)
                .build();

    }

    public List<StudentVO> getAll() {
        return studentRepository.findAll().stream()
                .map(student -> StudentVO.builder()
                        .address(student.getAddress())
                        .email(student.getEmail())
                        .name(student.getName())
                        .phoneNumber(student.getPhoneNumber())
                        .lastName(student.getLastName())
                        .dateOfBirth(student.getDateOfBirth())
                        .build()).collect(Collectors.toList());

    }

}
