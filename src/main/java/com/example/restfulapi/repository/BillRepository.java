package com.example.restfulapi.repository;

import com.example.restfulapi.entity.Bill;
import com.example.restfulapi.entity.CoffeeTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill,Integer> {
    List<Bill> findBillByCoffeeTable(CoffeeTable coffeeTable);
}
