package com.example.restfulapi.service;


import com.example.restfulapi.dto.response.CoffeeTableResponse;
import com.example.restfulapi.dto.response.ResourcePaginationResponse;
import com.example.restfulapi.entity.Bill;
import com.example.restfulapi.entity.BillDetail;
import com.example.restfulapi.entity.CoffeeTable;
import com.example.restfulapi.entity.Drink;
import com.example.restfulapi.exception.BadExecption;
import com.example.restfulapi.repository.BillRepository;
import com.example.restfulapi.repository.CoffeeTableRepositoty;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CofeeTableSevice {
    private final CoffeeTableRepositoty coffeeTableRepositoty;
    private final BillRepository billRepository;

    public ResourcePaginationResponse<List<CoffeeTableResponse>> getAll(Integer page,Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CoffeeTable> item = coffeeTableRepositoty.findAll(pageable);

        List<CoffeeTableResponse> coffeeTableResponses = new ArrayList<>();
        for (CoffeeTable table:item.getContent()
             ) {
            CoffeeTableResponse coffeeTableResponse = new CoffeeTableResponse();
            coffeeTableResponse.setId(table.getId());
            coffeeTableResponse.setName(table.getName());
            coffeeTableResponses.add(coffeeTableResponse);

        }
        return ResourcePaginationResponse.<List<CoffeeTableResponse>>builder().message("get category list ").status(true).data(coffeeTableResponses).page(item.getNumber()).size(item.getSize()).totalPages(item.getTotalPages()).totalItem(item.getTotalElements()).build();
    }

    public void createTable(String name) {
        if (coffeeTableRepositoty.existsByName(name)) throw new BadExecption(name + "is used ");
        CoffeeTable newTable = new CoffeeTable();
        newTable.setName(name);
        coffeeTableRepositoty.save(newTable);
    }

    public void updateTable(Integer id, String tableName) {

        CoffeeTable table = coffeeTableRepositoty.findById(id).orElseThrow(() -> new BadExecption(id + "is not Exist"));

        if (!table.getName().equals(tableName)) {
            if (coffeeTableRepositoty.existsByName(tableName)) throw new BadExecption("this name is duplicated");
            table.setName(tableName);
        }
        coffeeTableRepositoty.save(table);
    }

    public CoffeeTable getTable(Integer id) {
        return coffeeTableRepositoty.findById(id).orElseThrow(() -> new BadExecption(id + "doesn't Exist"));
    }

    @Transactional
    public void deleteTable(Integer id) {

        CoffeeTable coffeeTable = coffeeTableRepositoty.findById(id).orElseThrow(() -> new BadExecption("Drink with id " + id + " not found"));

//        List<Bill> billList = billRepository.findBillByCoffeeTable(coffeeTable);
//
//        for (Bill bill:billList
//             ) {
//            bill.setCoffeeTable(null);
//            billRepository.save(bill);
//        }

        coffeeTableRepositoty.delete(coffeeTable);

    }
}
