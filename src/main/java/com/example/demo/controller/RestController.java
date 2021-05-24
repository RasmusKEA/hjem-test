package com.example.demo.controller;

import com.example.demo.model.Authority;
import com.example.demo.model.DBFile;
import com.example.demo.model.Employee;
import com.example.demo.model.Item;
import com.example.demo.payload.UploadFileResponse;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.DBFileRepository;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.DBFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final EmployeeRepository employeeRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final DBFileStorageService dbFileStorageService;
    private final DBFileRepository dbFileRepository;


    public RestController(EmployeeRepository employeeRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, DBFileStorageService dbFileStorageService, DBFileRepository dbFileRepository){
        this.dbFileStorageService = dbFileStorageService;
        this.dbFileRepository = dbFileRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.employeeRepository = employeeRepository;
    }


    @PostMapping(value = "/createEmployee", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        Authority user = authorityRepository.save(Authority.builder().role("ROLE_USER").build());
        return employeeRepository.save(Employee.builder().username(employee.getUsername()).password(passwordEncoder.encode(employee.getPassword())).authority(user).build());

    }

    private static final Logger logger = LoggerFactory.getLogger(RestController.class);



    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("featured_img") MultipartFile file, @RequestParam("featNumber") String featNumber) throws Exception {
        DBFile dbFile = dbFileStorageService.storeFile(file, featNumber);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getFeaturedID())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize(), Integer.parseInt(featNumber));
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }


}


