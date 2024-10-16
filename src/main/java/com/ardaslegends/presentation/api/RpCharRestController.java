package com.ardaslegends.presentation.api;

import com.ardaslegends.domain.RPChar;
import com.ardaslegends.presentation.AbstractRestController;
import com.ardaslegends.presentation.api.response.player.rpchar.RpCharOwnerResponse;
import com.ardaslegends.presentation.api.response.player.rpchar.RpCharResponse;
import com.ardaslegends.service.RpCharService;
import com.ardaslegends.service.dto.player.rpchar.StationRpCharDto;
import com.ardaslegends.service.dto.player.rpchar.UnstationRpCharDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Roleplay Character (RPChar) entities.
 * <p>
 * This controller provides endpoints for retrieving, updating, and managing RPChar entities.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@Tag(name = "RPChar Controller", description = "All REST endpoints regarding Roleplay Characters")
@RequestMapping(RpCharRestController.BASE_URL)
public class RpCharRestController extends AbstractRestController {

    public static final String BASE_URL = "/api/rpchars";
    public static final String NAME = "/name";
    public static final String PATH_STATION = "/station";
    public static final String PATH_UNSTATION = "/unstation";

    private final RpCharService rpCharService;

    /**
     * Retrieves a paginated list of RPChar entities.
     *
     * @param pageable the pagination information.
     * @return a slice of {@link RpCharOwnerResponse} containing the RPChar entities.
     */
    @Operation(summary = "Get RpChars Paginated", description = "Returns a Page of RpChars")
    @GetMapping
    public ResponseEntity<Slice<RpCharOwnerResponse>> getAll(Pageable pageable) {
        log.info("Incoming get all rpchars Request");

        log.debug("Calling rpCharService.getAll...");
        Slice<RPChar> rpChars = rpCharService.getAll(pageable);
        log.debug("Received slice of RpChars from service");

        log.debug("Building RpChar response");
        Slice<RpCharOwnerResponse> rpCharOwnerResponses = rpChars.map(RpCharOwnerResponse::new);
        log.debug("Built response [{}]", rpCharOwnerResponses);

        log.info("Successfully handled get all RpChars request - returning data [{}]", rpCharOwnerResponses);
        return ResponseEntity.ok(rpCharOwnerResponses);
    }

    /**
     * Retrieves RPChar entities by their names.
     *
     * @param names the names of the RPChar entities.
     * @return an array of {@link RpCharOwnerResponse} containing the RPChar entities.
     */
    @Operation(summary = "Get RpChars By Names", description = "Returns an array of RpChars with the specified names")
    @GetMapping(NAME)
    public ResponseEntity<RpCharOwnerResponse[]> getRpCharsByNames(@RequestParam(name = "name") String[] names) {
        log.debug("Incoming getRpCharsByNames Request, parameter names: [{}]", (Object) names);

        List<RPChar> rpchars = rpCharService.getRpCharsByNames(names);

        log.debug("Building RpCharOwnerResponse with rpchars [{}]", rpchars);
        RpCharOwnerResponse[] response = rpchars.stream().map(RpCharOwnerResponse::new).toArray(RpCharOwnerResponse[]::new);

        return ResponseEntity.ok(response);
    }

    /**
     * Stations a RPChar entity.
     *
     * @param dto the data transfer object containing the details of the RPChar to station.
     * @return the updated {@link RpCharResponse}.
     */
    @PatchMapping(PATH_STATION)
    public HttpEntity<RpCharResponse> station(@RequestBody StationRpCharDto dto) {
        log.debug("Incoming station request: Data [{}]", dto);

        log.debug("Calling station()");
        RPChar modifiedCharacter = rpCharService.station(dto);
        log.debug("Converting to RpCharResponse");
        RpCharResponse response = new RpCharResponse(modifiedCharacter);

        log.info("Sending successful station request for [{}]", modifiedCharacter);
        return ResponseEntity.ok(response);
    }

    /**
     * Unstations a RPChar entity.
     *
     * @param dto the data transfer object containing the details of the RPChar to unstation.
     * @return the updated {@link RpCharResponse}.
     */
    @PatchMapping(PATH_UNSTATION)
    public HttpEntity<RpCharResponse> unstation(@RequestBody UnstationRpCharDto dto) {
        log.debug("Incoming station request: Data [{}]", dto);

        log.debug("Calling unstation()");
        RPChar modifiedCharacter = rpCharService.unstation(dto);
        log.debug("Converting to RpCharResponse");
        RpCharResponse response = new RpCharResponse(modifiedCharacter);

        log.info("Sending successful unstation request for [{}]", modifiedCharacter);
        return ResponseEntity.ok(response);
    }
}