package com.ardaslegends.presentation.api;

import com.ardaslegends.domain.Army;
import com.ardaslegends.domain.claimbuilds.ClaimBuild;
import com.ardaslegends.domain.claimbuilds.ClaimBuildType;
import com.ardaslegends.domain.claimbuilds.SpecialBuilding;
import com.ardaslegends.presentation.AbstractRestController;
import com.ardaslegends.presentation.api.response.claimbuild.ClaimbuildResponse;
import com.ardaslegends.service.ClaimBuildService;
import com.ardaslegends.service.dto.claimbuild.CreateClaimBuildDto;
import com.ardaslegends.service.dto.claimbuilds.DeleteClaimbuildDto;
import com.ardaslegends.service.dto.claimbuilds.UpdateClaimbuildOwnerDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing Claimbuild entities.
 * <p>
 * This controller provides endpoints for creating, retrieving, updating, and deleting Claimbuild entities.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(ClaimbuildRestController.BASE_URL)
public class ClaimbuildRestController extends AbstractRestController {
    public static final String BASE_URL = "/api/claimbuild";
    public static final String NAME = "/name";
    public static final String FACTION = "/faction";
    public static final String GET_TYPES = "/types";
    public static final String GET_SPECIAL_BUILDINGS = "/specialbuildings";
    public static final String PATH_CREATE_CLAIMBUILD = "/create";
    public static final String PATH_UPDATE_CLAIMBUILD = "/update";
    private static final String UPDATE_CLAIMBUILD_FATION = "/update/claimbuild-faction";
    private static final String DELETE_CLAIMBUILD = "/delete";

    private final ClaimBuildService claimBuildService;

    /**
     * Retrieves all Claimbuild types.
     *
     * @return an array of Claimbuild types.
     */
    @GetMapping(GET_TYPES)
    public ResponseEntity<String[]> getTypes() {
        log.debug("Incoming getClaimbuildTypes Request");

        val claimbuildTypesStringArray = Arrays.stream(ClaimBuildType.values())
                .map(ClaimBuildType::getName)
                .toArray(String[]::new);

        return ResponseEntity.ok(claimbuildTypesStringArray);
    }

    /**
     * Retrieves a paginated list of Claimbuild entities.
     *
     * @param pageable the pagination information.
     * @return a page of {@link ClaimbuildResponse} containing the Claimbuild entities.
     */
    @Operation(summary = "Get Claimbuilds Paginated", description = "Retrieves a Page with a set of elements, parameters define the size, which Page you want and how its sorted")
    @GetMapping
    public ResponseEntity<Page<ClaimbuildResponse>> getClaimbuildsPaginated(Pageable pageable) {
        log.debug("Incoming getClaimbuildsPaginated Request, paginated data [{}]", pageable);

        Page<ClaimBuild> pageDomain = claimBuildService.getClaimbuildsPaginated(pageable);
        var pageResponse = pageDomain.map(ClaimbuildResponse::new);

        return ResponseEntity.ok(pageResponse);
    }

