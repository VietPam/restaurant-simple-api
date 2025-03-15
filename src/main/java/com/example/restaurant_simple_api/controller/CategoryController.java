package com.example.restaurant_simple_api.controller;

import com.example.restaurant_simple_api.model.Category;
import com.example.restaurant_simple_api.service.CategoryService;
import com.example.restaurant_simple_api.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// CategoryController.java
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Get all active categories
    @GetMapping("/get-all-categories")
    public ResponseEntity<ApiResponse> getActiveCategories() {
        List<Category> activeCategories = categoryService.listActiveCategories();
        if (activeCategories.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("No categories found.", null, false));
        }
        return ResponseEntity.ok(new ApiResponse("Categories fetched successfully", activeCategories, true));
    }

    // Get category by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Category not found.", null, false));
        }
        return ResponseEntity.ok(new ApiResponse("Category fetched successfully", category, true));
    }

    // Create new category
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
        Category newCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Category created successfully", newCategory, true));
    }

    // Update category
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Category not found.", null, false));
        }
        return ResponseEntity.ok(new ApiResponse("Category updated successfully", updatedCategory, true));
    }

    // Soft delete category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteCategory(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("Category not found.", null, false));
        }
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null, true));
    }
}
