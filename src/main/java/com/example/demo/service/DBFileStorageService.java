package com.example.demo.service;

import com.example.demo.model.DBFile;
import com.example.demo.repository.DBFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;

    public DBFile storeFile(MultipartFile file, String featNumber, String shopLink, String itemName) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new IOException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes(), featNumber, shopLink, itemName);

            try{
                DBFile featExists = dbFileRepository.findByFeaturedID(featNumber);
                if(featExists != null){
                    dbFileRepository.deleteById(featExists.getId());
                }

            }catch (Exception e){
                throw new NullPointerException("File does not exist");
            }
            return dbFileRepository.save(dbFile);

        } catch (IOException ex) {
            throw new IOException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String featNumber) throws Exception {
        return dbFileRepository.findByFeaturedID(featNumber);
    }
}
