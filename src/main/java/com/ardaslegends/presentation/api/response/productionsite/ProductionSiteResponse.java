package com.ardaslegends.presentation.api.response.productionsite;

import com.ardaslegends.domain.claimbuilds.ProductionSite;

public record ProductionSiteResponse(String type, String resource) {
    public ProductionSiteResponse(ProductionSite productionSite) {
        this(
                productionSite.getType().getName(),
                productionSite.getProducedResource().getResourceName()
        );
    }
}
