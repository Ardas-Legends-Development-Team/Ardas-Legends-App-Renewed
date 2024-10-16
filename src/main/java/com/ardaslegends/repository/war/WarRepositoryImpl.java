package com.ardaslegends.repository.war;

import com.ardaslegends.domain.Faction;
import com.ardaslegends.domain.war.QWar;
import com.ardaslegends.domain.war.QWarParticipant;
import com.ardaslegends.domain.war.War;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import lombok.val;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Custom repository implementation for managing {@link War} entities.
 * <p>
 * This implementation provides custom query methods for {@link War} entities.
 * </p>
 */
public class WarRepositoryImpl extends QuerydslRepositorySupport implements WarRepositoryCustom {

    /**
     * Constructs a new {@link WarRepositoryImpl}.
     */
    public WarRepositoryImpl() {
        super(War.class);
    }

    /**
     * Queries wars by faction and status.
     *
     * @param faction   the faction to query wars for.
     * @param warStatus the status of the wars to query.
     * @return a set of wars involving the specified faction and matching the specified status.
     * @throws NullPointerException if the faction or warStatus is null.
     */
    @Override
    public Set<War> queryWarsByFaction(Faction faction, QueryWarStatus warStatus) {
        Objects.requireNonNull(faction, "Faction must not be null!");
        QWar qWar = QWar.war;
        //this way we create the instances with alias
        QWarParticipant qAggressors = new QWarParticipant("aggressors");
        QWarParticipant qDefenders = new QWarParticipant("defenders");

        val result = from(qWar)
                .leftJoin(qWar.aggressors, qAggressors)
                .leftJoin(qWar.defenders, qDefenders)
                .where(
                        qAggressors.warParticipant.name.eq(faction.getName())
                                .or(qDefenders.warParticipant.name.eq(faction.getName()))
                                .and(activePredicate(warStatus)))
                .fetch();

        return new HashSet<>(result);
    }

    /**
     * Queries wars between two factions and status.
     *
     * @param faction1  the first faction.
     * @param faction2  the second faction.
     * @param warStatus the status of the wars to query.
     * @return a set of wars between the specified factions and matching the specified status.
     * @throws NullPointerException if any of the factions or warStatus is null.
     */
    @Override
    public Set<War> queryWarsBetweenFactions(Faction faction1, Faction faction2, QueryWarStatus warStatus) {
        Objects.requireNonNull(faction1, "Faction must not be null");
        Objects.requireNonNull(faction2, "Faction must not be null");

        QWar qWar = QWar.war;
        //this way we create the instances with alias
        QWarParticipant qAggressors = new QWarParticipant("aggressors");
        QWarParticipant qDefenders = new QWarParticipant("defenders");

        val result = from(qWar)
                .leftJoin(qWar.aggressors, qAggressors)
                .leftJoin(qWar.defenders, qDefenders)
                .where(
                        qAggressors.warParticipant.name.eq(faction1.getName()).and(qDefenders.warParticipant.name.eq(faction2.getName()))
                                .or(qAggressors.warParticipant.name.eq(faction2.getName()).and(qDefenders.warParticipant.name.eq(faction1.getName())))
                                .and(activePredicate(warStatus)))
                .fetch();

        return new HashSet<>(result);
    }

    /**
     * Queries the active initial war between two factions.
     *
     * @param faction1 the first faction.
     * @param faction2 the second faction.
     * @return an {@link Optional} containing the active initial war between the specified factions, or empty if not found.
     * @throws NullPointerException if any of the factions is null.
     */
    @Override
    public Optional<War> queryActiveInitialWarBetween(Faction faction1, Faction faction2) {
        Objects.requireNonNull(faction1, "Faction must not be null");
        Objects.requireNonNull(faction2, "Faction must not be null");

        QWar qWar = QWar.war;
        //this way we create the instances with alias
        QWarParticipant qAggressors = new QWarParticipant("aggressors");
        QWarParticipant qDefenders = new QWarParticipant("defenders");

        val result = from(qWar)
                .leftJoin(qWar.aggressors, qAggressors)
                .leftJoin(qWar.defenders, qDefenders)
                .where(
                        qAggressors.warParticipant.name.eq(faction1.getName()).and(qDefenders.warParticipant.name.eq(faction2.getName()))
                                .or(qAggressors.warParticipant.name.eq(faction2.getName()).and(qDefenders.warParticipant.name.eq(faction1.getName())))
                                .and(qWar.isActive.isTrue()))
                .fetchFirst();

        return Optional.ofNullable(result);
    }

    /**
     * Queries an active war by its name.
     *
     * @param name the name of the war.
     * @return an {@link Optional} containing the active war with the specified name, or empty if not found.
     * @throws NullPointerException if the name is null.
     */
    @Override
    public Optional<War> queryActiveWarByName(String name) {
        Objects.requireNonNull(name, "War name must not be null in queryActiveWarByName!");

        QWar qWar = QWar.war;

        val result = from(qWar)
                .where(qWar.isActive.and(qWar.name.eq(name)))
                .fetchFirst();

        return Optional.ofNullable(result);
    }

    /**
     * Creates a predicate for filtering wars by their status.
     *
     * @param warStatus the status of the wars to filter.
     * @return a {@link BooleanExpression} representing the predicate for filtering wars by status.
     * @throws NullPointerException if the warStatus is null.
     */
    private BooleanExpression activePredicate(QueryWarStatus warStatus) {
        Objects.requireNonNull(warStatus, "WarStatus must not be null");
        val war = QWar.war;
        return switch (warStatus) {
            case ACTIVE -> war.isActive.isTrue();
            case INACTIVE -> war.isActive.isFalse();
            case ALL -> Expressions.TRUE;
        };
    }
}