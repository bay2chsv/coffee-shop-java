package com.example.restfulapi.repository;

import com.example.restfulapi.entity.CoffeeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeTableRepositoty extends JpaRepository<CoffeeTable,Integer> {

    boolean existsByName(String table);
}
