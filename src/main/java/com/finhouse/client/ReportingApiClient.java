package com.finhouse.client;

import com.finhouse.model.request.LoginRequest;
import com.finhouse.model.request.TransactionRequest;
import com.finhouse.model.request.TransactionQueryRequest;
import com.finhouse.model.request.TransactionReportRequest;
import com.finhouse.model.response.LoginResponse;
import com.finhouse.model.response.TransactionQueryResponse;
import com.finhouse.model.response.TransactionReportResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ReportingApiClient {
    private final WebClient webClient;

    public ReportingApiClient(@Value("${api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<LoginResponse> login(LoginRequest request) {
        return webClient.post()
                .uri("/api/v3/merchant/user/login")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(LoginResponse.class)
                .doOnNext(response -> log.info("Login successful for user: {}", request.getEmail()))
                .doOnError(error -> log.error("Login failed for user: {}", request.getEmail(), error));
    }

    public Mono<TransactionQueryResponse> queryTransactions(TransactionQueryRequest request, String token) {
        return webClient.post()
                .uri("/api/v3/transaction/list")
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TransactionQueryResponse.class)
                .doOnNext(response -> log.debug("Query successful with {} results",
                        response.getData() != null ? response.getData().size() : 0))
                .doOnError(error -> log.error("Query failed", error));
    }

    public Mono<TransactionQueryResponse> getTransaction(String transactionId, String token) {
        return webClient.post()
                .uri("/api/v3/transaction")
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(new TransactionRequest(transactionId))
                .retrieve()
                .bodyToMono(TransactionQueryResponse.class)
                .doOnNext(response -> log.debug("Successfully retrieved transaction details"))
                .doOnError(error -> log.error("Failed to retrieve transaction details", error));
    }

    public Mono<TransactionReportResponse> getTransactionReport(TransactionReportRequest request, String token) {
        return webClient.post()
                .uri("/api/v3/transactions/report")
                .header(HttpHeaders.AUTHORIZATION, token)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(TransactionReportResponse.class)
                .doOnNext(response -> log.debug("Report generated successfully"))
                .doOnError(error -> log.error("Failed to generate report", error));
    }
}