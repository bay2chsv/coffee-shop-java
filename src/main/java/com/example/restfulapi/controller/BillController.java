package com.example.restfulapi.controller;

import com.example.restfulapi.dto.request.BillRequest;
import com.example.restfulapi.dto.response.BillDetailResponse;
import com.example.restfulapi.dto.response.BillResponse;
import com.example.restfulapi.dto.response.ResourcePaginationResponse;
import com.example.restfulapi.dto.response.ResourceResponse;
import com.example.restfulapi.entity.Bill;
import com.example.restfulapi.entity.BillDetail;
import com.example.restfulapi.service.BillService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BillController {
    private final BillService billService;

    @GetMapping("/bills")
    public ResourcePaginationResponse<List<BillResponse>> getAllAccount(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page, @RequestParam(value = "size", defaultValue = "5", required = false) Integer size) {
        return billService.getAllBill(page, size);
    }
    @GetMapping("/bills/{id}")
    public ResourceResponse<BillDetailResponse> getCategory(@PathVariable Integer id) {
        BillDetailResponse bill = billService.getBill(id);
        return ResourceResponse.<BillDetailResponse>builder().data(bill).message("get  bill detail").status(true).build();
    }
    @PostMapping("/bills")
    public ResourceResponse<?> createCategory(@RequestBody BillRequest billRequest){
        billService.createBill(billRequest);
        return ResourceResponse.builder().status(true).message("create bill successfully").build();
    }
    @PatchMapping("/bills/{id}")
    public ResourceResponse<?> cancelBill(@PathVariable Integer id,@RequestParam Boolean cancel){
        billService.cancelBill(id, cancel);
        return ResourceResponse.builder().status(true).message("Sent request cancel bill successfully").build();
    }
    @DeleteMapping("/bills/{id}")
    public ResourceResponse<?> deleteBill(@PathVariable Integer id){
        billService.deleteBill(id);
        return ResourceResponse.builder().status(true).message("delete bill successfully").build();
    }

}
