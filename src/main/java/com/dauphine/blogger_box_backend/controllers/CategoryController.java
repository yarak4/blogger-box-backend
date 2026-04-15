package com.dauphine.blogger_box_backend.controllers;

import com.dauphine.blogger_box_backend.dtos.CategoryDTO;
import com.dauphine.blogger_box_backend.exceptions.CategoryExceptions;
import com.dauphine.blogger_box_backend.models.Category;
import com.dauphine.blogger_box_backend.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category Controller", description = "Management of Blog Categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "Get all categories like Name")
    public ResponseEntity<List<Category>> getAll(@RequestParam(required = false) String name) {
        List<Category> categories = name == null || name.isBlank()
                ? categoryService.getAll()
                : categoryService.getAllLikeName(name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a category by its ID")
    public ResponseEntity<Category> getById(@PathVariable UUID id) throws CategoryExceptions.CategoryNotFoundException {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new category")
    public ResponseEntity<Category> create(@RequestBody CategoryDTO categoryRequest)
            throws CategoryExceptions.CategoryNameAlreadyExistsException {
        Category category = categoryService.create(categoryRequest.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing category")
    public ResponseEntity<Category> update(@PathVariable UUID id, @RequestBody CategoryDTO dto)
            throws CategoryExceptions.CategoryNotFoundException, CategoryExceptions.CategoryNameAlreadyExistsException {
        Category category = categoryService.update(id, dto.name());
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a category")
    public void delete(@PathVariable UUID id) throws CategoryExceptions.CategoryNotFoundException {
        categoryService.deleteById(id);
    }
}