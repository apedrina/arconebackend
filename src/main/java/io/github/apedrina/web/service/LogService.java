package io.github.apedrina.web.service;

import io.github.apedrina.web.model.Log;
import io.github.apedrina.web.model.error.BusinessException;
import io.github.apedrina.web.repository.LogRepository;
import io.github.apedrina.web.repository.StudentRepository;
import io.github.apedrina.web.vo.LogVO;
import io.github.apedrina.web.vo.StudentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LogService {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private StudentRepository studentRepository;

    public void addLog(LogVO logVO) {
        var studentEntity = studentRepository.findById(logVO.getIdStudent());

        if (studentEntity.isPresent()) {
            var student = studentEntity.get();
            var logEntity = Log.builder()
                    .type(logVO.getType())
                    .description(logVO.getDescription())
                    .student(student).build();

            logRepository.save(logEntity);

        } else {
            throw new BusinessException(BusinessException.STUDENT_NOT_FOUND);

        }

    }

    public List<LogVO> getAll() {
        return logRepository.findAll().stream()
                .map(log -> {
                    var studentEntity = log.getStudent();
                    var studentVO = StudentVO.builder()
                            .address(studentEntity.getAddress())
                            .email(studentEntity.getEmail())
                            .phoneNumber(studentEntity.getPhoneNumber())
                            .dateOfBirth(studentEntity.getDateOfBirth())
                            .lastName(studentEntity.getLastName())
                            .name(studentEntity.getName()).build();
                    return LogVO.builder()
                            .id(log.getId())
                            .type(log.getType())
                            .description(log.getDescription())
                            .student(studentVO)
                            .build();
                }).collect(Collectors.toList());
    }
}
