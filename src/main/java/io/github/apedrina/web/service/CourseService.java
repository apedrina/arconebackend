package io.github.apedrina.web.service;

import io.github.apedrina.web.controller.payload.response.ArcOneResponse;
import io.github.apedrina.web.model.Course;
import io.github.apedrina.web.model.error.BusinessException;
import io.github.apedrina.web.repository.CourseRepository;
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

    public ArcOneResponse addCourse(CourseVO courseVO) {
        if (courseRepository.findByName(courseVO.getName()).size() > 0) {
            throw new BusinessException(BusinessException.NOT_UNIQUE_COURSE);

        }
        var courseEntity = Course.builder()
                .name(courseVO.getName())
                .description(courseVO.getDescription())
                .build();


        courseRepository.save(courseEntity);

        return buildResponse("Course added with success");

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
