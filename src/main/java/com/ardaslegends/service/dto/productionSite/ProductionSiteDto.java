package com.ardaslegends.service.dto.productionSite;

import com.ardaslegends.domain.applications.EmbeddedProductionSite;
import com.ardaslegends.domain.claimbuilds.ProductionSiteType;

public record ProductionSiteDto(ProductionSiteType type, String resource, long count) {
    public ProductionSiteDto(EmbeddedProductionSite embeddedProductionSite) {
        this(
                embeddedProductionSite.getProductionSite().getType(),
                embeddedProductionSite.getProductionSite().getProducedResource().getResourceName(),
                embeddedProductionSite.getCount()
        );
    }
}
