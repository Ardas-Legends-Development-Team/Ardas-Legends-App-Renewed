package com.ardaslegends.presentation.api.response.movement;

import com.ardaslegends.domain.Movement;

import java.util.List;

public record CurrentAndPastMovementResponse(
        MovementResponse currentMovement,
        List<MovementResponse> pastMovements
) {
    public CurrentAndPastMovementResponse(Movement currentMovement, List<Movement> pastMovements) {
        this(
                currentMovement == null ? null : new MovementResponse(currentMovement),
                pastMovements.stream().map(MovementResponse::new).toList()
        );
    }
}
