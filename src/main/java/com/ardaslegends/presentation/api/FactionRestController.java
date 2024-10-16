package com.ardaslegends.presentation.api;

import com.ardaslegends.domain.Faction;
import com.ardaslegends.presentation.AbstractRestController;
import com.ardaslegends.presentation.api.response.faction.PaginatedFactionResponse;
import com.ardaslegends.service.FactionService;
import com.ardaslegends.service.dto.UpdateFactionLeaderDto;
import com.ardaslegends.service.dto.faction.UpdateFactionLeaderResponseDto;
import com.ardaslegends.service.dto.faction.UpdateStockpileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Faction entities.
 * <p>
 * This controller provides endpoints for creating, retrieving, updating, and deleting Faction entities.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(FactionRestController.BASE_URL)
public class FactionRestController extends AbstractRestController {
    public static final String BASE_URL = "/api/faction";
    private static final String PATH_UPDATE_FACTION_LEADER = "/update/faction-leader";
    private static final String PATH_UPDATE_STOCKPILE_ADD = "/update/stockpile/add";
    private static final String PATH_UPDATE_STOCKPILE_REMOVE = "/update/stockpile/remove";
    private static final String PATH_GET_STOCKPILE_INFO = "/get/stockpile/info/{faction}";

    private final FactionService factionService;

    /**
     * Helper method to build an {@link UpdateStockpileDto} from a {@link Faction} entity.
     *
     * @param result the Faction entity.
     * @return the built {@link UpdateStockpileDto}.
     */
    private static UpdateStockpileDto getUpdateStockpileDto(Faction result) {
        log.trace("Result faction [{}] and stockpile [{}]", result.getName(), result.getFoodStockpile());
        log.trace("Building response body");

        UpdateStockpileDto body = new UpdateStockpileDto(result.getName(), result.getFoodStockpile());

        log.trace("Body [{}]", body);
        return body;
    }

    /**
     * Retrieves a paginated list of Faction entities.
     *
     * @param pageable the pagination information.
     * @return a page of {@link PaginatedFactionResponse} containing the Faction entities.
     */
    @GetMapping
    public ResponseEntity<Page<PaginatedFactionResponse>> getFactionsPaginated(Pageable pageable) {
        log.debug("Incoming getFactionsPaginated request, paginated data [{}]", pageable);

        Page<Faction> pageDomain = factionService.getFactionsPaginated(pageable);
        var pageResponse = pageDomain.map(PaginatedFactionResponse::new);

        return ResponseEntity.ok(pageResponse);
    }

    /**
     * Updates the leader of a Faction entity.
     *
     * @param dto the data transfer object containing the details of the new faction leader.
     * @return the updated {@link UpdateFactionLeaderResponseDto}.
     */
    @PatchMapping(PATH_UPDATE_FACTION_LEADER)
    public ResponseEntity<UpdateFactionLeaderResponseDto> setFactionLeader(@RequestBody UpdateFactionLeaderDto dto) {
        log.debug("Incoming update faction-leader request with data [{}]", dto);

        log.trace("Calling wrappedServiceExecution setFactionLeader");
        var result = factionService.setFactionLeader(dto);

        log.trace("Result: [faction:{}] and new leader [discId:{}]. Dto discId [{}]", result.getName(), result.getLeader().getDiscordID(), dto.targetDiscordId());
        log.trace("Building response body");

        UpdateFactionLeaderResponseDto body = new UpdateFactionLeaderResponseDto(result.getName(), result.getLeader().getIgn());

        log.trace("Body [{}]", body);
        log.info("Sending successful update faction-leader request to bot! Body:[{}]", body);
        return ResponseEntity.ok(body);
    }

    /**
     * Adds to the stockpile of a Faction entity.
     *
     * @param dto the data transfer object containing the details of the stockpile update.
     * @return the updated {@link UpdateStockpileDto}.
     */
    @PatchMapping(PATH_UPDATE_STOCKPILE_ADD)
    public ResponseEntity<UpdateStockpileDto> addStockpile(@RequestBody UpdateStockpileDto dto) {
        log.debug("Incoming add to stockpile request with data [{}]", dto);

        log.trace("Calling wrappedServiceExecution addToStockpile");
        var result = factionService.addToStockpile(dto);

        UpdateStockpileDto body = getUpdateStockpileDto(result);
        log.info("Sending successful update add to stockpile request to bot! Body:[{}]", body);
        return ResponseEntity.ok(body);
    }

    /**
     * Removes from the stockpile of a Faction entity.
     *
     * @param dto the data transfer object containing the details of the stockpile update.
     * @return the updated {@link UpdateStockpileDto}.
     */
    @PatchMapping(PATH_UPDATE_STOCKPILE_REMOVE)
    public ResponseEntity<UpdateStockpileDto> removeFromStockpile(@RequestBody UpdateStockpileDto dto) {
        log.debug("Incoming remove from stockpile request with data [{}]", dto);

        log.trace("Calling wrappedServiceExecution removeFromStockpile");
        var result = factionService.removeFromStockpile(dto);

        UpdateStockpileDto body = getUpdateStockpileDto(result);
        log.info("Sending successful update remove from stockpile request to bot! Body:[{}]", body);
        return ResponseEntity.ok(body);
    }

    /**
     * Retrieves the stockpile information for a specific faction.
     *
     * @param faction the name of the faction.
     * @return the {@link UpdateStockpileDto} containing the stockpile information for the specified faction.
     */
    @GetMapping(PATH_GET_STOCKPILE_INFO)
    public ResponseEntity<UpdateStockpileDto> getStockpileInfo(@PathVariable("faction") String faction) {
        log.debug("Incoming getStockpile info request with data [{}]", faction);

        log.trace("Calling wrappedServiceExecution getFactionByName");
        var result = factionService.getFactionByName(faction);

        UpdateStockpileDto body = getUpdateStockpileDto(result);
        log.info("Sending successful get stockpile info request to bot! Body:[{}]", body);
        return ResponseEntity.ok(body);
    }
}