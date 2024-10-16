package com.ardaslegends.repository.productionsite;

import com.ardaslegends.domain.claimbuilds.ProductionSite;
import com.ardaslegends.domain.claimbuilds.ProductionSiteType;
import com.ardaslegends.domain.claimbuilds.QProductionSite;
import com.ardaslegends.repository.exceptions.ProductionSiteRepositoryException;
import lombok.val;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Custom repository implementation for managing {@link ProductionSite} entities.
 * <p>
 * This implementation provides custom query methods for {@link ProductionSite} entities.
 * </p>
 */
public class ProductionSiteRepositoryImpl extends QuerydslRepositorySupport implements ProductionSiteRepositoryCustom {

    /**
     * Constructs a new {@link ProductionSiteRepositoryImpl}.
     */
    public ProductionSiteRepositoryImpl() {
        super(ProductionSite.class);
    }

    /**
     * Queries all {@link ProductionSite} entities.
     *
     * @return a set of all {@link ProductionSite} entities.
     */
    @Override
    public Set<ProductionSite> queryAll() {
        QProductionSite qProductionSite = QProductionSite.productionSite;

        val productionSites = from(qProductionSite)
                .fetch();

        return new HashSet<>(productionSites);
    }

    /**
     * Queries a {@link ProductionSite} by its type and resource.
     *
     * @param type     the type of the production site.
     * @param resource the resource produced by the production site.
     * @return the {@link ProductionSite} with the specified type and resource.
     * @throws ProductionSiteRepositoryException if no production site with the specified type and resource is found.
     * @throws NullPointerException              if the type or resource is null.
     */
    @Override
    public ProductionSite queryByTypeAndResource(ProductionSiteType type, String resource) {
        val fetchedSite = queryByTypeAndResourceOptional(type, resource);

        return fetchedSite.orElseThrow(() -> ProductionSiteRepositoryException.entityNotFound("(type, resource)", "(" + type.getName() + ", " + resource + ")"));
    }

    /**
     * Queries an {@link Optional} containing a {@link ProductionSite} by its type and resource.
     *
     * @param type     the type of the production site.
     * @param resource the resource produced by the production site.
     * @return an {@link Optional} containing the found {@link ProductionSite}, or empty if not found.
     * @throws NullPointerException if the type or resource is null.
     */
    @Override
    public Optional<ProductionSite> queryByTypeAndResourceOptional(ProductionSiteType type, String resource) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(resource);

        QProductionSite qProductionSite = QProductionSite.productionSite;

        val fetchedSite = from(qProductionSite)
                .where(qProductionSite.type.eq(type).and(qProductionSite.producedResource.resourceName.equalsIgnoreCase(resource)))
                .fetchFirst();

        return Optional.ofNullable(fetchedSite);
    }
}