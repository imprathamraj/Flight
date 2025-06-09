package com.paymentgateway.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StripeResponse{

    private String status;
    private String message;
    private String sessionId;
    private String sessionUrl;
}
