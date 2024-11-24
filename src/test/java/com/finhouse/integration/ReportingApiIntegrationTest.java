package com.finhouse.integration;

import com.finhouse.model.request.LoginRequest;
import com.finhouse.model.request.TransactionQueryRequest;
import com.finhouse.model.request.TransactionReportRequest;
import com.finhouse.model.response.LoginResponse;
import com.finhouse.model.response.TransactionQueryResponse;
import com.finhouse.model.response.TransactionReportResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.time.LocalDate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ReportingApiIntegrationTest {

    private WebTestClient webTestClient;
    private String authToken;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("https://sandbox-reporting.rpdpymnt.com")
                .build();


        authToken = login();
    }

    private String login() {
        LoginRequest loginRequest = LoginRequest.builder()
                .email("demo@financialhouse.io")
                .password("cjaiU8CV")
                .build();

        LoginResponse response = webTestClient.post()
                .uri("/api/v3/merchant/user/login")
                .bodyValue(loginRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(LoginResponse.class)
                .returnResult()
                .getResponseBody();

        return response != null ? response.getToken() : "";
    }

    @Test
    void shouldQueryTransactions() {
        TransactionQueryRequest request = TransactionQueryRequest.builder()
                .fromDate(LocalDate.now().minusDays(7))
                .toDate(LocalDate.now())
                .build();

        webTestClient.post()
                .uri("/api/v3/transaction/list")
                .header("Authorization", authToken)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionQueryResponse.class)
                .value(response -> {
                    assert response != null;
                    assert response.getData() != null;
                });
    }

    @Test
    void shouldGenerateTransactionReport() {
        TransactionReportRequest request = TransactionReportRequest.builder()
                .fromDate(LocalDate.now().minusDays(7))
                .toDate(LocalDate.now())
                .build();

        webTestClient.post()
                .uri("/api/v3/transactions/report")
                .header("Authorization", authToken)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionReportResponse.class)
                .value(response -> {
                    assert response != null;
                    assert "APPROVED".equals(response.getStatus());
                });
    }

    @Test
    void shouldHandleInvalidDateRange() {
        TransactionQueryRequest request = TransactionQueryRequest.builder()
                .fromDate(LocalDate.now())
                .toDate(LocalDate.now().minusDays(7))
                .build();

        webTestClient.post()
                .uri("/api/v3/transaction/list")
                .header("Authorization", authToken)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.data").isEmpty();
    }

    @Test
    void shouldHandleInvalidToken() {
        TransactionQueryRequest request = TransactionQueryRequest.builder()
                .fromDate(LocalDate.now().minusDays(7))
                .toDate(LocalDate.now())
                .build();

        webTestClient.post()
                .uri("/api/v3/transaction/list")
                .header("Authorization", "invalid-token")
                .bodyValue(request)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectBody()
                .jsonPath("$.status").isEqualTo("DECLINED")
                .jsonPath("$.message").isEqualTo("Wrong number of segments");
    }

    @Test
    void shouldHandleTokenExpiration() {

        String initialToken = login();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        String newToken = login();


        assert !initialToken.equals(newToken) : "The new token should not be the same as the old token";


        TransactionQueryRequest request = TransactionQueryRequest.builder()
                .fromDate(LocalDate.now().minusDays(7))
                .toDate(LocalDate.now())
                .build();

        webTestClient.post()
                .uri("/api/v3/transaction/list")
                .header("Authorization", initialToken)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(TransactionQueryResponse.class)
                .value(response -> {
                    assert response != null;
                    assert response.getData() != null;
                });
    }
}