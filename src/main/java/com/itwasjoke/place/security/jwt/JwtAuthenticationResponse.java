package com.itwasjoke.place.security.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthenticationResponse {
    private String token;

    private String code;
    private boolean confirmed;

    public JwtAuthenticationResponse(String token, String code, boolean confirmed) {
        this.token = token;
        this.code = code;
        this.confirmed = confirmed;
    }
}