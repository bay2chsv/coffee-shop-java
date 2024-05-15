package com.example.restfulapi.dto.request;

import com.example.restfulapi.entity.Bill;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetailRequest {
    private Integer quantity;
    private Integer drinkId;
}
