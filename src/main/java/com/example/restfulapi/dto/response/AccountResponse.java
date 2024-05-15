package com.example.restfulapi.dto.response;

import com.example.restfulapi.entity.Role;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponse {

    private Integer id;
    private String fullName;
    private String email;
    private Role role;
    private Boolean isBlock;
}
