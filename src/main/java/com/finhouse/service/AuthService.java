package com.finhouse.service;

import com.finhouse.client.ReportingApiClient;
import com.finhouse.model.request.LoginRequest;
import com.finhouse.model.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final ReportingApiClient apiClient;
    private static final String DEFAULT_EMAIL = "demo@financialhouse.io";
    private static final String DEFAULT_PASSWORD = "cjaiU8CV";

    public Mono<String> login() {
        return login(DEFAULT_EMAIL, DEFAULT_PASSWORD);
    }

    public Mono<String> login(String email, String password) {
        return apiClient.login(LoginRequest.builder()
                        .email(email)
                        .password(password)
                        .build())
                .map(LoginResponse::getToken)
                .cache(Duration.ofMinutes(9))
                .doOnSuccess(token -> log.debug("Successfully obtained authentication token"))
                .doOnError(error -> log.error("Failed to obtain authentication token", error));
    }
}