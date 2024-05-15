package com.example.restfulapi.dto.response;

import jakarta.persistence.Column;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
   private Integer id;
    private String name;
}
