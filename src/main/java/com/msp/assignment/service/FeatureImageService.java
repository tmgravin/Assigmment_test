package com.msp.assignment.service;

import com.msp.assignment.model.FeatureImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FeatureImageService {
    Object getImages(Optional<Long> id);

    String addImage(FeatureImages featureImages, MultipartFile imageUrl);
}
