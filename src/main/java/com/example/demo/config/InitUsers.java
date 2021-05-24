package com.example.demo.config;

import com.example.demo.model.Authority;
import com.example.demo.model.Employee;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitUsers implements CommandLineRunner {
    private final AuthorityRepository authorityRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        loadSecurityData();
    }

    private void loadSecurityData() {
        Authority admin = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());

        employeeRepository.save(Employee.builder().username("emil").password(passwordEncoder.encode("helholm")).authority(admin).build());

    }
}
