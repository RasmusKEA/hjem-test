package com.example.demo.controller;

import com.example.demo.repository.EmployeeRepository;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final EmployeeRepository employeeRepository;

    public RestController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
}
