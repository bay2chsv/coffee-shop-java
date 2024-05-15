package com.example.restfulapi.service;

import com.example.restfulapi.dto.request.DrinkRequest;
import com.example.restfulapi.dto.response.CategoryResponse;
import com.example.restfulapi.dto.response.DrinkResponse;
import com.example.restfulapi.entity.Bill;
import com.example.restfulapi.entity.BillDetail;
import com.example.restfulapi.entity.Category;
import com.example.restfulapi.entity.Drink;
import com.example.restfulapi.exception.BadExecption;
import com.example.restfulapi.repository.BillDetailRepository;
import com.example.restfulapi.repository.CategoryRepository;
import com.example.restfulapi.repository.DrinkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DrinkService {
    private final DrinkRepository drinkRepository;
    private final CategoryRepository categoryRepository;
    private final BillDetailRepository billDetailRepository;
    public List<DrinkResponse> getAllDrinks(String categoryName) {
        List<Drink> drinks;
        if (categoryName != null) {
            drinks = drinkRepository.findDrinksByCategory_Name(categoryName);
        } else {
            drinks = drinkRepository.findAll();
        }

        return mapToDrinkResponseList(drinks);
    }

    private List<DrinkResponse> mapToDrinkResponseList(List<Drink> drinks) {
        List<DrinkResponse> drinkResponses = new ArrayList<>();
        for (Drink item : drinks) {
            drinkResponses.add(mapToDrinkResponse(item));
        }
        return drinkResponses;
    }

    private DrinkResponse mapToDrinkResponse(Drink drink) {
        CategoryResponse categoryResponse = CategoryResponse.builder()
                .id(drink.getCategory().getId())
                .name(drink.getCategory().getName())
                .build();

        return DrinkResponse.builder()
                .price(drink.getPrice())
                .imageUrl(drink.getImageUrl())
                .categoryResponse(categoryResponse)
                .id(drink.getId())
                .name(drink.getName())
                .build();
    }


    public DrinkResponse getDrink(Integer id) {
        Drink drink = drinkRepository.findById(id).orElseThrow(() -> new BadExecption(id + " doesn't exist"));
        CategoryResponse categoryResponse = new CategoryResponse(drink.getCategory().getId(),drink.getCategory().getName());

        return  DrinkResponse.builder()
                .name(drink.getName())
                .imageUrl(drink.getImageUrl())
                .price(drink.getPrice())
                .id(drink.getId())
                .categoryResponse(categoryResponse).build();
    }

    public void createDrink(DrinkRequest menuRequest) {
        if (drinkRepository.existsByName(menuRequest.getName())) {
            throw new BadExecption(menuRequest.getName() + " is used already ");
        }
        Category category = categoryRepository.findById(menuRequest.getCategoryId()).orElseThrow(() -> new BadExecption(menuRequest.getCategoryId() + "  doesn't exist on DB"));
        Drink menu = Drink.builder().name(menuRequest.getName()).price(menuRequest.getPrice()).imageUrl(menuRequest.getImageUrl()).category(category).build();
        drinkRepository.save(menu);
    }

    public void updateDrink(Integer id, DrinkRequest drinkRequest) {
        Drink drink = drinkRepository.findById(id).orElseThrow(() -> new BadExecption(id + " doesn't exist"));

        if (!drink.getName().equals(drinkRequest.getName())) {
            if (drinkRepository.existsByName(drinkRequest.getName()))
                throw new BadExecption(drinkRequest.getName() + " is already used");
            drink.setName(drinkRequest.getName());
        }
        Category category = categoryRepository.findById(drinkRequest.getCategoryId()).orElseThrow(() -> new BadExecption(drinkRequest.getCategoryId() + "  doesn't exist on DB"));
        drink.setCategory(category);
        drink.setPrice(drinkRequest.getPrice());
        drink.setImageUrl(drinkRequest.getImageUrl());
        drinkRepository.save(drink);
    }

    public void deleteDrink(Integer id) {
        Drink drink = drinkRepository.findById(id).orElseThrow(() -> new BadExecption("Drink with id " + id + " not found"));
        List<BillDetail> billDetailList = billDetailRepository.findBillDetailByDrink(drink);
        for (BillDetail billDetail:billDetailList
             ) {
            billDetail.setDrink(null);
            billDetailRepository.save(billDetail);
        }
        drinkRepository.delete(drink);
    }
}
