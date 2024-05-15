package com.example.restfulapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Drink extends BaseEntity {
    @Column(nullable = false,length = 50 ,unique = true)
    private String name;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false ,length = 200)
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name = "category_id" )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Category category;
    @OneToMany(mappedBy = "drink")
    private List<BillDetail> billDetail;
}
