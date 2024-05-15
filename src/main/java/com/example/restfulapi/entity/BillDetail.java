package com.example.restfulapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetail extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "bill_id",nullable = false)
    private Bill bill;
    private Integer price;
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "drink_id")
    private Drink drink;
}
