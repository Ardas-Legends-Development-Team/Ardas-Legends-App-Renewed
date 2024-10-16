package com.ardaslegends.presentation.api;

import com.ardaslegends.presentation.api.response.authentication.AuthenticateResponse;
import com.ardaslegends.service.authentication.AuthenticationService;
import com.ardaslegends.service.authentication.DiscordTokenResponse;
import com.ardaslegends.service.authentication.JwtUtil;
import com.ardaslegends.service.dto.authentication.AuthenticateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing authentication.
 * <p>
 * This controller provides an endpoint for authenticating users via Discord.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@Tag(name = "Authentication Controller", description = "REST endpoints concerning Authentication")
@RequestMapping(AuthenticationRestController.BASE_URL)
public class AuthenticationRestController {
    public static final String BASE_URL = "/auth";

    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;

    /**
     * Authenticates a user using a Discord access code.
     * <p>
     * This method receives a Discord access code, generates a Discord token by calling the Discord API, and returns a generated JWT to access the API.
     * </p>
     *
     * @param dto the data transfer object containing the Discord access code.
     * @return the {@link AuthenticateResponse} containing the generated JWT, Discord ID, and token expiration time.
     */
    @Operation(summary = "Authenticate", description = "Receives a discord access code and generates a discord token by calling the discord API. Returns a generated JWT to access the API.")
    @PostMapping
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateDto dto) {
        log.info("Incoming authenticate Request, accessCode [{}]", dto);
        log.debug("Calling authenticationService.generateDiscordTokenFromCode");
        DiscordTokenResponse accessToken = authenticationService.generateDiscordTokenFromCode(dto.accessCode());

        log.debug("Fetch user discord ID");
        String discordId = authenticationService.getDiscordIdFromAccessToken(accessToken.getAccessToken());
        // verify user is in the guild
        // fetch user ID from DB to see if he's registered
        // if user is not in the DB, inform user to register

        log.debug("Generating JWT and storing in DB");
        String jwt = jwtUtil.generateToken(discordId, accessToken.getAccessToken(), accessToken.getExpiresIn());
        // store in DB

        log.debug("Building response");
        val authenticateResponse = new AuthenticateResponse(jwt, discordId, accessToken.getExpiresIn());

        log.info("Sending successful authenticate response [{}]", authenticateResponse);
        return ResponseEntity.ok(authenticateResponse);
    }
}