package com.example.restfulapi.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity{
    @Column(nullable = false, unique = true,length = 50)
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Drink> drinks;
}
