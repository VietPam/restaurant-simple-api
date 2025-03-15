package com.example.restaurant_simple_api.service;

import com.example.restaurant_simple_api.model.Category;
import com.example.restaurant_simple_api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// CategoryService.java
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Get active categories
    public List<Category> listActiveCategories() {
        return categoryRepository.findByStatus(1);
    }

    // Get category by ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    // Create category
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Update category
    public Category updateCategory(Long id, Category categoryDetails) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(categoryDetails.getName());
            category.setDescription(categoryDetails.getDescription());
            category.setCategoryImage(categoryDetails.getCategoryImage());
            return categoryRepository.save(category);
        }
        return null;
    }

    // Soft delete category
    public boolean deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setStatus(0); // Soft delete
            categoryRepository.save(category);
            return true;
        }
        return false;
    }
}
