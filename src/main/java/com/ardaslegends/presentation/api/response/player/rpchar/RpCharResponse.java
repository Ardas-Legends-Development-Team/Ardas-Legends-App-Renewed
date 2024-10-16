package com.ardaslegends.presentation.api.response.player.rpchar;

import com.ardaslegends.domain.RPChar;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;

@Slf4j
@Schema(name = "RpChar", description = "RpChar response containing all RpChar information")
public record RpCharResponse(
        @Schema(description = "Character's name", example = "Aragorn")
        String name,
        @Schema(description = "Character's title", example = "King of Gondor")
        String title,
        @Schema(description = "Character's gear as plain text", example = "Gondorian gear")
        String gear,
        @Schema(description = "If the character participates in pvp", example = "true")
        Boolean pvp,
        @Schema(description = "Region the character is currently located in", example = "102")
        String currentRegion,
        @Schema(description = "Name of army the character is bound to", example = "Gondor Army", nullable = true)
        String boundTo,
        @Schema(description = "Name of the claimbuild the character is stationed in", example = "Minas Tirith", nullable = true)
        String stationedAt,
        @Schema(description = "States if the character is injured", example = "true")
        Boolean injured,
        @Schema(description = "States if the character is currently healing in a House of Healing", example = "true")
        Boolean isHealing,
        @Schema(description = "Timestamp when the character started healing. Null when not healing", nullable = true)
        OffsetDateTime startedHeal,
        @Schema(description = "Timestamp when healing of the character ends. Null when not healing", nullable = true)
        OffsetDateTime healEnds
) {

    public RpCharResponse(RPChar rpChar) {
        this(rpChar.getName(), rpChar.getTitle(), rpChar.getGear(), rpChar.getPvp(), rpChar.getCurrentRegion().getId(),
                rpChar.getBoundTo() == null ? null : rpChar.getBoundTo().getName(), rpChar.getStationedAt() == null ? null : rpChar.getStationedAt().getName(),
                rpChar.getInjured(), rpChar.getIsHealing(), rpChar.getStartedHeal(), rpChar.getHealEnds());
        log.debug("Created RpCharResponse");
    }
}
