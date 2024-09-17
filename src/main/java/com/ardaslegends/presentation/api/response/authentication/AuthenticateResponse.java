package com.ardaslegends.presentation.api.response.authentication;

public record AuthenticateResponse(String jwt, String discordId, int expirationTime) {
}
