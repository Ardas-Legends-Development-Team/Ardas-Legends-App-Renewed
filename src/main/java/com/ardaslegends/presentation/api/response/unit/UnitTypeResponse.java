package com.ardaslegends.presentation.api.response.unit;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UnitType", description = "UnitType response")
public record UnitTypeResponse(
        @Schema(name = "name", description = "Unit name", example = "Gondor Archer")
        String name,
        @Schema(name = "tokenCost", description = "The unit's token cost", example = "1.5")
        Double tokenCost,
        @Schema(name = "isMounted", description = "Whether the unit is mounted or not", example = "false")
        boolean isMounted,
        @Schema(name = "usableBy", description = "Array of factions that can use this unit",
                example = "[\"Dol Amroth\", \"Gondor\"]")
        String[] usableBy
) {
}
