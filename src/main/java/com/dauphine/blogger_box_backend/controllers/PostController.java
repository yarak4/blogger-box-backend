package com.dauphine.blogger_box_backend.controllers;

import com.dauphine.blogger_box_backend.dtos.PostDTO;
import com.dauphine.blogger_box_backend.exceptions.CategoryExceptions;
import com.dauphine.blogger_box_backend.exceptions.PostExceptions;
import com.dauphine.blogger_box_backend.models.Post;
import com.dauphine.blogger_box_backend.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@Tag(name = "Post Controller", description = "Management of Blog Posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Operation(summary = "Get all posts like Title or Cat")
    public List<Post> getAll(@RequestParam(required = false) String name) {
        return name == null || name.isBlank()
                ? postService.getAllPostsOrdered()
                : postService.getAllLikeTitleOrCat(name);
    }

    @GetMapping("/categories/{categoryId}")
    @Operation(summary = "Get all posts by category ID")
    public List<Post> getByCategory(@PathVariable UUID categoryId)
            throws CategoryExceptions.CategoryNotFoundException {
        return postService.getPostsByCategory(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new post")
    public Post create(@RequestBody PostDTO dto)
            throws CategoryExceptions.CategoryNotFoundException {
        // Unpacking the DTO to match the service's create method
        return postService.createPost(dto.title(), dto.content(), dto.categoryId());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing post")
    public Post update(@PathVariable UUID id, @RequestBody PostDTO dto)
            throws PostExceptions.PostNotFoundException, CategoryExceptions.CategoryNotFoundException {
        // Unpacking the DTO to match the service's update method
        return postService.updatePost(id, dto.title(), dto.content(), dto.categoryId());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a post")
    public void delete(@PathVariable UUID id)
            throws PostExceptions.PostNotFoundException {
        postService.deletePost(id);
    }
}