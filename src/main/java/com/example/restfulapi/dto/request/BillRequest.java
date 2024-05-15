package com.example.restfulapi.dto.request;

import com.example.restfulapi.entity.CoffeeTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillRequest {
    private Integer total;
    private Integer coffeeTableId;
    private List<BillDetailRequest> billDetailRequest;

}
