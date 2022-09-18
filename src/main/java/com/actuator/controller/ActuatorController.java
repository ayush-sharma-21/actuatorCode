package com.actuator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActuatorController {

    @GetMapping("/demo")
    private String demoActuator(){
        return "helloWorld!!!";
    }
}
