package com.penguinwan.infrastructure.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api", consumes = "application/json", produces = "application/json")
public class RouteController {

    @GetMapping("/direct")
    public String get() {
        return "{\"h\":\"h\"}";
    }

}
