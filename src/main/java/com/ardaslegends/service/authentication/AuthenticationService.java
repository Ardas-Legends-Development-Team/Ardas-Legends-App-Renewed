package com.ardaslegends.service.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationService {

    private final RestTemplate restTemplate;

    @Value("${ardaslegends.auth.client_id}")
    private String clientId;

    @Value("${ardaslegends.auth.client_secret}")
    private String clientSecret;

    @Value("${ardaslegends.auth.redirect_uri}")
    private String redirectUri;

    @Value("${ardaslegends.auth.auth_url}")
    private String authUrl;

    /**
     * This method takes a discord access code and generates a discord token by calling the discord API.
     *
     * @param discordAccessCode The access code received from the frontend, when user authenticates via Discord.
     * @return The discord token generated by the discord API. This will be later saved in the database.
     */
    public DiscordTokenResponse generateDiscordTokenFromCode(String discordAccessCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("code", discordAccessCode);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", redirectUri);
        body.add("scope", "identify guilds");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<DiscordTokenResponse> response = restTemplate.exchange(authUrl, HttpMethod.POST, requestEntity, DiscordTokenResponse.class);
        log.debug("Discord token response: {}", response);
        log.debug("Discord token status code: {}", response.getStatusCode());
        log.debug("Discord token response body: {}", response.getBody());

        return response.getBody();
    }

    /**
     * This method takes a discord access token and queries the discord API for the user's discord ID.
     *
     * @param discordAccessToken The access token received from the authentication service, when user authenticates via Discord and we get the access token.
     * @return The discord ID of the user.
     */
    public String getDiscordIdFromAccessToken(String discordAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + discordAccessToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange("https://discord.com/api/users/@me", HttpMethod.GET, requestEntity, String.class);
        log.debug("Discord user response: {}", response);
        log.debug("Discord user status code: {}", response.getStatusCode());
        log.debug("Discord user response body: {}", response.getBody());

        return response.getBody();
    }
}
