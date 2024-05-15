package com.example.restfulapi.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder()
public class AuthResponse {
    private String email;
    private String userName;
    private String message;
}
