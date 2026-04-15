package com.dauphine.blogger_box_backend.services;

import com.dauphine.blogger_box_backend.repository.CategoryRepository;
import com.dauphine.blogger_box_backend.exceptions.CategoryExceptions;
import com.dauphine.blogger_box_backend.models.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category getById(UUID id) {
        // Automatically throws our 404 exception if the ID isn't found
        return repository.findById(id)
                .orElseThrow(() -> new CategoryExceptions.CategoryNotFoundException(id));
    }

    public Category create(String name) {
        // Check if the name already exists to prevent duplicates
        if (repository.existsByName(name)) {
            throw new CategoryExceptions.CategoryNameAlreadyExistsException(name);
        }

        Category category = new Category(name);
        return repository.save(category);
    }

    public Category update(UUID id, String name) {
        // 1. Fetch the category (this will throw CategoryNotFoundException if it doesn't exist)
        Category category = getById(id);

        // 2. Check if the new name is already taken by another category
        if (repository.existsByName(name)) {
            throw new CategoryExceptions.CategoryNameAlreadyExistsException(name);
        }

        // 3. Update and save
        category.setName(name);
        return repository.save(category);
    }

    public boolean deleteById(UUID id) {
        // Ensure it exists before trying to delete it, otherwise throw 404
        Category category = getById(id);

        repository.delete(category);
        return true;
    }

    public List<Category> getAllLikeName(String name) {
        return repository.findAllLikeName(name);
    }
}