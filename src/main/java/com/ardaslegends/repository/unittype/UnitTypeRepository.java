package com.ardaslegends.repository.unittype;

import com.ardaslegends.domain.UnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link UnitType} entities.
 */
@Repository
public interface UnitTypeRepository extends JpaRepository<UnitType, String>, UnitTypeRepositoryCustom {
}
