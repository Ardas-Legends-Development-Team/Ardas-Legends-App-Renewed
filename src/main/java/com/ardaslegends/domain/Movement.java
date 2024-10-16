package com.ardaslegends.domain;

import com.ardaslegends.service.utils.ServiceUtils;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Represents a movement in the application.
 * This class is marked as {@link Entity} and is mapped to the "movements" table in the database.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Slf4j
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
@Table(name = "movements")
public final class Movement extends AbstractDomainObject {

    /**
     * The unique identifier of the movement.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The roleplay character associated with this movement.
     */
    @ManyToOne
    @JoinColumn(name = "rpchar_id", foreignKey = @ForeignKey(name = "fk_movement_rpchar_id"))
    private RPChar rpChar;

    /**
     * The army associated with this movement. Is null when it's a roleplay character movement.
     */
    @ManyToOne
    @JoinColumn(name = "army_name", foreignKey = @ForeignKey(name = "fk_movement_army_name"))
    private Army army;

    /**
     * Indicates if the movement is a character movement. Should be true when army is null.
     */
    private Boolean isCharMovement;

    /**
     * The path of the movement.
     */
    @ElementCollection
    private List<PathElement> path;

    /**
     * The start time of the movement.
     */
    private OffsetDateTime startTime;

    /**
     * The end time of the movement.
     */
    private OffsetDateTime endTime;

    /**
     * Indicates if the movement is currently active.
     */
    private Boolean isCurrentlyActive;

    /**
     * The time when the movement reaches the next region.
     */
    private OffsetDateTime reachesNextRegionAt;

    /**
     * The last time the movement was updated.
     */
    private OffsetDateTime lastUpdatedAt;

    /**
     * Constructs a new Movement.
     *
     * @param rpChar              the roleplay character associated with this movement
     * @param army                the army associated with this movement
     * @param isCharMovement      indicates if the movement is a character movement
     * @param path                the path of the movement
     * @param startTime           the start time of the movement
     * @param endTime             the end time of the movement
     * @param isCurrentlyActive   indicates if the movement is currently active
     * @param reachesNextRegionAt the time when the movement reaches the next region
     */
    public Movement(RPChar rpChar, Army army, Boolean isCharMovement, List<PathElement> path, OffsetDateTime startTime, OffsetDateTime endTime, Boolean isCurrentlyActive, OffsetDateTime reachesNextRegionAt) {
        this.rpChar = rpChar;
        this.army = army;
        this.isCharMovement = isCharMovement;
        this.path = path;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isCurrentlyActive = isCurrentlyActive;
        this.reachesNextRegionAt = reachesNextRegionAt;
    }

    /**
     * Returns the ID of the start region of the movement.
     *
     * @return the ID of the start region
     */
    public String getStartRegionId() {
        return path.get(0).getRegion().getId();
    }

    /**
     * Returns the ID of the destination region of the movement.
     *
     * @return the ID of the destination region
     */
    public String getDestinationRegionId() {
        return path.get(path.size() - 1).getRegion().getId();
    }


    /**
     * Sets the movement to inactive and sets reachesNextRegionAt to null.
     */
    public void end() {
        log.debug("Ending movement of {} {} with path {}", getMovingEntity(), getMovingEntityName(), ServiceUtils.buildPathString(path));
        isCurrentlyActive = false;
        reachesNextRegionAt = null;
    }

    /**
     * Returns the type of entity that is moving.
     *
     * @return Either "character" or "army", depending on the kind of entity that is moving.
     */
    public String getMovingEntity() {
        return isCharMovement ? "character" : "army";
    }

    /**
     * Returns the name of the army or character that is moving.
     *
     * @return The name of the army or character that is moving.
     */
    public String getMovingEntityName() {
        return isCharMovement ? rpChar.getName() : army.getName();
    }

    /**
     * Returns the cost of the movement.
     *
     * @return The cost of the movement.
     */
    public Integer getCost() {
        return ServiceUtils.getTotalPathCost(path);
    }

    /**
     * Returns the current region of the movement.
     *
     * @return The current region of the movement.
     */
    public Region getCurrentRegion() {
        return isCharMovement ? rpChar.getCurrentRegion() : army.getCurrentRegion();
    }

    /**
     * Sets the current region of the movement.
     *
     * @param region the current region to set
     */
    public void setCurrentRegion(Region region) {
        if (isCharMovement) {
            log.trace("Movement is char movement, setting current region to [{}]", region);
            rpChar.setCurrentRegion(region);
        } else {
            log.trace("Movement is army movement, setting current region to [{}]", region);
            army.setCurrentRegion(region);
            if (army.getBoundTo() != null) {
                log.trace("Army is bound to a character, setting the character's region to [{}]", region);
                army.getBoundTo().setCurrentRegion(region);
            }
        }
    }

    /**
     * Returns the next region in the path.
     *
     * @return The next region. Null if there is no next region.
     */
    public Region getNextRegion() {
        val nextPathElement = getNextPathElement();
        return nextPathElement == null ? null : nextPathElement.getRegion();
    }

    /**
     * Returns the current path element.
     *
     * @return The current path element.
     * @throws IllegalStateException if the current region is not found in the path
     */
    public PathElement getCurrentPathElement() {
        val currentRegion = getCurrentRegion();
        return path.stream().filter(pathElement -> pathElement.hasRegion(currentRegion)).findFirst()
                .orElseThrow(() -> {
                    log.warn("COULD NOT FIND REGION [{}] IN PATH [{}] OF MOVEMENT [{}] - THIS ERROR SHOULD NEVER BE THROWN",
                            currentRegion, ServiceUtils.buildPathString(path), this);
                    return new IllegalStateException("COULD NOT FIND REGION %s IN PATH %s. PLEASE CONTACT A DEV IMMEDIATELY"
                            .formatted(currentRegion.getId(), ServiceUtils.buildPathString(path)));
                });
    }

    /**
     * Returns the next path element in the path.
     *
     * @return The next path element. Null if there is no next region.
     */
    public PathElement getNextPathElement() {
        val currentPathElement = getCurrentPathElement();
        val nextRegionIndex = path.indexOf(currentPathElement) + 1;
        if (nextRegionIndex >= path.size())
            return null;
        return path.get(nextRegionIndex);
    }

    /**
     * Returns the duration until the next region is reached.
     *
     * @return The duration until the next region is reached.
     */
    public Duration getDurationUntilNextRegion() {
        if (reachesNextRegionAt == null)
            return Duration.ZERO;
        else
            return Duration.between(OffsetDateTime.now(), reachesNextRegionAt);
    }

    /**
     * Returns the duration until the movement is complete.
     *
     * @return The duration until the movement is complete.
     */
    public Duration getDurationUntilComplete() {
        val now = OffsetDateTime.now();
        return Duration.between(now, endTime);
    }

    /**
     * Returns the duration already moved.
     *
     * @return The duration already moved.
     */
    public Duration getDurationAlreadyMoved() {
        val now = OffsetDateTime.now();
        return Duration.between(startTime, now);
    }

    /**
     * Checks if this Movement is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return Objects.equals(id, movement.id);
    }

    /**
     * Returns the hash code of this Movement.
     *
     * @return the hash code of this Movement
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Returns a string representation of this Movement.
     *
     * @return a string representation of this Movement
     */
    @Override
    public String toString() {
        return "Movement{" +
                "roleplayCharacter=" + rpChar +
                ", army=" + army +
                ", isCharMovement=" + isCharMovement +
                ", path=" + path +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}