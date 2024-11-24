package com.finhouse.service;

import com.finhouse.client.ReportingApiClient;
import com.finhouse.model.request.TransactionQueryRequest;
import com.finhouse.model.request.TransactionReportRequest;
import com.finhouse.model.response.TransactionQueryResponse;
import com.finhouse.model.response.TransactionReportResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final ReportingApiClient apiClient;
    private final AuthService authService;

    public Mono<TransactionQueryResponse> queryTransactions(TransactionQueryRequest request) {
        return authService.login()
                .flatMap(token -> apiClient.queryTransactions(request, token))
                .doOnSuccess(response -> log.info("Successfully queried {} transactions",
                        response.getData() != null ? response.getData().size() : 0))
                .doOnError(error -> log.error("Failed to query transactions", error));
    }

    public Mono<TransactionQueryResponse> getTransaction(String transactionId) {
        return authService.login()
                .flatMap(token -> apiClient.getTransaction(transactionId, token))
                .doOnSuccess(response -> log.info("Successfully retrieved transaction details"))
                .doOnError(error -> log.error("Failed to retrieve transaction details", error));
    }

    public Mono<TransactionReportResponse> getTransactionReport(TransactionReportRequest request) {
        return authService.login()
                .flatMap(token -> apiClient.getTransactionReport(request, token))
                .doOnSuccess(response -> log.info("Successfully generated transaction report"))
                .doOnError(error -> log.error("Failed to generate transaction report", error));
    }

}