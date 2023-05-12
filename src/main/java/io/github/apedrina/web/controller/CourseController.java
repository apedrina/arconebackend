package io.github.apedrina.web.controller;

import io.github.apedrina.web.controller.payload.response.ArcOneResponse;
import io.github.apedrina.web.service.CourseService;
import io.github.apedrina.web.vo.CourseVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"}, maxAge = 3600)
@RestController
@RequestMapping("/arcone/course")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Operation(summary = "Add a course", description = "Add a new course",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Course added with success", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class)))
            })
    public ResponseEntity<ArcOneResponse> addCourse(@RequestBody CourseVO courseVO) {

        return new ResponseEntity<ArcOneResponse>(courseService.addCourse(courseVO), HttpStatus.CREATED);

    }

    @GetMapping
    @Operation(summary = "Get a list of courses", description = "Get all courses",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Courses retrieved with success", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class)))
            })
    public ResponseEntity<?> getCourses(@RequestBody CourseVO courseVO) {

        return new ResponseEntity<List<CourseVO>>(courseService.getAll(), HttpStatus.CREATED);

    }


}