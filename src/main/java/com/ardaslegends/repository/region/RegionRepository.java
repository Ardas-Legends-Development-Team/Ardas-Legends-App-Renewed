package com.ardaslegends.repository.region;

import com.ardaslegends.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link Region} entities.
 */
@Repository
public interface RegionRepository extends JpaRepository<Region, String>, RegionRepositoryCustom {
    List<Region> findAllByHasOwnershipChangedTrue();
}
