package com.example.restfulapi.repository;

import com.example.restfulapi.entity.Category;
import com.example.restfulapi.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrinkRepository extends JpaRepository<Drink,Integer> {
    boolean existsByName(String name);

    List<Drink> findDrinksByCategory_Name(String id);
    List<Drink> findDrinksByCategory(Category category);
}
