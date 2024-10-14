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

@RequiredArgsConstructor

@Slf4j
@RestController
@Tag(name = "RPChar Controller", description = "All REST endpoints regarding Roleplay Characters")
@RequestMapping(RpCharRestController.BASE_URL)
public class RpCharRestController extends AbstractRestController {

    public final static String BASE_URL = "/api/rpchars";
    public final static String NAME = "/name";
    public final static String PATH_STATION = "/station";
    public final static String PATH_UNSTATION = "/unstation";

    private final RpCharService rpCharService;

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

    @Operation(summary = "Get RpChars By Names", description = "Returns an array of RpChars with the specified names")
    @GetMapping(NAME)
    public ResponseEntity<RpCharOwnerResponse[]> getRpCharsByNames(@RequestParam(name = "name") String[] names) {
        log.debug("Incoming getRpCharsByNames Request, parameter names: [{}]", (Object) names);

        List<RPChar> rpchars = rpCharService.getRpCharsByNames(names);

        log.debug("Building RpCharOwnerResponse with rpchars [{}]", rpchars);
        RpCharOwnerResponse[] response = rpchars.stream().map(RpCharOwnerResponse::new).toArray(RpCharOwnerResponse[]::new);

        return ResponseEntity.ok(response);
    }

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
