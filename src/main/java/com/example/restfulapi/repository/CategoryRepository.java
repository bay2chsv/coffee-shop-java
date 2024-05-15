package com.example.restfulapi.repository;

import com.example.restfulapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByName(String name);

}
