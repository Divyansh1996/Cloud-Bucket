package com.example.cloudbucket.controller;

import com.example.cloudbucket.service.StorageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class HomeController {

    private StorageImpl storageImpl;

    public HomeController(StorageImpl storage){
        this.storageImpl = storage;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("bucketName") String bucketName,@RequestParam("file") MultipartFile file) {
        try{
            storageImpl.storeFile(bucketName, file);
        }
        catch (Exception e){
            return ResponseEntity.status(400).body("Found Exception while uploading file " +file.getOriginalFilename()+" with error message: "+e.getMessage());
        }
        return ResponseEntity.status(200).body("File uploaded successfully");
    }

    @GetMapping("/retrieveFile")
    public ResponseEntity<String> retrieveFile(@RequestParam("bucketName") String bucketName, @RequestParam("filePath") String filePath){
        String newFilePath;
        try{
            newFilePath = storageImpl.retrieveFile(bucketName, filePath);
        }
        catch (Exception e){
            return ResponseEntity.status(404).body("Found Exception while retrieving file " +filePath+" with error message: "+e.getMessage());
        }

        return ResponseEntity.status(200).body("File retrieved successfully present at path: "+newFilePath);
    }
}
