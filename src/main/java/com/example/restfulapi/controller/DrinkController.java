package com.example.restfulapi.controller;

import com.example.restfulapi.dto.request.DrinkRequest;
import com.example.restfulapi.dto.response.DrinkResponse;
import com.example.restfulapi.dto.response.ResourceResponse;
import com.example.restfulapi.entity.Drink;
import com.example.restfulapi.service.DrinkService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class DrinkController {

    private final DrinkService drinkService;

    @GetMapping("/drinks")
    public ResourceResponse<List<DrinkResponse>> getallDrink(@RequestParam(value = "categoryName", required = false) String categoryName) {
        List<DrinkResponse> drinks = drinkService.getAllDrinks(categoryName);
        return ResourceResponse.<List<DrinkResponse>>builder().message("get drink list").status(true).data(drinks).build();
    }
    @GetMapping("/drinks/{id}")
    public ResourceResponse<DrinkResponse> getDrink(@PathVariable Integer id){
        DrinkResponse drink = drinkService.getDrink(id);
        return ResourceResponse.<DrinkResponse>builder().status(true).data(drink).message("get Drink").build();
    }
    @PostMapping("/drinks")
    public ResourceResponse<?> createDrink(@RequestBody DrinkRequest drinkRequest) {
        drinkService.createDrink(drinkRequest);
        //Drink and food
        return ResourceResponse.builder().status(true).message("create drink successfully").build();
    }
    @PatchMapping("/drinks/{id}")
    public ResourceResponse<?> updateDrink(@PathVariable Integer id,@RequestBody DrinkRequest drinkRequest) {
        drinkService.updateDrink( id,drinkRequest);
        return ResourceResponse.builder().status(true).message("update drink successfully").build();
    }

    @DeleteMapping("/drinks/{id}")
    public ResourceResponse<?> deleteDrink(@PathVariable Integer id){
        drinkService.deleteDrink(id);
        return ResourceResponse.builder().message("delete drink successfully").status(true).build();
    }

}

