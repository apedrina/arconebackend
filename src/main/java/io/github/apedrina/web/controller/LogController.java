package io.github.apedrina.web.controller;

import io.github.apedrina.web.service.LogService;
import io.github.apedrina.web.vo.LogVO;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/arcone/log")
@Slf4j
public class LogController {

    private static final String LOG_ADDED = "Log added with success";

    @Autowired
    private LogService logService;

    @PostMapping
    @Operation(summary = "Add a log", description = "Add a new log",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Log added with success", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class)))
            })
    public ResponseEntity<String> addLog(@RequestBody LogVO logVO) {

        logService.addLog(logVO);

        return new ResponseEntity<>(LOG_ADDED, HttpStatus.CREATED);

    }

    @GetMapping
    @Operation(summary = "Get a list of logs", description = "Get all logs",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Logs retrieved with success", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error", content =
                    @Content(mediaType = "application/json", schema = @Schema(implementation = HttpStatus.class)))
            })
    public ResponseEntity<List<LogVO>> getLogs() {

        return new ResponseEntity<>(logService.getAll(), HttpStatus.CREATED);

    }


}