package com.dauphine.blogger_box_backend.repository;

import com.dauphine.blogger_box_backend.models.Category;
import com.dauphine.blogger_box_backend.models.Post;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByOrderByCreatedDateDesc();

    
    List<Post> findByCategoryId(UUID categoryId);


    @Query("""
SELECT post FROM Post post 
WHERE post.title ILIKE concat("%", :name,"%") OR post.content ILIKE concat("%", :name,"%") 
""")List<Post> findAllLikeTitleOrCat(@Param("name") String name);
}
