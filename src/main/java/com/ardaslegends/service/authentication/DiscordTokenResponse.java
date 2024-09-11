package com.ardaslegends.service.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DiscordTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private String scope;
}