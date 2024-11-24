package com.finhouse.service;

import com.finhouse.client.ReportingApiClient;
import com.finhouse.model.request.TransactionQueryRequest;
import com.finhouse.model.request.TransactionReportRequest;
import com.finhouse.model.response.TransactionQueryResponse;
import com.finhouse.model.response.TransactionReportResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private ReportingApiClient apiClient;

    @Mock
    private AuthService authService;

    @InjectMocks
    private ReportService reportService;

    private static final String TEST_TOKEN = "test-token";

    @BeforeEach
    void setUp() {
        when(authService.login()).thenReturn(Mono.just(TEST_TOKEN));
    }

    @Test
    void shouldQueryTransactionsSuccessfully() {
        // Given
        TransactionQueryRequest request = TransactionQueryRequest.builder()
                .fromDate(LocalDate.now().minusDays(7))
                .toDate(LocalDate.now())
                .build();

        TransactionQueryResponse expectedResponse = new TransactionQueryResponse();
        expectedResponse.setData(Collections.emptyList());

        when(apiClient.queryTransactions(any(TransactionQueryRequest.class), anyString()))
                .thenReturn(Mono.just(expectedResponse));

        // When & Then
        StepVerifier.create(reportService.queryTransactions(request))
                .expectNext(expectedResponse)
                .verifyComplete();
    }

    @Test
    void shouldGenerateTransactionReportSuccessfully() {
        // Given
        TransactionReportRequest request = TransactionReportRequest.builder()
                .fromDate(LocalDate.now().minusDays(7))
                .toDate(LocalDate.now())
                .build();

        TransactionReportResponse expectedResponse = new TransactionReportResponse();
        expectedResponse.setStatus("APPROVED");
        expectedResponse.setResponse(Collections.emptyList());

        when(apiClient.getTransactionReport(any(TransactionReportRequest.class), anyString()))
                .thenReturn(Mono.just(expectedResponse));

        // When & Then
        StepVerifier.create(reportService.getTransactionReport(request))
                .expectNext(expectedResponse)
                .verifyComplete();
    }
}