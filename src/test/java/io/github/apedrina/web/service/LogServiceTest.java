package io.github.apedrina.web.service;

import io.github.apedrina.web.mock.MockUtilTest;
import io.github.apedrina.web.model.ELogType;
import io.github.apedrina.web.vo.LogVO;
import io.github.apedrina.web.vo.StudentVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LogServiceTest extends MockUtilTest {

    @Autowired
    private LogService logService;

    @Autowired
    private StudentService studentService;

    @Test
    public void a_should_add_a_log(){
        StudentVO studentVO = StudentVO.builder()
                .address("xxxxx")
                .email("xxxxx@gmail.com")
                .name("KKKKK")
                .lastName("dfdfdf")
                .phoneNumber(2323232)
                .dateOfBirth("1980-08-23")
                .build();

        StudentVO addStudent = studentService.addStudent(studentVO);
        studentVO.setId(addStudent.getId());

        var logVO = LogVO.builder()
                .description("hello")
                .student(studentVO)
                .idStudent(addStudent.getId())
                .type(ELogType.WATCHING_VIDEOS)
                .build();

        logService.addLog(logVO);

    }
}
