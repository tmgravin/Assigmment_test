package com.msp.assignment.controller;

import com.msp.assignment.service.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/files")
public class FilesController {

    private static final Logger logger = LoggerFactory.getLogger(FilesController.class);

    @Autowired
    private S3Service s3Service;

    @GetMapping("/")
    public ResponseEntity<?> getFile() {
        logger.info("No key provided, listing all files in the bucket");
        try {
            List<String> keys = s3Service.listAllKeys();
            logger.info("Successfully listed all files");
            return ResponseEntity.ok(keys);
        } catch (Exception e) {
            logger.error("Failed to list all files", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to list files");
        }
    }
}
