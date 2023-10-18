package com.lcwd.APIGateway.controller;

import com.lcwd.APIGateway.payloads.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@Slf4j
@SuppressWarnings("All")
public class AuthController {

    @GetMapping("/token")
    public ResponseEntity<AuthResponse> login(@RegisteredOAuth2AuthorizedClient("okta") OAuth2AuthorizedClient client,
                                              @AuthenticationPrincipal OidcUser user){

        log.info("Logging in for user {} ",user.getEmail());
        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        AuthResponse authResponse = AuthResponse.builder().userName(user.getEmail())
                                                          .accessToken(client.getAccessToken().getTokenValue())
                                                          .refreshToken(client.getRefreshToken().getTokenValue())
                                                          .expirationDate(user.getExpiresAt().getEpochSecond())
                                                          .authorities(authorities)
                                                          .build();
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }
}
