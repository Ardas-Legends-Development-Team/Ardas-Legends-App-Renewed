package com.ardaslegends.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Represents an abstract entity that serves as a base class for all entities in the application.
 * This class provides common properties such as id and version.
 */
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractEntity extends AbstractDomainObject {

    /**
     * The unique identifier of the entity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    /**
     * The version of the entity, used for optimistic locking.
     */
    @Version
    @EqualsAndHashCode.Exclude
    private Integer version = 0;

    /**
     * Checks if this AbstractEntity is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity that = (AbstractEntity) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Returns the hash code of this AbstractEntity.
     *
     * @return the hash code of this AbstractEntity
     */
    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}