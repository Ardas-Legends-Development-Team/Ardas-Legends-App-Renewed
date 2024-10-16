package com.ardaslegends.repository;

import com.ardaslegends.domain.Army;
import com.ardaslegends.domain.Movement;
import com.ardaslegends.domain.RPChar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Movement} entities.
 */
@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    //TODO add Test
    List<Movement> findMovementsByRpChar(RPChar rpChar);

    List<Movement> findMovementByArmyAndIsCurrentlyActiveFalse(Army army);

    List<Movement> findMovementByRpCharAndIsCurrentlyActiveFalse(RPChar rpChar);

    Optional<Movement> findMovementByArmyAndIsCurrentlyActiveTrue(Army army);

    Optional<Movement> findMovementByRpCharAndIsCurrentlyActiveTrue(RPChar rpChar);

    List<Movement> findMovementsByIsCurrentlyActive(Boolean isActive);
}
