package com.ardaslegends.domain.claimbuilds;

import com.ardaslegends.domain.AbstractDomainObject;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/***
 * This is the Id Mapping for the Production - Claimbuild Association Table.
 * Without this, JPA doesn't map the primary key correctly.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public final class ProductionClaimbuildId extends AbstractDomainObject implements Serializable {

    @Serial
    private static final long serialVersionUID = -7659401942823299559L;

    /**
     * The ID of the production site.
     */
    private Long productionSiteId;

    /**
     * The ID of the claim build.
     */
    private Long claimbuildId;

    /**
     * Checks if this ProductionClaimbuildId is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionClaimbuildId that = (ProductionClaimbuildId) o;
        return Objects.equals(productionSiteId, that.productionSiteId) && Objects.equals(claimbuildId, that.claimbuildId);
    }

    /**
     * Returns the hash code of this ProductionClaimbuildId.
     *
     * @return the hash code of this ProductionClaimbuildId
     */
    @Override
    public int hashCode() {
        return Objects.hash(productionSiteId, claimbuildId);
    }
}