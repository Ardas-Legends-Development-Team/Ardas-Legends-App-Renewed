package com.ardaslegends.presentation.api;

import com.ardaslegends.domain.Movement;
import com.ardaslegends.presentation.AbstractRestController;
import com.ardaslegends.presentation.api.response.movement.CurrentAndPastMovementResponse;
import com.ardaslegends.presentation.api.response.movement.MovementResponse;
import com.ardaslegends.service.MovementService;
import com.ardaslegends.service.dto.army.MoveArmyDto;
import com.ardaslegends.service.dto.player.DiscordIdDto;
import com.ardaslegends.service.dto.player.rpchar.MoveRpCharDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Movement entities.
 * <p>
 * This controller provides endpoints for creating, retrieving, updating, and deleting Movement entities.
 * </p>
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(MovementRestController.BASE_URL)
public class MovementRestController extends AbstractRestController {

    public static final String BASE_URL = "/api/movement";
    public static final String PATH_GET_ARMY_MOVEMENTS = "/army";
    public static final String PATH_GET_CHAR_MOVEMENTS = "/char";
    public static final String PATH_MOVE_CHAR = "/move-char";
    public static final String PATH_MOVE_ARMY = "/move-army-or-company";
    public static final String PATH_CANCEL_CHAR_MOVEMENT = "/cancel-char-move";
    public static final String PATH_CANCEL_ARMY_MOVEMENT = "/cancel-army-move";
    public static final String PATH_CALCULATE_ARMY_MOVEMENT = "/calculate/army";
    public static final String PATH_CALCULATE_CHAR_MOVEMENT = "/calculate/char";
    private final MovementService movementService;

