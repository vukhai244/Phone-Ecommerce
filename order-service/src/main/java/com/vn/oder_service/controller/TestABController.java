package com.vn.oder_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-ab")
public class TestABController {
    @GetMapping
    public String testAB() {
        System.out.println("Apache benchmark client request");
        return "Response from Apache Benchmark";
    }

}
