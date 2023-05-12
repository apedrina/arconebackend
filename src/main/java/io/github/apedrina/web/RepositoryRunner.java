package io.github.apedrina.web;

import io.github.apedrina.web.model.*;
import io.github.apedrina.web.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RepositoryRunner implements CommandLineRunner {

    private static final String ADDRESS = "1234 NW Bobcat Lane, St. Robert, MO 65584-5678";
    private static final String DATE = "1980-08-23";
    private static final String EMAIL = "robertson@gmail.com";
    private static final String NAME = "Robertson";
    private static final String LAST_NAME = "Martin";
    private static final long PHONE = 2124567890l;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository profileRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Listing student...");
        studentRepository.findAll().forEach(System.out::println);

        log.info("Inserting Student...");

        var student = Student.builder()
                .address(ADDRESS)
                .email(EMAIL)
                .name(NAME)
                .dateOfBirth(DATE)
                .lastName(LAST_NAME)
                .phoneNumber(PHONE)
                .build();

        var studentSaved = studentRepository.save(student);

        Optional<Student> studentById = studentRepository.findById(studentSaved.getId());

        if (studentById.isPresent()) {
            var newStudent = studentById.get();

            log.info("Student: {}", newStudent);

            var logObj = Log.builder()
                    .description("Testing logs...")
                    .student(newStudent)
                    .type("Watching_Videos")
                    .build();

            Log logSaved = logRepository.save(logObj);

            log.info("Log: {}", logSaved);

            var course = Course.builder()
                    .description("Title: Test")
                    .name("Hello World")
                    .students(List.of(newStudent))
                    .build();
            Course savedCourse = courseRepository.save(course);

            log.info("Course: {}", savedCourse);

            var user = User.builder()
                    .username("test")
                    .email("test@gmail.com")
                    .build();

            User savedUser = userRepository.save(user);

            log.info("User: {}", savedUser);

            var profile = Role.builder()
                    .name(ERole.ROLE_ADMIN)
                    .build();

            Role savedProfile = profileRepository.save(profile);

            log.info("Profile: {}", savedProfile);

            var roleUser = Role.builder()
                    .name(ERole.ROLE_USER)
                    .build();
            roleRepository.save(roleUser);

            log.info("Role: {}", roleUser);

            var roleModerator = Role.builder()
                    .name(ERole.ROLE_MODERATOR)
                    .build();
            roleRepository.save(roleModerator);

            log.info("Role: {}", roleModerator);

            var roleAdmin = Role.builder()
                    .name(ERole.ROLE_ADMIN)
                    .build();
            roleRepository.save(roleAdmin);

            log.info("Role: {}", roleAdmin);

        }


    }

}
