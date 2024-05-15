package com.example.restfulapi.service;

import com.example.restfulapi.dto.request.BillDetailRequest;
import com.example.restfulapi.dto.request.BillRequest;
import com.example.restfulapi.dto.response.*;
import com.example.restfulapi.entity.Bill;
import com.example.restfulapi.entity.CoffeeTable;
import com.example.restfulapi.exception.BadExecption;
import com.example.restfulapi.repository.BillRepository;
import com.example.restfulapi.repository.CoffeeTableRepositoty;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final CoffeeTableRepositoty coffeeTableRepositoty;
    private final BillDetailService billDetailService;


    public ResourcePaginationResponse<List<BillResponse>> getAllBill(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bill> item = billRepository.findAll(pageable);
        List<BillResponse> response = new ArrayList<>();
        for (Bill newBill : item.getContent()) {
            BillResponse billResponse = new BillResponse();

            billResponse.setId(newBill.getId());
            billResponse.setTotal(newBill.getTotal());
            billResponse.setCreatedDate(newBill.getCreatedDate());
            CoffeeTableResponse coffeeTableResponse = new CoffeeTableResponse();
            coffeeTableResponse.setName(newBill.getCoffeeTable().getName());
            coffeeTableResponse.setId(newBill.getCoffeeTable().getId());
            billResponse.setCoffeeTable(coffeeTableResponse);
            billResponse.setIsCancel(newBill.getIsCancel());
            response.add(billResponse);
        }
        return ResourcePaginationResponse.<List<BillResponse>>builder().message("get bill list ").status(true).data(response).page(item.getNumber()).size(item.getSize()).totalPages(item.getTotalPages()).totalItem(item.getTotalElements()).build();
    }

    public BillDetailResponse getBill(Integer id) { //
        Bill bill = billRepository.findById(id).orElseThrow(() -> new BadExecption(id + "not found"));
        BillResponse billResponse = new BillResponse();
        billResponse.setIsCancel(bill.getIsCancel());
        billResponse.setTotal(bill.getTotal());
        CoffeeTableResponse coffeeTableResponse = new CoffeeTableResponse();
        coffeeTableResponse.setName(bill.getCoffeeTable().getName());
        coffeeTableResponse.setId(bill.getCoffeeTable().getId());
        billResponse.setCoffeeTable(coffeeTableResponse);
        billResponse.setCreatedDate(bill.getCreatedDate());
        List<DetailResponse> detailResponseList = billDetailService.getBillDetail(id);
        return BillDetailResponse.builder().billResponse(billResponse).detailResponses(detailResponseList).build();
    }
    @Transactional
    public void createBill(BillRequest billRequest) {
        CoffeeTable table = coffeeTableRepositoty.findById(billRequest.getCoffeeTableId()).orElseThrow(() -> new BadExecption(billRequest.getCoffeeTableId() + "doesn't exist"));
        Bill bill = Bill.builder()
                .total(billRequest.getTotal())
                .createdDate(Instant.now().atZone(ZoneOffset.systemDefault()).toLocalDateTime())
                .coffeeTable(table)
                .build();
        Bill newbill = billRepository.save(bill);
        for (BillDetailRequest billDetail : billRequest.getBillDetailRequest()) {
            billDetailService.createBillDetail(newbill, billDetail);
        }
    }

    public void cancelBill(Integer id, Boolean cancel) {
        if(cancel==null){
            throw new BadExecption("cancel is null");
        }

        Bill bill = billRepository.findById(id).orElseThrow(() -> new BadExecption(id + "doesn't exist"));
        bill.setIsCancel(cancel);
        billRepository.save(bill);
    }

    public void deleteBill(Integer id) {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new BadExecption(id + "doesn't exist"));
        if (!bill.getIsCancel()) {
            throw new BadExecption("Only cancel status Bill can be removed");
        }
        billRepository.deleteById(id);

    }

}
