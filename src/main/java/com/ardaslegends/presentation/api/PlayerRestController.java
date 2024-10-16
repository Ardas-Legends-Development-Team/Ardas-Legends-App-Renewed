package com.ardaslegends.presentation.api;

import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.RPChar;
import com.ardaslegends.presentation.AbstractRestController;
import com.ardaslegends.presentation.api.response.PaginatedPlayerResponse;
import com.ardaslegends.presentation.api.response.player.PlayerResponse;
import com.ardaslegends.presentation.api.response.player.PlayerRpCharResponse;
import com.ardaslegends.presentation.api.response.player.PlayerUpdateDiscordIdResponse;
import com.ardaslegends.presentation.api.response.player.rpchar.RpCharResponse;
import com.ardaslegends.service.FactionService;
import com.ardaslegends.service.PlayerService;
import com.ardaslegends.service.dto.player.*;
import com.ardaslegends.service.dto.player.rpchar.CreateRPCharDto;
import com.ardaslegends.service.dto.player.rpchar.UpdateRpCharDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

/**
 * REST controller for managing Player entities.
 * <p>
 * This controller provides endpoints for creating, retrieving, updating, and deleting Player entities.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@Tag(name = "Player Controller", description = "All REST endpoints regarding Players")
@RequestMapping(PlayerRestController.BASE_URL)
public class PlayerRestController extends AbstractRestController {

    public final static String BASE_URL = "/api/player";
    public static final String PATH_RPCHAR = "/rpchar";
    public static final String PATH_FACTION = "/faction";
    public static final String PATH_IGN = "/ign";
    public static final String PATH_DISCORDID = "/discordid";
    public static final String PATH_RPCHAR_NAME = "/rpchar/name";
    public static final String PATH_RPCHAR_TITLE = "/rpchar/title";
    public static final String PATH_RPCHAR_GEAR = "/rpchar/gear";
    public static final String PATH_RPCHAR_PVP = "/rpchar/pvp";
    public static final String PATH_INJURE = "/rpchar/injure";
    public static final String PATH_HEAL_START = "/rpchar/heal-start";
    public static final String PATH_HEAL_STOP = "/rpchar/heal-stop";
    public static final String PATH_GET_BY_IGN = PATH_IGN + "/{ign}";
    public static final String PATH_GET_BY_DISCORD_ID = PATH_DISCORDID + "/{discId}";

    private final PlayerService playerService;
    private final FactionService factionService;

    /**
     * Retrieves a paginated list of Player entities.
     *
     * @param pageable the pagination information.
     * @return a page of {@link PaginatedPlayerResponse} containing the Player entities.
     */
    @GetMapping
    public HttpEntity<Page<PaginatedPlayerResponse>> getPlayersPaginated(Pageable pageable) {
        log.debug("Incoming getPlayersPaginated Request");

        Page<Player> pageDomain = playerService.getPlayersPaginated(pageable);
        var pageResponse = pageDomain.map(PaginatedPlayerResponse::new);

        return ResponseEntity.ok(pageResponse);
    }

    /**
     * Retrieves a player by their Minecraft IGN.
     *
     * @param ign the Minecraft IGN of the player.
     * @return the {@link PlayerRpCharResponse} containing the player and their roleplay characters.
     */
    @Operation(summary = "Get by IGN", description = "Get a player by their minecraft IGN")
    @Parameter(name = "ign", description = "Minecraft IGN of the player", example = "Luktronic")
    @GetMapping(PATH_GET_BY_IGN)
    public HttpEntity<PlayerRpCharResponse> getByIgn(@PathVariable String ign) {
        log.debug("Incoming getByIgn Request. Ign: {}", ign);

        log.debug("Calling PlayerService.getPlayerByIgn, Ign: {}", ign);
        val playerFound = playerService.getPlayerByIgn(ign);
        val response = new PlayerRpCharResponse(playerFound);

        log.info("Successfully fetched player ({}) by ign ({})", playerFound, playerFound.getIgn());
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a player by their Discord ID.
     *
     * @param discId the Discord ID of the player.
     * @return the {@link PlayerRpCharResponse} containing the player and their roleplay characters.
     */
    @Operation(summary = "Get by Discord ID", description = "Get a player by their Discord ID")
    @GetMapping(PATH_GET_BY_DISCORD_ID)
    public HttpEntity<PlayerRpCharResponse> getByDiscordId(@PathVariable String discId) {
        log.info("Incoming getByDiscordId Request. DiscordId: {}", discId);

        log.debug("Calling PlayerService.getPlayerByDiscordId, DiscordId: {}", discId);
        val playerFound = playerService.getPlayerByDiscordId(discId);
        val response = new PlayerRpCharResponse(playerFound);

        log.info("Successfully fetched player ({}) by DiscordId ({})", playerFound, playerFound.getDiscordID());
        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new player in the database.
     *
     * @param createPlayerDto the data transfer object containing the details of the player to create.
     * @return the created {@link PlayerResponse}.
     */
    @Operation(summary = "Creates a player", description = "Create a new player in the database.")
    @PostMapping("")
    public HttpEntity<PlayerResponse> createPlayer(@RequestBody CreatePlayerDto createPlayerDto) {
        log.info("Incoming createPlayer Request. Data [{}]", createPlayerDto);

        log.info("Calling PlayerService.createPlayer. Data {}", createPlayerDto);
        Player createdPlayer = playerService.createPlayer(createPlayerDto);
        var response = new PlayerResponse(createdPlayer);

        URI self = UriComponentsBuilder.fromPath(BASE_URL + PATH_GET_BY_IGN)
                .uriVariables(Map.of("ign", response.ign()))
                .build().toUri();
        log.info("URI built. Data {}, URI {}", response, self);

        log.info("Sending HttpResponse with successfully created Player {}", createdPlayer);
        return ResponseEntity.created(self).body(response);
    }

    /**
     * Creates a new Roleplay Character.
     *
     * @param createRPCharDto the data transfer object containing the details of the roleplay character to create.
     * @return the created {@link RpCharResponse}.
     */
    @Operation(summary = "Create RpChar", description = "Create a Roleplay Character")
    @PostMapping(PATH_RPCHAR)
    public HttpEntity<RpCharResponse> createRpChar(@RequestBody CreateRPCharDto createRPCharDto) {
        log.debug("Incoming createRpChar Request. Data [{}]", createRPCharDto);

        log.debug("Calling PlayerService.createRoleplayCharacter. Data [{}]", createRPCharDto);
        RPChar createdRpChar = playerService.createRoleplayCharacter(createRPCharDto);
        var response = new RpCharResponse(createdRpChar);

        log.info("Sending HttpResponse with successfully created Player {}", createdRpChar);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a player's faction.
     *
     * @param updatePlayerFactionDto the data transfer object containing the details of the player's faction to update.
     * @return the updated {@link PlayerResponse}.
     */
    @Operation(summary = "Update Faction", description = "Update a Player's Faction")
    @PatchMapping(PATH_FACTION)
    public HttpEntity<PlayerResponse> updatePlayerFaction(@RequestBody UpdatePlayerFactionDto updatePlayerFactionDto) {
        log.debug("Incoming updatePlayerFaction Request. Data {}", updatePlayerFactionDto);

        log.trace("Trying to update the player's faction");
        Player player = playerService.updatePlayerFaction(updatePlayerFactionDto);
        log.debug("Successfully updated faction without encountering any errors");
        var response = new PlayerResponse(player);

        log.trace("Building URI for player...");
        URI self = UriComponentsBuilder.fromPath(BASE_URL + PATH_GET_BY_IGN)
                .uriVariables(Map.of("ign", response.ign()))
                .build().toUri();
        log.debug("URI built. Data {}, URI {}", updatePlayerFactionDto, self);

        log.debug("Updating player done!");
        log.info("Sending HttpResponse with successfully updated Player {}", player);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a player's Minecraft IGN.
     *
     * @param dto the data transfer object containing the details of the player's IGN to update.
     * @return the updated {@link PlayerResponse}.
     */
    @Operation(summary = "Update IGN", description = "Update a Player's Minecraft IGN")
    @PatchMapping(PATH_IGN)
    public HttpEntity<PlayerResponse> updatePlayerIgn(@RequestBody UpdatePlayerIgnDto dto) {

        log.debug("Incoming updatePlayerIgn Request: Data [{}]", dto);

        log.trace("Trying to update the player's ingame name");
        Player player = playerService.updateIgn(dto);
        log.debug("Successfully updated faction without encountering any errors");
        var response = new PlayerResponse(player);

        log.info("Sending HttpResponse with successfully updated Player {}", player);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a player's Discord ID.
     *
     * @param dto the data transfer object containing the details of the player's Discord ID to update.
     * @return the updated {@link PlayerUpdateDiscordIdResponse}.
     */
    @Operation(summary = "Update Discord ID", description = "Update a Player's Discord ID")
    @PatchMapping(PATH_DISCORDID)
    public HttpEntity<PlayerUpdateDiscordIdResponse> updatePlayerDiscordId(@RequestBody UpdateDiscordIdDto dto) {
        log.debug("Incoming updateDiscordId Request: Data [{}]", dto);

        log.trace("Trying to update the player's executorDiscordId");
        Player player = playerService.updateDiscordId(dto);
        log.debug("Successfully updated executorDiscordId without encountering any errors");
        var response = new PlayerUpdateDiscordIdResponse(player, dto.oldDiscordId());

        log.info("Sending HttpResponse with successfully updated Player {}", player);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates the name of a Roleplay Character.
     *
     * @param dto the data transfer object containing the details of the roleplay character's name to update.
     * @return the updated {@link RpCharResponse}.
     */
    @Operation(summary = "Update RpChar name", description = "Update the name of a Roleplay Character")
    @PatchMapping(PATH_RPCHAR_NAME)
    public HttpEntity<RpCharResponse> updateCharacterName(@RequestBody UpdateRpCharDto dto) {

        log.debug("Incoming updateCharacterName Request: Data [{}]", dto);

        log.trace("Executing playerService.updateCharacterName");
        RPChar rpChar = playerService.updateCharacterName(dto);
        log.debug("Successfully updated character name without encountering any errors");
        var response = new RpCharResponse(rpChar);

        log.info("Sending HttpResponse with successfully updated RPChar [{}]", rpChar);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates the title of a Roleplay Character.
     *
     * @param dto the data transfer object containing the details of the roleplay character's title to update.
     * @return the updated {@link RpCharResponse}.
     */
    @Operation(summary = "Update RpChar title", description = "Update the title of a Roleplay Character")
    @PatchMapping(PATH_RPCHAR_TITLE)
    public HttpEntity<RpCharResponse> updateCharacterTitle(@RequestBody UpdateRpCharDto dto) {

        log.debug("Incoming updateCharacterTitle Request: Data [{}]", dto);

        log.trace("Executing playerService.updateCharacterTitle");
        RPChar rpChar = playerService.updateCharacterTitle(dto);
        log.debug("Successfully updated character title without encountering any errors");
        var response = new RpCharResponse(rpChar);

        log.info("Sending HttpResponse with successfully updated RPChar [{}]", rpChar);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates the gear of a Roleplay Character.
     *
     * @param dto the data transfer object containing the details of the roleplay character's gear to update.
     * @return the updated {@link RpCharResponse}.
     */
    @Operation(summary = "Update RpChar gear", description = "Update the used gear of a Roleplay Character")
    @PatchMapping(PATH_RPCHAR_GEAR)
    public HttpEntity<RpCharResponse> updateCharacterGear(@RequestBody UpdateRpCharDto dto) {
        log.debug("Incoming updateCharacterGear Request: Data [{}]", dto);

        log.trace("Executing playerService.updateCharacterGear");
        RPChar rpChar = playerService.updateCharacterGear(dto);
        log.debug("Successfully updated character Gear without encountering any errors");
        var response = new RpCharResponse(rpChar);

        log.info("Sending HttpResponse with successfully updated RPChar [{}]", rpChar);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates if a Roleplay Character is participating in PvP.
     *
     * @param dto the data transfer object containing the details of the roleplay character's PvP status to update.
     * @return the updated {@link RpCharResponse}.
     */
    @Operation(summary = "Update RpChar PvP", description = "Update if a Roleplay Character is participating in PvP")
    @PatchMapping(PATH_RPCHAR_PVP)
    public HttpEntity<RpCharResponse> updateCharacterPvP(@RequestBody UpdateRpCharDto dto) {
        log.debug("Incoming updateCharacterPvP Request: Data [{}]", dto);

        log.trace("Executing playerService.updateCharacterPvP");
        RPChar rpChar = playerService.updateCharacterPvp(dto);
        log.debug("Successfully updated character PvP without encountering any errors");
        var response = new RpCharResponse(rpChar);

        log.info("Sending HttpResponse with successfully updated RPChar [{}]", rpChar);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a player.
     *
     * @param dto the data transfer object containing the details of the player to delete.
     * @return the deleted {@link PlayerResponse}.
     */
    @Operation(summary = "Delete Player", description = "Delete a Player")
    @DeleteMapping("")
    public HttpEntity<PlayerResponse> deletePlayer(@RequestBody DiscordIdDto dto) {
        log.debug("Incoming deletePlayer Request: Data [{}]", dto);

        log.trace("Executing playerService.deletePlayer");
        Player player = playerService.deletePlayer(dto);
        var response = new PlayerResponse(player);
        log.debug("Successfully deleted player, [{}]", player);

        log.info("Sending HttpResponse with successfully deleted Player [{}]", player);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a Roleplay Character.
     *
     * @param dto the data transfer object containing the details of the roleplay character to delete.
     * @return the deleted {@link RpCharResponse}.
     */
    @Operation(summary = "Delete RpChar", description = "Delete a Roleplay Character")
    @DeleteMapping(PATH_RPCHAR)
    public HttpEntity<RpCharResponse> deleteRpChar(@RequestBody DiscordIdDto dto) {
        log.debug("Incoming deleteRpChar Request: Data [{}]", dto);

        log.trace("Executing playerService.deleteRpChar");
        RPChar rpChar = playerService.deleteRpChar(dto);
        log.debug("Successfully deleted rpchar, [{}]", rpChar);
        var response = new RpCharResponse(rpChar);

        log.info("Sending HttpResponse with successfully deleted RpChar [{}]", rpChar);
        return ResponseEntity.ok(response);
    }

    /**
     * Injures a roleplay character.
     *
     * @param dto the data transfer object containing the details of the roleplay character to injure.
     * @return the injured {@link RpCharResponse}.
     */
    @Operation(summary = "Injure RpChar", description = "Injure a roleplay character")
    @PatchMapping(PATH_INJURE)
    public HttpEntity<RpCharResponse> injureChar(@RequestBody DiscordIdDto dto) {
        log.debug("Incoming injureChar Request: Data [{}]", dto);

        log.trace("Executing playerService.injureChar");
        RPChar rpChar = playerService.injureChar(dto);
        log.debug("Successfully injured character without encountering any errors");
        var response = new RpCharResponse(rpChar);

        log.info("Sending HttpResponse with successfully injured RPChar [{}]", rpChar);
        return ResponseEntity.ok(response);
    }

    /**
     * Starts healing a Roleplay Character.
     *
     * @param dto the data transfer object containing the details of the roleplay character to start healing.
     * @return the {@link RpCharResponse} containing the roleplay character.
     */
    @Operation(summary = "Heal start", description = "Start healing a Roleplay Character")
    @PatchMapping(PATH_HEAL_START)
    public HttpEntity<RpCharResponse> healStart(@RequestBody DiscordIdDto dto) {
        log.debug("Incoming healStart Request: Data [{}]", dto);

        log.trace("Executing playerService.healStart");
        RPChar rpChar = playerService.healStart(dto);
        log.debug("Successfully started healing of character without encountering any errors");
        var response = new RpCharResponse(rpChar);

        log.info("Sending HttpResponse with successful start of healing of RPChar [{}]", rpChar);
        return ResponseEntity.ok(response);
    }

    /**
     * Cancels healing of a Roleplay Character.
     *
     * @param dto the data transfer object containing the details of the roleplay character to stop healing.
     * @return the {@link RpCharResponse} containing the roleplay character.
     */
    @Operation(summary = "Heal stop", description = "Cancel healing of a Roleplay Character")
    @PatchMapping(PATH_HEAL_STOP)
    public HttpEntity<RpCharResponse> healStop(@RequestBody DiscordIdDto dto) {
        log.debug("Incoming healStop Request: Data [{}]", dto);

        log.trace("Executing playerService.healStop");
        RPChar rpChar = playerService.healStop(dto);
        var response = new RpCharResponse(rpChar);
        log.debug("Successfully started healing of character without encountering any errors");

        log.info("Sending HttpResponse with successful stop of healing of RPChar [{}]", rpChar);
        return ResponseEntity.ok(response);
    }
}