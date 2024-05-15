package com.example.restfulapi.service;

import com.example.restfulapi.dto.response.CategoryResponse;
import com.example.restfulapi.dto.response.ResourcePaginationResponse;
import com.example.restfulapi.entity.Bill;
import com.example.restfulapi.entity.Category;
import com.example.restfulapi.entity.CoffeeTable;
import com.example.restfulapi.entity.Drink;
import com.example.restfulapi.exception.BadExecption;
import com.example.restfulapi.repository.CategoryRepository;
import com.example.restfulapi.repository.DrinkRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final DrinkRepository drinkRepository;
    public ResourcePaginationResponse<List<CategoryResponse>>  getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> item = categoryRepository.findAll(pageable);
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Category obj:item.getContent()
             ) {
            CategoryResponse categoryResponse= CategoryResponse.builder().id(obj.getId()).name(obj.getName()).build();
       categoryResponses.add(categoryResponse);
        }
        return ResourcePaginationResponse.<List<CategoryResponse>>builder()
                .message("get category list")
                .status(true)
                .data(categoryResponses)
                .page(item.getNumber())
                .size(item.getSize())
                .totalPages(item.getTotalPages())
                .totalItem(item.getTotalElements())
                .build();
    }

    public void createCategory(String name) {
        if (categoryRepository.existsByName(name)) throw new BadExecption(name + "is used ");
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
    }

    public void updateCategoty(Integer id, String tableName) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new BadExecption(id + "is not Exist"));

        if (!category.getName().equals(tableName)) {
            if (categoryRepository.existsByName(tableName)) throw new BadExecption("this name is duplicated");
            category.setName(tableName);
        }
        categoryRepository.save(category);
    }

    public Category getCategory(Integer id) {
        return categoryRepository.findById(id).orElseThrow(() -> new BadExecption("Category is not found with id:"+id));
    }

    public void deleteCategory(Integer id) {
        Category category =categoryRepository.findById(id).orElseThrow(() -> new BadExecption("Drink with id " + id + " not found"));

        List<Drink> drinkList = drinkRepository.findDrinksByCategory(category);
        for (Drink drink:drinkList
             ) {
            drink.setCategory(null);
            drinkRepository.save(drink);
        }

        categoryRepository.delete(category);
    }
}
