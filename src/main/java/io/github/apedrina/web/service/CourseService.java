package io.github.apedrina.web.service;

import io.github.apedrina.web.controller.payload.response.ArcOneResponse;
import io.github.apedrina.web.model.Course;
import io.github.apedrina.web.model.error.BusinessException;
import io.github.apedrina.web.repository.CourseRepository;
import io.github.apedrina.web.repository.StudentRepository;
import io.github.apedrina.web.vo.CourseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    public void validateRegisterStudent(Long idStudent){
        var studentOptional = studentRepository.findById(idStudent);
        if (studentOptional.isPresent()){
            var studentEntity = studentOptional.get();
            if (studentEntity.getStudentsCourses().size() >= 3){
                throw new BusinessException(BusinessException.STUDENT_DOING_MORE_THAN_3_COURSES);
            }
        } else {
            throw new BusinessException(BusinessException.STUDENT_NOT_FOUND);
        }
    }

    public CourseVO addCourse(CourseVO courseVO) {
        if (courseRepository.findByName(courseVO.getName()).size() > 0) {
            throw new BusinessException(BusinessException.NOT_UNIQUE_COURSE);

        }
        var courseEntity = Course.builder()
                .name(courseVO.getName())
                .description(courseVO.getDescription())
                .build();

        Course savedCourse = courseRepository.save(courseEntity);

        return CourseVO.builder()
                .id(savedCourse.getId())
                .description(savedCourse.getDescription())
                .name(savedCourse.getName())
                .build();

    }

    public List<CourseVO> getAll() {
        return courseRepository.findAll().stream().map(course -> CourseVO
                .builder()
                .name(course.getName())
                .description(course.getDescription())
                .build()).collect(Collectors.toList());

    }

    private ArcOneResponse buildResponse(String msg) {
        return ArcOneResponse.builder()
                .status("201")
                .statusDetails(msg)
                .build();

    }

}
