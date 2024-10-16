package com.ardaslegends.repository;

import com.ardaslegends.domain.claimbuilds.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link Resource} entities.
 */
@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
