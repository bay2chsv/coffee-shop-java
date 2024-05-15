package com.example.restfulapi.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrinkRequest {

    private String name;
    private Integer price;
    private String imageUrl;
    private Integer categoryId;
}
