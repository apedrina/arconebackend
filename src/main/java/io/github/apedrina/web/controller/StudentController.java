package io.github.apedrina.web.controller;

import io.github.apedrina.web.controller.payload.response.ArcOneResponse;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/arcone/student")
@Slf4j
public class StudentController {

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
    public ResponseEntity<ArcOneResponse> addStudent(@RequestBody StudentVO studentRequest, WebRequest webRequest) {
        log.info("BrotoRequest payload recebido: " + studentRequest);
        webRequest.setAttribute(STUDENT_REQUEST, studentRequest, RequestAttributes.SCOPE_REQUEST);

        return new ResponseEntity<ArcOneResponse>(studentService.addUser(studentRequest), HttpStatus.CREATED);

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
    public ResponseEntity<ArcOneResponse> getStudents(@RequestBody StudentVO brotoRequest, WebRequest webRequest) {
        log.info("BrotoRequest payload recebido: " + brotoRequest);

        webRequest.setAttribute(STUDENT_REQUEST, brotoRequest, RequestAttributes.SCOPE_REQUEST);

        studentService.validar(brotoRequest);

        return null;//new ResponseEntity<BrotoResponse>(null, HttpStatus.CREATED);

    }


}