package com.ardaslegends.presentation.api;

import com.ardaslegends.domain.UnitType;
import com.ardaslegends.presentation.AbstractRestController;
import com.ardaslegends.presentation.api.response.unit.UnitTypeResponse;
import com.ardaslegends.service.UnitTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for managing UnitType entities.
 * <p>
 * This controller provides endpoints for retrieving UnitType entities.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@Tag(name = "UnitType Controller", description = "All REST endpoints regarding unit types")
@RequestMapping(UnitTypeRestController.BASE_URL)
public class UnitTypeRestController extends AbstractRestController {

    public final static String BASE_URL = "/api/unittypes";

    private final UnitTypeService unitTypeService;

    /**
     * Retrieves an array of unit types of the specified factions. Returns all unit types if no faction is specified.
     *
     * @param factions the list of faction names to filter the unit types by. Can pass multiple values to get units from multiple factions.
     * @return an array of {@link UnitTypeResponse} containing the unit types.
     */
    @Operation(summary = "Get UnitTypes",
            description = "Returns an array of unit types of the passed faction. Returns all unit types if no faction is specified",
            parameters = {@Parameter(name = "faction",
                    description = "The faction to get the units from. Can pass multiple values to get units from multiple factions",
                    example = "faction=Gondor&faction=Mordor")})
    @GetMapping
    public ResponseEntity<UnitTypeResponse[]> getUnitTypes(@RequestParam(name = "faction", required = false) List<String> factions) {
        log.debug("Incoming getUnitTypes request with factions: [{}]", StringUtils.join(factions, ", "));

        val unitTypes = new ArrayList<UnitType>();

        if (factions != null) {
            log.debug("Searching by faction names");
            log.debug("Calling unitTypeService.getByFactions");
            unitTypes.addAll(unitTypeService.getByFactionNames(factions));
        } else {
            log.debug("No factions were specified - searching all unit types");
            unitTypes.addAll(unitTypeService.getAll());
        }

        log.debug("Building response");
        UnitTypeResponse[] response = unitTypes.stream().map(UnitTypeResponse::new).toArray(UnitTypeResponse[]::new);

        log.info("Successfully handled getUnitTypes request - sending response [{}]", StringUtils.join(response, ", "));
        return ResponseEntity.ok(response);
    }
}