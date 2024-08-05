package com.msp.assignment.service.impl;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Object;
import com.msp.assignment.service.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class S3ServiceImpl implements S3Service {

    private static final Logger logger = LoggerFactory.getLogger(S3ServiceImpl.class);

    private final S3Client s3Client;

    @Value("${s3.bucket.name}")
    private String bucketName;

    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

//    @Override
//    public InputStream getObject(String key) throws IOException {
//        logger.info("Retrieving object with key: {}", key);
//        try {
//            return s3Client.getObject(GetObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(key)
//                    .build());
//        } catch (Exception e) {
//            logger.error("Failed to retrieve object with key: {}", key, e);
//            throw new IOException("Failed to retrieve object from S3", e);
//        }
//    }

//    @Override
//    public String getObjectContentType(String key) throws IOException {
//        logger.info("Retrieving content type for object with key: {}", key);
//        try {
//            GetObjectResponse response = s3Client.getObject(GetObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(key)
//                    .build()).response();
//            return response.contentType();
//        } catch (Exception e) {
//            logger.error("Failed to retrieve content type for object with key: {}", key, e);
//            throw new IOException("Failed to retrieve object metadata from S3", e);
//        }
//    }

    @Override
    public List<String> listAllKeys() {
        logger.info("Listing all keys in bucket: {}", bucketName);
        try {
            return s3Client.listObjectsV2Paginator(builder -> builder.bucket(bucketName))
                    .contents()
                    .stream()
                    .map(S3Object::key)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to list keys in bucket: {}", bucketName, e);
            return List.of(); // Return an empty list in case of an error
        }
    }
}
