package com.example.restfulapi.controller;

import com.example.restfulapi.dto.response.CategoryResponse;
import com.example.restfulapi.dto.response.ResourcePaginationResponse;
import com.example.restfulapi.dto.response.ResourceResponse;
import com.example.restfulapi.entity.Category;
import com.example.restfulapi.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResourcePaginationResponse<List<CategoryResponse>> getAllCategory(@RequestParam(value = "page",defaultValue = "0",required = false) Integer page , @RequestParam(value = "size",defaultValue = "5",required = false) Integer size) {
        return categoryService.getAll(page, size);
    }

    @GetMapping("/categories/{id}")
    public ResourceResponse<Category> getCategory(@PathVariable Integer id) {
        Category category = categoryService.getCategory(id);
        return ResourceResponse.<Category>builder().data(category).message("get category id").status(true).build();
    }
    @PostMapping("/categories")
    public ResourceResponse<?> createCategory(@RequestParam String name){
        categoryService.createCategory(name);
        return ResourceResponse.builder().status(true).message("create table successfully").build();
    }
    @PatchMapping("/categories/{id}")
    public ResourceResponse<?> updateCategory(@PathVariable Integer id,@RequestParam String name){
        categoryService.updateCategoty(id,name);
        return ResourceResponse.builder().status(true).message("update table successfully").build();
    }

    @DeleteMapping("/categories/{id}")
    public ResourceResponse<?> updateCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return ResourceResponse.builder().status(true).message("delete table successfully").build();
    }



}
