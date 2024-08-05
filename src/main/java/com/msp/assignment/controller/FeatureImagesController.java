package com.msp.assignment.controller;

import com.msp.assignment.exception.FileUploadException;
import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.FeatureImages;
import com.msp.assignment.service.FeatureImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/featureImages")
public class FeatureImagesController {
    private static final Logger log = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    private FeatureImageService featureImageService;

    //Api for getting all image URLs or image URL by ID
    @GetMapping("/")
    public ResponseEntity<?> getAllImages(@RequestParam(value = "id", required = false) Long id) {
        log.info("Inside getAllImages method of FeatureImagesController.");
        try {
            Object images = featureImageService.getImages(Optional.ofNullable(id));
            if (images instanceof String && images.equals("Images not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(images);
            }
            return ResponseEntity.ok(images);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //Api for uploading features images
    @PostMapping("/")
    public ResponseEntity<String> uploadImages(@RequestParam("image") MultipartFile imageUrl) {
        log.info("Inside uploadImages method of FeatureImagesController.");
        try {
            featureImageService.addImage(new FeatureImages(), imageUrl);
            return ResponseEntity.status(HttpStatus.OK).body("Image saved successfully.");
        } catch (FileUploadException e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
