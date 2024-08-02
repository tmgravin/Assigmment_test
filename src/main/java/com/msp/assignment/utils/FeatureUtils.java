package com.msp.assignment.utils;

import com.msp.assignment.exception.FileUploadException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Component
public class FeatureUtils {
    private static final String FILES = "./src/main/resources/static/featureImage";
    private final Set<String> allowedExtensions = new HashSet<>(Set.of(".jpg", ".jpeg", ".png"));

    public void saveFile(MultipartFile file, String fileName) throws IOException {
        Path uploads = Paths.get(FILES);
        if (!Files.exists(uploads)) {
            Files.createDirectories(uploads);
        }
        Files.copy(file.getInputStream(), uploads.resolve(fileName));
    }

    public String generateFileName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new FileUploadException("Invalid file name.");
        }
        String extension = getFileExtension(originalFilename);
        if (!allowedExtensions.contains(extension.toLowerCase())) {
            throw new FileUploadException("Unsupported file type. Only .jpg, .jpeg, and .png files are allowed.");
        }
        return originalFilename;
    }

    public void deleteFileIfExists(String fileName) throws IOException {
        Path filePath = Paths.get(FILES).resolve(fileName); // Path to the file
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex == -1) {
            return "";
        }
        return filename.substring(dotIndex).toLowerCase();
    }
}
