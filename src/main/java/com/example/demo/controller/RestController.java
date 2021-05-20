package com.example.demo.controller;

import com.example.demo.model.Authority;
import com.example.demo.model.DBFile;
import com.example.demo.model.Employee;
import com.example.demo.model.Item;
import com.example.demo.payload.UploadFileResponse;
import com.example.demo.repository.AuthorityRepository;
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


    public RestController(EmployeeRepository employeeRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, DBFileStorageService dbFileStorageService){
        this.dbFileStorageService = dbFileStorageService;
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

    @PostMapping(value = "/uploadImage")
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@RequestBody MultipartFile[] featured_img){
        System.out.println(Arrays.toString(featured_img));
        System.out.println(featured_img.length);
        return null;
    }

    private static final Logger logger = LoggerFactory.getLogger(RestController.class);



    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("featured_img") MultipartFile file) throws Exception {
        DBFile dbFile = dbFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> {
                    try {
                        return uploadFile(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
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


