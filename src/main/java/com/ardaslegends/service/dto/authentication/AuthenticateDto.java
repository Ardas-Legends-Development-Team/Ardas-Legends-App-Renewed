package com.ardaslegends.service.dto.authentication;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthenticateDto(
        @Schema(description = "Discord temporary access code", example = "261173268365443074")
        String accessCode) {
}
