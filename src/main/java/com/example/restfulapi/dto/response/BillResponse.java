package com.example.restfulapi.dto.response;

import com.example.restfulapi.entity.CoffeeTable;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillResponse {
    private Integer id;
    private Integer total;
    private LocalDateTime createdDate;
    private Boolean isCancel;
    private CoffeeTableResponse coffeeTable;
}
