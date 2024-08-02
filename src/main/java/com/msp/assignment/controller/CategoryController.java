package com.msp.assignment.controller;

import com.msp.assignment.exception.ResourceNotFoundException;
import com.msp.assignment.service.ProjectCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private ProjectCategoryService projectCategoryService;

    @GetMapping("/")
    public ResponseEntity<?> getCategory(@RequestParam(name = "id", required = false) Long id) {
        try {
            if (id != null){
                return ResponseEntity.status(HttpStatus.OK).body(projectCategoryService.getById(id));
            }
            return ResponseEntity.status(HttpStatus.OK).body(projectCategoryService.getCategory());
        } catch (ResourceNotFoundException e) {
            e.getLocalizedMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
