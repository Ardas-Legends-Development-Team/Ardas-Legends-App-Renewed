package com.ardaslegends.presentation.api.response.player;

import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.Role;
import com.ardaslegends.presentation.api.response.player.rpchar.RpCharResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@Schema(name = "Player with RpChar", description = "Player response including RpChar information")
public record PlayerRpCharResponse(

        @Schema(description = "Player's Discord ID", example = "1015367754771083405")
        String discordId,
        @Schema(description = "Player's Minecraft IGN", example = "VernonRoche")
        String ign,
        @Schema(description = "Faction name of new player", example = "Gondor")
        String faction,
        @Schema(description = "Player's Roleplay Character")
        RpCharResponse rpChar,
        @Schema(description = "Roles of the player", example = "ROLE_USER")
        Set<Role> roles
) {

    public PlayerRpCharResponse(Player player) {
        this(player.getDiscordID(), player.getIgn(), player.getFaction().getName(), (player.getActiveCharacter().isEmpty() ? null : new RpCharResponse(player.getActiveCharacter().get())), player.getRoles());
        log.debug("Created PlayerResponse: '{}'", this);
    }
}