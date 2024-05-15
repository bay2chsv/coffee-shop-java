package com.example.restfulapi.dto.response;

import com.example.restfulapi.entity.Drink;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetailResponse {
    private Integer id;
    private Integer price;
    private Integer quantity;
    private Drink drink;
}