    /**
     * Retrieves Claimbuilds by their names.
     *
     * @param names the names of the Claimbuilds to retrieve.
     * @return an array of {@link ClaimbuildResponse} containing the Claimbuilds with the specified names.
     */
    @Operation(summary = "Get Claimbuilds By Name", description = "Returns an array of claimbuilds with the specified names")
    @GetMapping(NAME)
    public ResponseEntity<ClaimbuildResponse[]> getClaimbuildsByNames(@RequestParam(name = "name") String[] names) {
        log.debug("Incoming getClaimbuildsByName Request, parameter names: [{}]", (Object) names);

        List<ClaimBuild> claimBuilds = claimBuildService.getClaimBuildsByNames(names);

        log.debug("Building ClaimbuildResponses with claimbuilds [{}]", claimBuilds);
        ClaimbuildResponse[] response = claimBuilds.stream().map(ClaimbuildResponse::new).toArray(ClaimbuildResponse[]::new);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves Claimbuilds by their faction.
     *
     * @param faction the faction of the Claimbuilds to retrieve.
     * @return an array of {@link ClaimbuildResponse} containing the Claimbuilds of the specified faction.
     */
    @Operation(summary = "Get Claimbuilds By Faction", description = "Returns an array of claimbuilds of the specified faction")
    @GetMapping(FACTION)
    public ResponseEntity<ClaimbuildResponse[]> getClaimbuildsByFaction(@RequestParam String faction) {
        log.debug("Incoming getClaimbuildsByFaction Request, parameter faction: [{}]", faction);

        List<ClaimBuild> claimBuilds = claimBuildService.getClaimBuildsByFaction(faction);

        log.debug("Building ClaimbuildResponses with claimbuilds [{}]", claimBuilds);
        ClaimbuildResponse[] response = claimBuilds.stream().map(ClaimbuildResponse::new).toArray(ClaimbuildResponse[]::new);

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all special buildings.
     *
     * @return an array of special buildings.
     */
    @GetMapping(GET_SPECIAL_BUILDINGS)
    public HttpEntity<String[]> getSpecialBuildings() {
        log.debug("Incoming getAllSpecialBuilds request");

        val specialBuildingsStringArray = Arrays.stream(SpecialBuilding.values())
                .map(SpecialBuilding::getName)
                .toArray(String[]::new);

        return ResponseEntity.ok(specialBuildingsStringArray);
    }

    /**
     * Creates a new Claimbuild entity.
     *
     * @param dto the data transfer object containing the details of the Claimbuild to create.
     * @return the created {@link ClaimBuild}.
     */
    @PostMapping(PATH_CREATE_CLAIMBUILD)
    public HttpEntity<ClaimBuild> createClaimbuild(@RequestBody CreateClaimBuildDto dto) {
        log.debug("Incoming createClaimbuild Request: Data [{}]", dto);

        log.debug("Calling claimBuildService.createClaimbuild");
        ClaimBuild claimBuild = claimBuildService.createClaimbuild(dto, true);

        log.info("Sending successful createClaimbuild Request for [{}]", claimBuild.getName());
        return ResponseEntity.ok(claimBuild);
    }

    /**
     * Updates an existing Claimbuild entity.
     *
     * @param dto the data transfer object containing the details of the Claimbuild to update.
     * @return the updated {@link ClaimBuild}.
     */
    @PatchMapping(PATH_UPDATE_CLAIMBUILD)
    public HttpEntity<ClaimBuild> updateClaimbuild(@RequestBody CreateClaimBuildDto dto) {
        log.debug("Incoming updateClaimbuild Request: Data [{}]", dto);

        log.debug("Calling claimBuildService.createClaimbuild");
        ClaimBuild claimBuild = claimBuildService.createClaimbuild(dto, false);

        log.info("Sending successful updateClaimbuild Request for [{}]", claimBuild.getName());
        return ResponseEntity.ok(claimBuild);
    }

    /**
     * Updates the owner of a Claimbuild entity.
     *
     * @param dto the data transfer object containing the details of the Claimbuild owner to update.
     * @return the updated {@link UpdateClaimbuildOwnerDto}.
     */
    @PatchMapping(UPDATE_CLAIMBUILD_FATION)
    public HttpEntity<UpdateClaimbuildOwnerDto> updateClaimbuildOwner(@RequestBody UpdateClaimbuildOwnerDto dto) {
        log.debug("Incoming update Claimbuild Owner Request with data [{}]", dto);

        log.trace("Calling wrappedServiceExecution of setOwnerFaction");
        var result = claimBuildService.changeOwnerFromDto(dto);

        log.trace("Building response Dto");
        UpdateClaimbuildOwnerDto response = new UpdateClaimbuildOwnerDto(result.getName(), result.getOwnedBy().getName());

        log.info("Sending successful response [{}] to bot!", response);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a Claimbuild entity.
     *
     * @param dto the data transfer object containing the details of the Claimbuild to delete.
     * @return the deleted {@link DeleteClaimbuildDto}.
     */
    @DeleteMapping(DELETE_CLAIMBUILD)
    public HttpEntity<DeleteClaimbuildDto> deleteClaimbuild(@RequestBody DeleteClaimbuildDto dto) {
        log.debug("Incoming delete Claimbuild Request with data [{}]", dto);

        log.trace("Calling wrappedServiceExecution of deleteClaimbuild");
        var result = claimBuildService.deleteClaimbuild(dto);

        log.trace("Building body Dto");
        DeleteClaimbuildDto body = new DeleteClaimbuildDto(result.getName(),
                result.getStationedArmies().stream().map(Army::getName).collect(Collectors.toList()),
                result.getCreatedArmies().stream().map(Army::getName).collect(Collectors.toList()));

        log.info("Creating response with body [{}]", body);
        return ResponseEntity.ok(body);
    }
}