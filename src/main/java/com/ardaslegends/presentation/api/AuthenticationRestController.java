package com.ardaslegends.presentation.api;

import com.ardaslegends.presentation.api.response.authentication.AuthenticateResponse;
import com.ardaslegends.service.authentication.AuthenticationService;
import com.ardaslegends.service.authentication.DiscordTokenResponse;
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

@RequiredArgsConstructor

@Slf4j
@RestController
@Tag(name = "Authentication Controller", description = "REST endpoints concerning Authentication")
@RequestMapping(AuthenticationRestController.BASE_URL)
public class AuthenticationRestController {
    public static final String BASE_URL = "/api/auth";

    private final AuthenticationService authenticationService;

    @Operation(summary = "Authenticate", description = "Receives a discord access code and generates a discord token by calling the discord API. Returns a generated JWT to access the API.")
    @PostMapping
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateDto dto) {
        log.debug("Incoming authenticate Request, dto [{}]", dto);

        log.debug("Calling authenticationService.generateDiscordTokenFromCode");
        DiscordTokenResponse accessToken = authenticationService.generateDiscordTokenFromCode(dto.accessCode());

        log.debug("Building response");
        val authenticateResponse = new AuthenticateResponse();

        log.info("Sending successful authenticate response [{}]", authenticateResponse);
        return ResponseEntity.ok(authenticateResponse);
    }
}
