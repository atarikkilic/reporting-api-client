package com.finhouse.controller;

import com.finhouse.model.request.TransactionQueryRequest;
import com.finhouse.model.request.TransactionReportRequest;
import com.finhouse.model.response.TransactionQueryResponse;
import com.finhouse.model.response.TransactionReportResponse;
import com.finhouse.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/transactions")
    public Mono<TransactionQueryResponse> queryTransactions(@RequestBody @Valid TransactionQueryRequest request) {
        return reportService.queryTransactions(request)
                .doOnSuccess(response -> log.info("Transaction query completed successfully"))
                .doOnError(error -> log.error("Transaction query failed", error));
    }

    @GetMapping("/transactions/{transactionId}")
    public Mono<TransactionQueryResponse> getTransaction(@PathVariable String transactionId) {
        return reportService.getTransaction(transactionId)
                .doOnSuccess(response -> log.info("Transaction details retrieved successfully"))
                .doOnError(error -> log.error("Failed to retrieve transaction details", error));
    }

    @PostMapping("/transaction-report")
    public Mono<TransactionReportResponse> getTransactionReport(@RequestBody @Valid TransactionReportRequest request) {
        return reportService.getTransactionReport(request)
                .doOnSuccess(response -> log.info("Transaction report endpoint called successfully"))
                .doOnError(error -> log.error("Failed to call transaction report endpoint", error));
    }

}