package com.ardaslegends.service.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Represents the response received from Discord when exchanging an authorization code for an access token.
 * This class is used to deserialize the JSON response from the Discord API.
 */
@Data
public class DiscordTokenResponse {

    /**
     * The access token issued by Discord.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * The type of the token issued by Discord.
     */
    @JsonProperty("token_type")
    private String tokenType;

    /**
     * The duration in seconds for which the access token is valid.
     */
    @JsonProperty("expires_in")
    private int expiresIn;

    /**
     * The refresh token issued by Discord, which can be used to obtain new access tokens.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;

    /**
     * The scope of the access token, indicating the permissions granted.
     */
    private String scope;
}