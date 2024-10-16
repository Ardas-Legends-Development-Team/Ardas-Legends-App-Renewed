package com.ardaslegends.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Represents a production site in the application.
 * This class is marked as {@link Entity} and is mapped to the "production_sites" table in the database.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "production_sites")
public final class ProductionSite extends AbstractDomainObject {

    /**
     * The unique identifier of the production site.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type of the production site.
     */
    @Enumerated(EnumType.STRING)
    private ProductionSiteType type; // unique, type of production site, e.g. FARM

    /**
     * The resource produced by this production site.
     */
    @ManyToOne
    @JoinColumn(referencedColumnName = "resource_name", name = "produced_resource", foreignKey = @ForeignKey(name = "fk_production_site_resource_name"))
    private Resource producedResource; // the resource this production site produces

    /**
     * The amount of resource produced by this production site.
     */
    @Column(name = "amount_produced")
    private Integer amountProduced; // the amount

    /**
     * Checks if this ProductionSite is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductionSite that = (ProductionSite) o;
        return type == that.type && producedResource.equals(that.producedResource) && amountProduced.equals(that.amountProduced);
    }

    /**
     * Returns the hash code of this ProductionSite.
     *
     * @return the hash code of this ProductionSite
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, producedResource, amountProduced);
    }
}