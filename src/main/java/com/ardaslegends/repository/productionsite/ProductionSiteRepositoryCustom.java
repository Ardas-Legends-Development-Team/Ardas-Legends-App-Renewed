package com.ardaslegends.repository.productionsite;

import com.ardaslegends.domain.claimbuilds.ProductionSite;
import com.ardaslegends.domain.claimbuilds.ProductionSiteType;

import java.util.Optional;
import java.util.Set;

/**
 * Custom repository interface for managing {@link ProductionSite} entities.
 */
public interface ProductionSiteRepositoryCustom {

    Set<ProductionSite> queryAll();

    ProductionSite queryByTypeAndResource(ProductionSiteType type, String resource);

    Optional<ProductionSite> queryByTypeAndResourceOptional(ProductionSiteType type, String resource);
}
