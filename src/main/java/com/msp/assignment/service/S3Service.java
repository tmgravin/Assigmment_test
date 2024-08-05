package com.msp.assignment.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface S3Service {
//    InputStream getObject(String key) throws IOException;
//    String getObjectContentType(String key) throws IOException;
    List<String> listAllKeys(); // Method to list all object keys
}
