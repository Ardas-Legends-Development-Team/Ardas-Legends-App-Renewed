package com.ardaslegends.repository;

import com.ardaslegends.domain.claimbuilds.ProductionSite;
import com.ardaslegends.repository.productionsite.ProductionSiteRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link ProductionSite} entities.
 */
@Repository
public interface ProductionSiteRepository extends JpaRepository<ProductionSite, Long>, ProductionSiteRepositoryCustom {

}
