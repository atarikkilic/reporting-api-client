package com.finhouse.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class TransactionRequest {
    @NotBlank
    private String transactionId;
}