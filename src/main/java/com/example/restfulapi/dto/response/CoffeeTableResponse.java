package com.example.restfulapi.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoffeeTableResponse {
   private Integer id;
    private String name;
}
