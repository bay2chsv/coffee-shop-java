package com.example.restfulapi.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account extends BaseEntity {
    @Column(nullable = false, length = 120)
    private String fullName;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 120)
    private String password;

    private Boolean isBlock;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @PrePersist
    public void prePersist() {
        if (isBlock == null) {
            isBlock = false;
        }
    }

}
