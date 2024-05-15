package com.example.restfulapi.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResourceResponse<T> {
    private boolean status;
    private String message;
    private T data;

}
