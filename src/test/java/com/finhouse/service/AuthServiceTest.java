package com.finhouse.service;

import com.finhouse.client.ReportingApiClient;
import com.finhouse.model.request.LoginRequest;
import com.finhouse.model.response.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private ReportingApiClient apiClient;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldLoginSuccessfully() {
        // Given
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken("test-token");
        loginResponse.setStatus("APPROVED");

        when(apiClient.login(any(LoginRequest.class)))
                .thenReturn(Mono.just(loginResponse));

        // When & Then
        StepVerifier.create(authService.login())
                .expectNext("test-token")
                .verifyComplete();
    }
}