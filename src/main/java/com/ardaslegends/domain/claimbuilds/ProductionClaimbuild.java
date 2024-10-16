package com.ardaslegends.domain.claimbuilds;

import com.ardaslegends.domain.AbstractDomainObject;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Association Table for the Claimbuild 1:n - n:1 ProductionSite Association.
 * This class is marked as {@link Entity} and is mapped to the "production_claimbuild" table in the database.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "production_claimbuild")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "productionSite")
public final class ProductionClaimbuild extends AbstractDomainObject {

    /**
     * The composite key for the ProductionClaimbuild entity.
     */
    @EmbeddedId
    private ProductionClaimbuildId id;

    /**
     * The production site associated with this ProductionClaimbuild.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId("productionSiteId")
    @JoinColumn(name = "production_site_id", foreignKey = @ForeignKey(name = "fk_production_claimbuild_production_site_id"))
    private ProductionSite productionSite;

    /**
     * The claim build associated with this ProductionClaimbuild.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @MapsId("claimbuildId")
    @JoinColumn(name = "claimbuild_id", foreignKey = @ForeignKey(name = "fk_production_claimbuild_claimbuild_id"))
    private ClaimBuild claimbuild;

    /**
     * The count of the production claim build.
     */
    private Long count;

    /**
     * Constructs a new ProductionClaimbuild with the specified production site, claim build, and count.
     *
     * @param productionSite the production site associated with this ProductionClaimbuild
     * @param claimbuild     the claim build associated with this ProductionClaimbuild
     * @param count          the count of the production claim build
     */
    public ProductionClaimbuild(ProductionSite productionSite, ClaimBuild claimbuild, Long count) {
        this.id = new ProductionClaimbuildId();
        this.productionSite = productionSite;
        this.claimbuild = claimbuild;
        this.count = count;
    }

    /**
     * Checks if this ProductionClaimbuild is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionClaimbuild that = (ProductionClaimbuild) o;
        return productionSite.equals(that.productionSite) && claimbuild.equals(that.claimbuild) && count.equals(that.count);
    }

    /**
     * Returns the hash code of this ProductionClaimbuild.
     *
     * @return the hash code of this ProductionClaimbuild
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, productionSite, claimbuild, count);
    }
}