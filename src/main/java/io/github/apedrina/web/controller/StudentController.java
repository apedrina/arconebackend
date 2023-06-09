package io.github.apedrina.web.controller;

import io.github.apedrina.web.service.StudentService;
import io.github.apedrina.web.vo.StudentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/arcone/student")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    @Operation(summary = "Add a student", description = "Add a new student",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Student added with success", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class)))
            })
    public ResponseEntity<?> addStudent(@RequestBody StudentVO studentRequest) {
        studentService.validate(studentRequest);

        return new ResponseEntity<StudentVO>(studentService.addStudent(studentRequest), HttpStatus.CREATED);

    }

    @GetMapping
    @Operation(summary = "Get a list of students", description = "Get all students",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Student retrieved with success", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class)))
            })
    public ResponseEntity<List<StudentVO>> getStudents(@RequestBody StudentVO studentVO) {
        studentService.validate(studentVO);

        return new ResponseEntity<List<StudentVO>>(studentService.getAll(), HttpStatus.CREATED);

    }


}