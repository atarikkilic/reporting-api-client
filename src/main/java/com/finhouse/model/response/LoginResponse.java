package com.finhouse.model.response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String status;
}