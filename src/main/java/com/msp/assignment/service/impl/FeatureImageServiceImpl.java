package com.msp.assignment.service.impl;

import com.msp.assignment.exception.FileUploadException;
import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.model.FeatureImages;
import com.msp.assignment.repository.FeatureImagesRepo;
import com.msp.assignment.service.FeatureImageService;
import com.msp.assignment.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

@Service
public class FeatureImageServiceImpl implements FeatureImageService {
    @Autowired
    private FeatureImagesRepo featureImagesRepo;

    @Autowired
    private FileUtils fileUtils;

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
    public String addImage( MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                FeatureImages featureImages = new FeatureImages();
                String fileName = fileUtils.generateFileName(file);
                fileUtils.saveFile(file, fileName);
                fileUtils.deleteFileIfExists(fileName);
                featureImages.setImageUrl(fileName);

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
