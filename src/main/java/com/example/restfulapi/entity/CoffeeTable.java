package com.example.restfulapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CoffeeTable extends BaseEntity {
    @Column(nullable = false, unique = true,length = 50)
    private String name;


    @OneToMany(mappedBy = "coffeeTable")
    private List<Bill> bill;
}
