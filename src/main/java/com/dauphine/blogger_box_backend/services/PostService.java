package com.dauphine.blogger_box_backend.services;

import com.dauphine.blogger_box_backend.repository.PostRepository;
import com.dauphine.blogger_box_backend.exceptions.PostExceptions;
import com.dauphine.blogger_box_backend.models.Category;
import com.dauphine.blogger_box_backend.models.Post;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;

    public PostService(PostRepository postRepository, CategoryService categoryService) {
        this.postRepository = postRepository;
        this.categoryService = categoryService;
    }

    public List<Post> getAllPostsOrdered() {
        return postRepository.findAllByOrderByCreatedDateDesc();
    }

    public List<Post> getPostsByCategory(UUID categoryId) {
        return postRepository.findByCategoryId(categoryId);
    }

    public Post getById(UUID id) {
        // Now throws your custom exception!
        return postRepository.findById(id)
                .orElseThrow(() -> new PostExceptions.PostNotFoundException(id));
    }

    public Post createPost(String title, String content, UUID categoryId) {
        
        Category category = categoryService.getById(categoryId);

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedDate(LocalDateTime.now());
        post.setCategory(category);

        return postRepository.save(post);
    }

    public Post updatePost(UUID id, String title, String content, UUID categoryId) {
        
        Post post = getById(id);

       
        Category category = categoryService.getById(categoryId);

        post.setTitle(title);
        post.setContent(content);
        post.setCategory(category);

        return postRepository.save(post);
    }

    public boolean deletePost(UUID id) {
        // Ensure the post exists before attempting to delete it
        Post post = getById(id);

        postRepository.delete(post);
        return true;
    }

    public List<Post> getAllLikeTitleOrCat(String name) {
        return postRepository.findAllLikeTitleOrCat(name);
    }
}
