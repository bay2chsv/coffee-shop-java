package com.example.restfulapi.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResourcePaginationResponse<T> {
    private boolean status;
    private String message;
    private T data;
    private Integer size;
    private Integer page;
    private Integer totalPages;
    private long totalItem;
}
