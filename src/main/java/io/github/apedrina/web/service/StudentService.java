package io.github.apedrina.web.service;

import io.github.apedrina.web.model.Student;
import io.github.apedrina.web.model.error.BusinessException;
import io.github.apedrina.web.repository.StudentRepository;
import io.github.apedrina.web.vo.StudentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentVO addStudent(StudentVO studentRequest) {
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

        Student savedStudent = studentRepository.save(student);

        return StudentVO.builder()
                .address(savedStudent.getAddress())
                .dateOfBirth(savedStudent.getDateOfBirth())
                .lastName(savedStudent.getLastName())
                .phoneNumber(savedStudent.getPhoneNumber())
                .name(savedStudent.getName())
                .id(savedStudent.getId())
                .email(savedStudent.getEmail())
                .build();

    }

    //TODO put to working the @Valid annotation
    public void validate(StudentVO studentRequest) {
        if (StringUtils.isEmpty(studentRequest.getDateOfBirth())){
            throw new BusinessException(StudentVO.DOB_INVALID);
        }
        if (!isValidFormat(studentRequest.getDateOfBirth())) {
            throw new BusinessException("[Date of birth not in valid format, valid patterns should be like: (yyyy-MM-dd)]");

        }

    }

    private static boolean isValidFormat(String value) {
        try {
            DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(value);
            return true;

        } catch (DateTimeParseException dtpe) {
            return false;
        }
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
