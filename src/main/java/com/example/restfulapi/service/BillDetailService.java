package com.example.restfulapi.service;

import com.example.restfulapi.dto.request.BillDetailRequest;
import com.example.restfulapi.dto.response.DetailResponse;
import com.example.restfulapi.entity.Bill;
import com.example.restfulapi.entity.BillDetail;
import com.example.restfulapi.entity.Drink;
import com.example.restfulapi.exception.BadExecption;
import com.example.restfulapi.repository.BillDetailRepository;
import com.example.restfulapi.repository.BillRepository;
import com.example.restfulapi.repository.DrinkRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BillDetailService {
    private final BillDetailRepository billDetailRepository;
    private final DrinkRepository drinkRepository;
    private final BillRepository billRepository;

    public List<DetailResponse> getBillDetail(Integer id) {
        if(!billRepository.existsById(id)){
            throw new BadExecption(id + "doesn't exist");
        }
        List<BillDetail> billDetailList = billDetailRepository.findBillDetailByBill_Id(id);
        List<DetailResponse> responses = new ArrayList<>();
        for (BillDetail item: billDetailList
             ) {
            DetailResponse detailResponse = new DetailResponse();
            detailResponse.setId(item.getId());
            detailResponse.setDrink(item.getDrink());
            detailResponse.setQuantity(item.getQuantity());
            detailResponse.setPrice(item.getPrice());
            responses.add(detailResponse);
        }
        return responses;
    }
    public void createBillDetail(Bill bill, BillDetailRequest billDetailRequest) {
        Drink drink = drinkRepository.findById(billDetailRequest.getDrinkId()).orElseThrow(() -> new BadExecption("id:"+billDetailRequest.getDrinkId()+" not found"));
        BillDetail billDetail = BillDetail.builder()
                .bill(bill).drink(drink)
                .price(drink.getPrice())
                .quantity(billDetailRequest.getQuantity())
                .build();
        billDetailRepository.save(billDetail);
    }
}
