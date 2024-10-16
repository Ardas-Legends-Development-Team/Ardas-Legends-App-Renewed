package com.ardaslegends.repository.war.battle;

import com.ardaslegends.domain.war.battle.Battle;

/**
 * Custom repository interface for managing {@link Battle} entities.
 */
public interface BattleRepositoryCustom {

    Battle queryByIdOrElseThrow(Long id);
}
