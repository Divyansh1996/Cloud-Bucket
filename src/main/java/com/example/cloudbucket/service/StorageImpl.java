package com.example.cloudbucket.service;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
public class StorageImpl {

    private final Storage storage;


    public StorageImpl() throws IOException {
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("C:\\Users\\asus\\Downloads\\cloud-spanner-430219-340a59da7f5a.json"));
        storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId("cloud-spanner-430219").build().getService();
    }

    public void storeFile(String bucketName, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, originalFileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, file.getBytes());
    }

    public String retrieveFile(String bucketName, String path) throws IOException {
        Blob blob = storage.get(bucketName, path);
        ReadChannel reader = blob.reader();
        String localPath = "C:\\Users\\asus\\IdeaProjects\\Cloud-Bucket\\src\\main\\resources\\"+path;
        File file = new File(localPath);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.getChannel().transferFrom(reader, 0, Long.MAX_VALUE);
        fileOutputStream.close();
        return localPath;
    }
}
