package com.msp.assignment.utils;

import com.msp.assignment.exception.FileUploadException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Set;

@Component
public class FeatureUtils {
    private static final String FILES = "C:\\Users\\kshitiz\\Desktop\\MSP\\MSPAssignment\\.\\src\\main\\resources\\static\\featureImage";
    private final Set<String> allowedExtensions = new HashSet<>(Set.of(".jpg", ".jpeg", ".png"));

    public void saveFile(MultipartFile file, String fileName) throws IOException {
        Path uploads = Paths.get(FILES);
        if (!Files.exists(uploads)) {
            Files.createDirectories(uploads);
            System.out.println("Directory created: " + uploads.toAbsolutePath());
        }
        Path filePath = uploads.resolve(fileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File saved to: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Failed to save file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
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
            System.out.println("File deleted: " + filePath.toAbsolutePath());
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
