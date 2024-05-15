package com.example.restfulapi.dto.response;

import com.example.restfulapi.entity.Category;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DrinkResponse {
    private Integer id;
    private String name;
    private Integer price;

    private String imageUrl;
    private CategoryResponse categoryResponse;
}
