package com.ardaslegends.repository.war.battle;

import com.ardaslegends.domain.war.battle.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

/**
 * Repository interface for managing {@link Battle} entities.
 */
public interface BattleRepository extends JpaRepository<Battle, Long>, BattleRepositoryCustom {
    @Query("SELECT b FROM Battle b JOIN b.attackingArmies a JOIN b.defendingArmies d WHERE (a.id = :armyId OR d.id = :armyId) AND b.battlePhase <> 'CONCLUDED'")
    Battle findActiveBattleByArmyId(@Param("armyId") Long armyId);

    @Query("SELECT b FROM Battle b JOIN b.attackingArmies a JOIN b.defendingArmies d WHERE (a.id = :armyId OR d.id = :armyId) AND b.battlePhase = 'CONCLUDED'")
    Set<Battle> findPastBattlesByArmyId(@Param("armyId") Long armyId);

}
