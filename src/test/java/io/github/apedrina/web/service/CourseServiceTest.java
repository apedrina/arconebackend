package io.github.apedrina.web.service;

import io.github.apedrina.web.mock.MockUtilTest;
import io.github.apedrina.web.model.Course;
import io.github.apedrina.web.model.Student;
import io.github.apedrina.web.model.error.BusinessException;
import io.github.apedrina.web.repository.StudentRepository;
import io.github.apedrina.web.vo.CourseVO;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CourseServiceTest extends MockUtilTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void a_should_add_a_course(){
        var courseVO = CourseVO.builder()
                .name("New Course")
                .description("hello description")
                .build();

        courseService.addCourse(courseVO);

    }

    @Test(expected = BusinessException.class)
    public void b_avoid_add_a_course_with_same_name(){
        var courseVO = CourseVO.builder()
                .name("New Course")
                .description("hello description")
                .build();

        courseService.addCourse(courseVO);

    }


    @Test(expected = BusinessException.class)
    public void c_avoid_register_a_student_in_more_than_3_courses_at_same_time(){
        var courseVO1 = CourseVO.builder()
                .name("New Course2")
                .description("hello description2")
                .build();
        CourseVO addedCourse1 = courseService.addCourse(courseVO1);

        var courseEntity1 = Course.builder()
                .id(addedCourse1.getId())
                .description(addedCourse1.getDescription())
                .name(addedCourse1.getName())
                .build();

        var courseVO2= CourseVO.builder()
                .name("New Course3")
                .description("hello description3")
                .build();
        CourseVO addedCourse2 = courseService.addCourse(courseVO2);

        var courseEntity2 = Course.builder()
                .id(addedCourse2.getId())
                .description(addedCourse2.getDescription())
                .name(addedCourse2.getName())
                .build();

        var courseVO3= CourseVO.builder()
                .name("New Course4")
                .description("hello description4")
                .build();
        CourseVO addedCourse3 = courseService.addCourse(courseVO3);

        var courseEntity3 = Course.builder()
                .id(addedCourse3.getId())
                .description(addedCourse3.getDescription())
                .name(addedCourse3.getName())
                .build();

        Set<Course> courses = Set.of(courseEntity1,courseEntity2,courseEntity3);

        var student = Student.builder()
                .dateOfBirth("1980-08-23")
                .phoneNumber(34343434)
                .lastName("silva")
                .name("gregorio")
                .address("sxgge")
                .studentsCourses(courses)
                .build();

        Student savedStudent = studentRepository.save(student);

        courseService.validateRegisterStudent(savedStudent.getId());


    }

    @Test
    public void d_should_register_a_student_in_a_course(){
        var courseVO1 = CourseVO.builder()
                .name("New Course22")
                .description("hello description22")
                .build();
        CourseVO addedCourse1 = courseService.addCourse(courseVO1);

        var courseEntity1 = Course.builder()
                .id(addedCourse1.getId())
                .description(addedCourse1.getDescription())
                .name(addedCourse1.getName())
                .build();

        Set<Course> courses = Set.of(courseEntity1);

        var student = Student.builder()
                .dateOfBirth("1980-08-23")
                .phoneNumber(34343434)
                .lastName("silva2")
                .name("gregorio2")
                .address("sxgge2")
                .studentsCourses(courses)
                .build();

        Student savedStudent = studentRepository.save(student);

        courseService.validateRegisterStudent(savedStudent.getId());

        Assert.assertNotNull(savedStudent);


    }


}
