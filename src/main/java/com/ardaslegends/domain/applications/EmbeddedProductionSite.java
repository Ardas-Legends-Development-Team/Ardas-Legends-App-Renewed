package com.ardaslegends.domain.applications;

import com.ardaslegends.domain.AbstractDomainObject;
import com.ardaslegends.domain.claimbuilds.ProductionSite;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Represents an embedded production site within a claim build application.
 * This class is marked as {@link Embeddable} to be used as a component of other entities.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Embeddable
public class EmbeddedProductionSite extends AbstractDomainObject {

    /**
     * The production site associated with this embedded production site.
     */
    @ManyToOne
    @JoinColumn(name = "production_site_id", foreignKey = @ForeignKey(name = "fk_claimbuild_application_production_sites_production_site_id"))
    private ProductionSite productionSite;

    /**
     * The count of the production site.
     */
    private Long count;

    /**
     * Checks if this embedded production site is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmbeddedProductionSite that = (EmbeddedProductionSite) o;
        return Objects.equals(productionSite, that.productionSite);
    }

    /**
     * Returns the hash code of this embedded production site.
     *
     * @return the hash code of this embedded production site
     */
    @Override
    public int hashCode() {
        return Objects.hash(productionSite);
    }
}