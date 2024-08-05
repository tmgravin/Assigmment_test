package com.msp.assignment.utils;

import lombok.Data;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Component
@Data
@Slf4j
public class FileUtils {

    private final S3Client s3Client;

    @Value("${s3.bucket.name}")
    private String bucketName;

    public FileUtils(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // Save a file to the S3 bucket.
    public void saveFile(MultipartFile file, String fileName) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    RequestBody.fromInputStream(inputStream, file.getSize()));
            log.info("File uploaded to S3: " + fileName);
        }
    }

    // Generates a unique filename using a truncated UUID and the original file extension.
    public String generateFileName(MultipartFile file) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = getFileExtension(originalFilename);
        String uuidPart = UUID.randomUUID().toString().substring(0, 20);
        return uuidPart + "." + extension;
    }

    // Delete a file from the S3 bucket.
    public void deleteFileIfExists(String fileName) {
        try {
            if (s3Client.headObject(r -> r.bucket(bucketName).key(fileName)).sdkHttpResponse().isSuccessful()) {
                s3Client.deleteObject(DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build());
                log.info("Deleted file from S3: " + fileName);
            } else {
                log.error("File not found in S3 for deletion: " + fileName);
            }
        } catch (S3Exception e) {
            log.error("Failed to delete file from S3", e);
        }
    }

    // Extracts the file extension from the filename.
    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
