package com.dauphine.blogger_box_backend.exceptions;

import java.util.UUID;

public class PostExceptions {

    // Thrown when a post ID is not found in the database
    public static class PostNotFoundException extends RuntimeException {
        public PostNotFoundException(UUID id) {
            super("Post with ID " + id + " was not found.");
        }
    }
}
