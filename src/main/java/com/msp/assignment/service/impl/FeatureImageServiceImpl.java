package com.msp.assignment.service.impl;

import com.msp.assignment.exception.FileUploadException;
import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.FeatureImages;
import com.msp.assignment.repository.FeatureImagesRepo;
import com.msp.assignment.service.FeatureImageService;
import com.msp.assignment.utils.FeatureUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

@Service
public class FeatureImageServiceImpl implements FeatureImageService {
    @Autowired
    private FeatureImagesRepo featureImagesRepo;

    @Autowired
    private FeatureUtils featureUtils;

    @Value("${S3_BASE_URL}")
    private String s3BaseUrl;

    @Override
    public Object getImages(Optional<Long> id) {
        try {
            if (id.isPresent()) {
                Optional<FeatureImages> image = featureImagesRepo.findById(id.get());
                if (image.isEmpty()) {
                    throw new ResourceNotFoundException("Image not found with ID: " + id.get());
                }
                return image.get().getImageUrl();
            } else {
                List<FeatureImages> images = featureImagesRepo.findAll();
                if (images.isEmpty()) {
                    throw new ResourceNotFoundException("No images found.");
                }
                return images.stream().map(FeatureImages::getImageUrl).toList();
            }
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public String addImage(FeatureImages featureImages, MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                String fileName = featureUtils.generateFileName(file);
                featureUtils.deleteFileIfExists(fileName);
                featureUtils.saveFile(file, fileName);
                featureImages.setImageUrl(s3BaseUrl+fileName);

                featureImagesRepo.save(featureImages);
                return "Feature image uploaded successfully: " + fileName;
            } else {
                throw new FileUploadException("File is empty or null");
            }
        } catch (FileUploadException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }
}
