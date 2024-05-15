package com.example.restfulapi.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthRequest {
    private String email;
    private String password;
    private String name;
}
