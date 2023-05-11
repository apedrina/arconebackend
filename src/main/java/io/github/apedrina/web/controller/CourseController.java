package io.github.apedrina.web.controller;

import io.github.apedrina.web.model.StudentRequest;
import io.github.apedrina.web.model.StudentResponse;
import io.github.apedrina.web.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@CrossOrigin(origins = {"${cors.url}"}, maxAge = 3600)
@RestController
@RequestMapping("/arcone/course")
@Slf4j
public class CourseController {

    public static final String STUDENT_REQUEST = "studentRequest";

    @Autowired
    private StudentService studentService;

    @PostMapping
    @Operation(summary = "Add a student", description = "Add a new student",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User added with success", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class)))
            })
    public ResponseEntity<StudentResponse> addStudent(@RequestBody StudentRequest studentRequest, WebRequest webRequest) {
        log.info("BrotoRequest payload recebido: " + studentRequest);
        webRequest.setAttribute(STUDENT_REQUEST, studentRequest, RequestAttributes.SCOPE_REQUEST);

        return new ResponseEntity<StudentResponse>(studentService.addUser(studentRequest), HttpStatus.CREATED);

    }

    @GetMapping
    @Operation(summary = "Get a list of students", description = "Get all students",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Users retrieved with success", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class)))
            })
    public ResponseEntity<StudentResponse> getStudents(@RequestBody StudentRequest brotoRequest, WebRequest webRequest) {
        log.info("BrotoRequest payload recebido: " + brotoRequest);

        webRequest.setAttribute(STUDENT_REQUEST, brotoRequest, RequestAttributes.SCOPE_REQUEST);

        studentService.validar(brotoRequest);

        return null;//new ResponseEntity<BrotoResponse>(null, HttpStatus.CREATED);

    }


}