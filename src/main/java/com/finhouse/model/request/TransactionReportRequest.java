package com.finhouse.model.request;

import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class TransactionReportRequest {
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;

    private Integer merchant;
    private Integer acquirer;
}