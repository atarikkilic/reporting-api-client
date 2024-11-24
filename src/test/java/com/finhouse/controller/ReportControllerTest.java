package com.finhouse.controller;

import com.finhouse.model.request.TransactionQueryRequest;
import com.finhouse.model.request.TransactionReportRequest;
import com.finhouse.model.response.TransactionQueryResponse;
import com.finhouse.model.response.TransactionReportResponse;
import com.finhouse.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ReportService reportService;

    @Test
    void shouldQueryTransactionsSuccessfully() {

        TransactionQueryRequest request = TransactionQueryRequest.builder()
                .fromDate(LocalDate.now().minusDays(7))
                .toDate(LocalDate.now())
                .build();

        TransactionQueryResponse response = new TransactionQueryResponse();
        response.setData(Collections.emptyList());

        when(reportService.queryTransactions(any(TransactionQueryRequest.class)))
                .thenReturn(Mono.just(response));


        webTestClient.post()
                .uri("/api/reports/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionQueryResponse.class)
                .isEqualTo(response);
    }

    @Test
    void shouldGenerateTransactionReportSuccessfully() {

        TransactionReportRequest request = TransactionReportRequest.builder()
                .fromDate(LocalDate.now().minusDays(7))
                .toDate(LocalDate.now())
                .build();

        TransactionReportResponse response = new TransactionReportResponse();
        response.setStatus("APPROVED");
        response.setResponse(Collections.emptyList());

        when(reportService.getTransactionReport(any(TransactionReportRequest.class)))
                .thenReturn(Mono.just(response));

        
        webTestClient.post()
                .uri("/api/reports/transaction-report")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionReportResponse.class)
                .isEqualTo(response);
    }
}