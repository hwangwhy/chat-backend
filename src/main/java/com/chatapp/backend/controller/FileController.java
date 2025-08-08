package com.chatapp.backend.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileController {
    
    @Value("${file.upload-dir}")
    private String uploadDir;
    
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("Received file upload request");
            System.out.println("File name: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize() + " bytes");
            System.out.println("Upload directory: " + uploadDir);
            
            if (file.isEmpty()) {
                System.out.println("File is empty");
                return ResponseEntity.badRequest().body("File is empty");
            }
            
            // Create upload directory if not exists
            Path uploadPath = Paths.get(uploadDir.trim());
            System.out.println("Upload path: " + uploadPath.toAbsolutePath());
            if (!Files.exists(uploadPath)) {
                System.out.println("Creating upload directory");
                Files.createDirectories(uploadPath);
            }
            
            // Generate unique filename
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null || !originalFilename.contains(".")) {
                return ResponseEntity.badRequest().body("Invalid file name");
            }
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = UUID.randomUUID().toString() + extension;
            
            // Save file
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);
            System.out.println("File saved to: " + filePath.toAbsolutePath());
            
            // Return file URL
            String fileUrl = "http://192.168.1.164:8080/api/files/" + filename;
            Map<String, String> response = new HashMap<>();
            response.put("url", fileUrl);
            response.put("filename", filename);
            
            System.out.println("Upload successful. URL: " + fileUrl);
            return ResponseEntity.ok(response);
            
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Could not upload file: " + e.getMessage());
        }
    }
    
    @GetMapping("/{filename}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir.trim()).resolve(filename);
            
            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }
            
            byte[] fileContent = Files.readAllBytes(filePath);
            String contentType = Files.probeContentType(filePath);
            
            return ResponseEntity.ok()
                    .header("Content-Type", contentType != null ? contentType : "application/octet-stream")
                    .body(fileContent);
                    
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Could not read file: " + e.getMessage());
        }
    }
} 