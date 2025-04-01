package com.ardaslegends.service.dto.army;

import com.ardaslegends.domain.ArmyType;
import com.ardaslegends.service.dto.unit.UnitTypeDto;

public record CreateArmyDto(
        String executorDiscordId,
        String name,
        ArmyType armyType,
        String claimBuildName,
        UnitTypeDto[] units,
        String unitString) {

    public CreateArmyDto(String executorDiscordId, String name, ArmyType armyType, String claimBuildName, UnitTypeDto[] units) {
        this(executorDiscordId, name, armyType, claimBuildName, units, null);
    }
}
