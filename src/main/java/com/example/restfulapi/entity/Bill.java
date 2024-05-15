package com.example.restfulapi.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bill extends BaseEntity {

    private Integer total;
    private LocalDateTime createdDate;

    @Column()
    private Boolean isCancel;

    @ManyToOne
    @JoinColumn(name = "table_id" )
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private CoffeeTable coffeeTable;

    @OneToMany(mappedBy = "bill" ,  cascade = CascadeType.REMOVE)
    private List<BillDetail> billDetails;

    @PrePersist
    public void prePersist() {
        if (isCancel == null) {
            isCancel = false;
        }
    }

}
