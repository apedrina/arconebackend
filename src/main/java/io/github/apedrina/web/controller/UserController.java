package io.github.apedrina.web.controller;

import io.github.apedrina.web.service.UserService;
import io.github.apedrina.web.vo.UserVO;
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
@RequestMapping("/arcone/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Add a user", description = "Add a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User added with success", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class)))
            })
    public ResponseEntity<?> addUser(@RequestBody UserVO userVO) {

        userService.addUser(userVO);

        return new ResponseEntity<>("User added with success", HttpStatus.CREATED);

    }

    @GetMapping
    @Operation(summary = "Get a list of users", description = "Get all users",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Users retrieved with success", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class)))
            })
    public ResponseEntity<List<UserVO>> getUsers() {

        return new ResponseEntity<>(userService.getAll(), HttpStatus.CREATED);
    }


}