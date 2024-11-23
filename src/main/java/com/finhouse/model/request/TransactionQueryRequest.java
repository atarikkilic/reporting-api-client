package com.finhouse.model.request;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class TransactionQueryRequest {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    private String status;
    private String operation;
    private Integer merchantId;
    private Integer acquirerId;
    private String paymentMethod;
    private String errorCode;
    private String filterField;
    private String filterValue;
    private Integer page;
}