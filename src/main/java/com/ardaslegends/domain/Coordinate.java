package com.ardaslegends.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

/**
 * Represents a coordinate in Minecraft.
 * This class is marked as {@link Embeddable} to be used as an embedded object in other entities.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Coordinate {

    /**
     * The x-coordinate.
     */
    private Integer x;

    /**
     * The y-coordinate.
     */
    private Integer y;

    /**
     * The z-coordinate.
     */
    private Integer z;

    /**
     * Checks if this Coordinate is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x.equals(that.x) && y.equals(that.y) && z.equals(that.z);
    }

    /**
     * Returns the hash code of this Coordinate.
     *
     * @return the hash code of this Coordinate
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    /**
     * Returns a string representation of this Coordinate.
     *
     * @return a string representation of this Coordinate
     */
    @Override
    public String toString() {
        return x + " " + y + " " + z;
    }
}