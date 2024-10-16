package com.ardaslegends.repository.unittype;

import com.ardaslegends.domain.UnitType;

import java.util.List;

/**
 * Custom repository interface for managing {@link UnitType} entities.
 */
public interface UnitTypeRepositoryCustom {

    List<UnitType> queryByFactionNames(List<String> factionNames);
}
