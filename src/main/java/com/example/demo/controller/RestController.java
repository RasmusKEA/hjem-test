package com.example.demo.controller;

import com.example.demo.model.Authority;
import com.example.demo.model.Employee;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final EmployeeRepository employeeRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;


    public RestController(EmployeeRepository employeeRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.employeeRepository = employeeRepository;
    }





    @PostMapping(value = "/createEmployee", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        Authority admin = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
        Authority user = authorityRepository.save(Authority.builder().role("ROLE_USER").build());
        Authority customer = authorityRepository.save(Authority.builder().role("ROLE_CUSTOMER").build());

        return employeeRepository.save(Employee.builder().username(employee.getUsername()).password(passwordEncoder.encode(employee.getPassword())).authority(user).build());


    }
}

