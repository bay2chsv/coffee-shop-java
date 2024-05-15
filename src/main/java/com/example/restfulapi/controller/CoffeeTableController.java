package com.example.restfulapi.controller;

import com.example.restfulapi.dto.response.CoffeeTableResponse;
import com.example.restfulapi.dto.response.ResourcePaginationResponse;
import com.example.restfulapi.dto.response.ResourceResponse;
import com.example.restfulapi.entity.CoffeeTable;
import com.example.restfulapi.service.CofeeTableSevice;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CoffeeTableController {
    private final CofeeTableSevice cofeeTableSevice;

    @GetMapping("/coffeetables")
    public ResourcePaginationResponse<List<CoffeeTableResponse>> getAllTable(@RequestParam(value = "page",defaultValue = "0",required = false) Integer page , @RequestParam(value = "size",defaultValue = "5",required = false) Integer size) {
        return cofeeTableSevice.getAll(page,size);
    }
    @PostMapping("/coffeetables")
    public ResourceResponse<?> createTable(@RequestParam String name) {
        cofeeTableSevice.createTable(name);
        return ResourceResponse.builder().status(true).message("create table successfully").build();
    }
    @GetMapping("/coffeetables/{id}")
    public ResourceResponse<CoffeeTable> getTableById(@PathVariable Integer id) {
        CoffeeTable table = cofeeTableSevice.getTable(id);
        return ResourceResponse.<CoffeeTable>builder().data(table).status(true).message("get Table sucessfully").build();
    }

    @DeleteMapping("/coffeetables/{id}")
    public ResourceResponse<?> deleteTableById(@PathVariable Integer id) {
        cofeeTableSevice.deleteTable(id);
        return ResourceResponse.<CoffeeTable>builder().status(true).message("delete Table sucessfully").build();
    }
    @PatchMapping("/coffeetables/{id}")
    public ResourceResponse<?> updateTableById(@PathVariable Integer id ,@RequestParam String name) {
        cofeeTableSevice.updateTable(id,name);
        return ResourceResponse.<CoffeeTable>builder().status(true).message("upload Table sucessfully").build();
    }
}
