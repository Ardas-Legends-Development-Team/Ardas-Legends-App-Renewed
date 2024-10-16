package com.ardaslegends.repository;

import com.ardaslegends.domain.Army;
import com.ardaslegends.domain.ArmyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Army} entities.
 */
@Repository
public interface ArmyRepository extends JpaRepository<Army, Long> {

    Optional<Army> findArmyByName(String name);

    List<Army> findAllByArmyType(ArmyType armyType);

    List<Army> findArmyByIsHealingTrue();

}
