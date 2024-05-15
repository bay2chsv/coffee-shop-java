package com.example.restfulapi.dto.request;

import com.example.restfulapi.entity.Role;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {
    private String fullName;
    private String email;
    private String password;
    private Integer roleId;
    private Boolean isBlock;
}
