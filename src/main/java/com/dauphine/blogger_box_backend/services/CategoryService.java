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
        
        return repository.findById(id)
                .orElseThrow(() -> new CategoryExceptions.CategoryNotFoundException(id));
    }

    public Category create(String name) {
        
        if (repository.existsByName(name)) {
            throw new CategoryExceptions.CategoryNameAlreadyExistsException(name);
        }

        Category category = new Category(name);
        return repository.save(category);
    }

    public Category update(UUID id, String name) {
        
        Category category = getById(id);

        
        if (repository.existsByName(name)) {
            throw new CategoryExceptions.CategoryNameAlreadyExistsException(name);
        }

        
        category.setName(name);
        return repository.save(category);
    }

    public boolean deleteById(UUID id) {
       
        Category category = getById(id);

        repository.delete(category);
        return true;
    }

    public List<Category> getAllLikeName(String name) {
        return repository.findAllLikeName(name);
    }
}
