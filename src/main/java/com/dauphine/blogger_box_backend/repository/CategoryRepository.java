package com.dauphine.blogger_box_backend.repository;
import com.dauphine.blogger_box_backend.models.Category;
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

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query("""
select category from Category category
WHERE category.name ilike concat("%",:name,"%") 
""")List<Category> findAllLikeName(@Param("name") String name);

    boolean existsByName(String name);

}
