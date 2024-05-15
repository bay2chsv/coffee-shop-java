package com.example.restfulapi.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillDetailResponse {
    private BillResponse billResponse;
    private List<DetailResponse> detailResponses;
}
