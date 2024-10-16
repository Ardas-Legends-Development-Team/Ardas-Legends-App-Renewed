package com.ardaslegends.presentation.api;

import com.ardaslegends.domain.war.War;
import com.ardaslegends.presentation.AbstractRestController;
import com.ardaslegends.presentation.api.response.war.ActiveWarResponse;
import com.ardaslegends.presentation.api.response.war.WarResponse;
import com.ardaslegends.service.dto.war.CreateWarDto;
import com.ardaslegends.service.dto.war.EndWarDto;
import com.ardaslegends.service.war.WarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing War entities.
 * <p>
 * This controller provides endpoints for creating, retrieving, and ending War entities.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@Tag(name = "War Controller", description = "REST Endpoints concerning Wars")
@RequestMapping(WarRestController.BASE_URL)
public class WarRestController extends AbstractRestController {
    public static final String BASE_URL = "/api/wars";
    public static final String CREATE_WAR = "/declare";
    public static final String END = "/end"; // Will be used later on when faction leaders can end war
    public static final String FORCE_END = END + "/force"; // Staff only

    private final WarService warService;

    /**
     * Retrieves a paginated list of War entities.
     *
     * @param pageable the pagination information.
     * @return a page of {@link WarResponse} containing the War entities.
     */
    @Operation(summary = "Get Wars Paginated", description = "Retrieves a Page with a set of elements, parameters define the size, which Page you want and how its sorted")
    @GetMapping
    public ResponseEntity<Page<WarResponse>> getWarsPaginated(Pageable pageable) {
        log.debug("Incoming getWarsPaginated Request, paginated data [{}]", pageable);

        Page<War> page = warService.getWars(pageable);
        log.debug(page.toString());

        Page<WarResponse> response = page.map(WarResponse::new);

        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new War entity.
     *
     * @param dto the data transfer object containing the details of the war to create.
     * @return the created {@link ActiveWarResponse}.
     */
    @Operation(summary = "Declare War", description = "Creates a new War entity.")
    @PostMapping(CREATE_WAR)
    public ResponseEntity<ActiveWarResponse> createWar(@RequestBody CreateWarDto dto) {
        log.debug("Incoming declareWar Request: Data [{}]", dto);

        War createWarResult = warService.createWar(dto);
        ActiveWarResponse response = new ActiveWarResponse(createWarResult);

        log.debug("Result from service is [{}]", response);

        log.info("Sending successful createWar Request for [{}]", dto.nameOfWar());
        return ResponseEntity.ok(response);
    }

    /**
     * Forces the end of a War entity.
     *
     * @param warName           the name of the war to end.
     * @param executorDiscordId the Discord ID of the executor.
     * @return the ended {@link ActiveWarResponse}.
     */
    @Operation(summary = "Force End War", description = "Forces the end of a War entity. Staff only.")
    @DeleteMapping(FORCE_END)
    public ResponseEntity<ActiveWarResponse> forceEndWar(String warName, String executorDiscordId) {
        val dto = new EndWarDto(warName, executorDiscordId);
        log.debug("Incoming force end war Request: Data [{}]", dto);

        War createWarResult = warService.forceEndWar(dto);
        ActiveWarResponse response = new ActiveWarResponse(createWarResult);

        log.debug("Result from service is [{}]", response);

        log.info("Sending successful createWar Request for [{}]", dto.warName());
        return ResponseEntity.ok(response);
    }
}