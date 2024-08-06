package com.msp.assignment.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.InputStream;

@RestController
@CrossOrigin
@RequestMapping("/api/file/download")
public class S3FileDownloadController {

    private static final Logger log = LoggerFactory.getLogger(S3FileDownloadController.class);

    private final S3Client s3Client;

    @Value("${AWS_REGION}")
    private String region;

    @Value("${S3_BASE_URL}")
    private String s3BaseUrl;

    public S3FileDownloadController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping("/")
    public ResponseEntity<InputStreamResource> downloadFile(@RequestParam String bucketName, @RequestParam String filename) {
        log.info("Request received to download file '{}' from bucket '{}'", filename, bucketName);
        try {
            // Remove S3_BASE_URL from filename to get the actual S3 key
            String actualFilename = filename.replace(s3BaseUrl, "").replaceFirst("^/+", "");

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(actualFilename)
                    .build();

            log.info("Constructed GetObjectRequest: {}", getObjectRequest);

            InputStream inputStream = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());
            GetObjectResponse getObjectResponse = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream()).response();

            log.info("Successfully retrieved file from S3");

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + actualFilename + "\"");
            headers.add(HttpHeaders.CONTENT_TYPE, getObjectResponse.contentType());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            log.error("Error occurred while trying to download file: ", e);
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
