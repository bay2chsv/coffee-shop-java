package com.example.restfulapi.repository;

import com.example.restfulapi.entity.BillDetail;
import com.example.restfulapi.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillDetailRepository extends JpaRepository<BillDetail,Integer> {

    List<BillDetail> findBillDetailByBill_Id(Integer billId);
    List<BillDetail> findBillDetailByDrink(Drink drink);
}
