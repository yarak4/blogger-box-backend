package com.dauphine.blogger_box_backend.exceptions;


import java.util.UUID;

public class CategoryExceptions {

    // Thrown when a category ID is not found in the database
    public static class CategoryNotFoundException extends RuntimeException {
        public CategoryNotFoundException(UUID id) {
            super("Category with ID " + id + " was not found.");
        }
    }

    // Thrown when creating or updating a category with a duplicate name
    public static class CategoryNameAlreadyExistsException extends RuntimeException {
        public CategoryNameAlreadyExistsException(String name) {
            super("A category with the name '" + name + "' already exists.");
        }
    }
}