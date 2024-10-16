package com.ardaslegends.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Objects;

/**
 * Represents an element in a path.
 * This class is marked as {@link Embeddable} and is used to define the path of a movement.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class PathElement {

    /**
     * The actual cost of the path element in hours.
     */
    private Integer actualCost; // in hours

    /**
     * The base cost of the path element in hours.
     */
    private Integer baseCost; // in hours

    /**
     * The region associated with this path element.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Region region;

    /**
     * Checks if this PathElement is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathElement that = (PathElement) o;
        return Objects.equals(actualCost, that.actualCost) &&
                Objects.equals(baseCost, that.baseCost) &&
                Objects.equals(region, that.region);
    }

    /**
     * Returns the hash code of this PathElement.
     *
     * @return the hash code of this PathElement
     */
    @Override
    public int hashCode() {
        return Objects.hash(actualCost, baseCost, region);
    }

    /**
     * Returns a string representation of this PathElement.
     *
     * @return a string representation of this PathElement
     */
    @Override
    public String toString() {
        return "PathElement{" +
                "region=" + region.getId() +
                ", actualCost=" + actualCost +
                '}';
    }

    /**
     * Checks if this PathElement has the specified region.
     *
     * @param region the region to check
     * @return true if this PathElement has the specified region, false otherwise
     */
    public boolean hasRegion(Region region) {
        return this.region.equals(region);
    }
}