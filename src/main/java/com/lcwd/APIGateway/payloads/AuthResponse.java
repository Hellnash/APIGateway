package com.lcwd.APIGateway.payloads;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String userName;

    private String accessToken;

    private String refreshToken;

    private long expirationDate;

    private Collection<String> authorities;
}