    /**
     * Retrieves the current and past movements of an army.
     *
     * @param name the name of the army.
     * @return the {@link CurrentAndPastMovementResponse} containing the current and past movements of the army.
     */
    @GetMapping(PATH_GET_ARMY_MOVEMENTS)
    public HttpEntity<CurrentAndPastMovementResponse> getArmyMovements(String name) {
        log.debug("Incoming get request for previous army movements [{}]", name);

        val movements = movementService.getArmyMovements(name);

        log.debug("Building response");
        val response = new CurrentAndPastMovementResponse(movements.getFirst().orElse(null), movements.getSecond());

        log.info("Successfully handled request, getArmyMovements");
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves the current and past movements of a roleplay character.
     *
     * @param name the name of the roleplay character.
     * @return the {@link CurrentAndPastMovementResponse} containing the current and past movements of the roleplay character.
     */
    @GetMapping(PATH_GET_CHAR_MOVEMENTS)
    public HttpEntity<CurrentAndPastMovementResponse> getCharMovements(String name) {
        log.debug("Incoming get request for previous rpChar movements [{}]", name);

        val movements = movementService.getCharMovements(name);

        log.debug("Building response");
        val response = new CurrentAndPastMovementResponse(movements.getFirst().orElse(null), movements.getSecond());

        log.info("Successfully handled request, getCharMovements");
        return ResponseEntity.ok(response);
    }

    /**
     * Calculates the movement of an army.
     *
     * @param executorDiscordId the Discord ID of the executor.
     * @param armyName          the name of the army.
     * @param toRegion          the destination region.
     * @return the {@link MovementResponse} containing the calculated movement details.
     */
    @GetMapping(PATH_CALCULATE_ARMY_MOVEMENT)
    public HttpEntity<MovementResponse> calculateArmyMove(String executorDiscordId, String armyName, String toRegion) {
        val dto = new MoveArmyDto(executorDiscordId, armyName, toRegion);
        log.debug("Incoming get request for army movement calculation [{}]", dto);

        log.trace("WrappedServiceExecution of calculateArmyMovement function");
        val movement = movementService.calculateArmyMovement(dto);
        log.debug("Creating MovementResponse");
        MovementResponse response = new MovementResponse(movement);

        log.info("Successfully handled request - calculated army movement!");
        return ResponseEntity.ok(response);
    }

    /**
     * Calculates the movement of a roleplay character.
     *
     * @param discordId the Discord ID of the character.
     * @param toRegion  the destination region.
     * @return the {@link MovementResponse} containing the calculated movement details.
     */
    @GetMapping(PATH_CALCULATE_CHAR_MOVEMENT)
    public HttpEntity<MovementResponse> calculateCharMove(String discordId, String toRegion) {
        val dto = new MoveRpCharDto(discordId, toRegion);
        log.debug("Incoming get request for char movement calculation [{}]", dto);

        log.trace("WrappedServiceExecution of calculateRpCharMovement function");
        val movement = movementService.calculateRpCharMovement(dto);
        log.debug("Creating MovementResponse");
        MovementResponse response = new MovementResponse(movement);

        log.info("Successfully handled request - calculated char movement!");
        return ResponseEntity.ok(response);
    }

    /**
     * Moves a roleplay character.
     *
     * @param dto the data transfer object containing the details of the character movement.
     * @return the {@link MovementResponse} containing the movement details.
     */
    @PostMapping(PATH_MOVE_CHAR)
    public HttpEntity<MovementResponse> moveRoleplayCharacter(@RequestBody MoveRpCharDto dto) {
        log.debug("Incoming Post Request to create rp char movement, data [{}]", dto);
        log.trace("WrappedServiceExecution of createRpCharMovement function");
        Movement movement = movementService.createRpCharMovement(dto);
        log.debug("Creating MovementResponse");
        MovementResponse response = new MovementResponse(movement);

        log.info("Successfully handled request for creating rpchar movement!");
        return ResponseEntity.ok(response);
    }

    /**
     * Cancels the movement of a roleplay character.
     *
     * @param dto the data transfer object containing the Discord ID of the character.
     * @return the {@link MovementResponse} containing the movement details.
     */
    @PatchMapping(PATH_CANCEL_CHAR_MOVEMENT)
    public HttpEntity<MovementResponse> cancelRoleplayCharacterMove(@RequestBody DiscordIdDto dto) {
        log.debug("Incoming Post Request to cancel rp char movement, data [{}]", dto);
        log.trace("WrappedServiceExecution of cancelRpCharMovement function");
        Movement movement = movementService.cancelRpCharMovement(dto);
        log.debug("Creating MovementResponse");
        MovementResponse response = new MovementResponse(movement);

        log.info("Successfully handled request for cancelling rpchar movement!");
        return ResponseEntity.ok(response);
    }

    /**
     * Moves an army.
     *
     * @param dto the data transfer object containing the details of the army movement.
     * @return the {@link MovementResponse} containing the movement details.
     */
    @PostMapping(PATH_MOVE_ARMY)
    public HttpEntity<MovementResponse> moveArmy(@RequestBody MoveArmyDto dto) {
        log.debug("Incoming Post Request to create army movement, data [{}]", dto);

        log.trace("WrappedServiceExecution of createArmyMovement function");
        Movement movement = movementService.createArmyMovement(dto);
        log.debug("Creating MovementResponse");
        MovementResponse response = new MovementResponse(movement);

        log.info("Successfully handled request for creating army movement!");
        return ResponseEntity.ok(response);
    }

    /**
     * Cancels the movement of an army.
     *
     * @param dto the data transfer object containing the details of the army movement.
     * @return the {@link MovementResponse} containing the movement details.
     */
    @PatchMapping(PATH_CANCEL_ARMY_MOVEMENT)
    public HttpEntity<MovementResponse> cancelArmyMove(@RequestBody MoveArmyDto dto) {
        log.debug("Incoming Post Request to cancel army movement, data [{}]", dto);
        log.trace("WrappedServiceExecution of cancelArmyMovement function");
        Movement movement = movementService.cancelArmyMovement(dto);
        log.debug("Creating MovementResponse");
        MovementResponse response = new MovementResponse(movement);

        log.info("Successfully handled request for cancelling army movement!");
        return ResponseEntity.ok(response);
    }
}