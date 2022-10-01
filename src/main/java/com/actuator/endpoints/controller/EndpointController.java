package com.actuator.endpoints.controller;

import com.actuator.endpoints.service.EndpointService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/monitor")
public class EndpointController {

    @Autowired
    EndpointService endpointService;

    @GetMapping("/metrics")
    public ResponseEntity<JSONArray> metricsController() throws IOException, ParseException {
        return new ResponseEntity<>(endpointService.metricsService(), HttpStatus.OK);
    }

    @GetMapping("/environment")
    public ResponseEntity<JSONObject> environmentController() throws IOException, ParseException {
        return new ResponseEntity(endpointService.envService(), HttpStatus.OK);
    }

    @GetMapping("/heapDump")
    public ResponseEntity heapDumpController() {
        return new ResponseEntity(HttpStatus.OK);
    }
}
