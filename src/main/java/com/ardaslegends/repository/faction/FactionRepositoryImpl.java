package com.ardaslegends.repository.faction;

import com.ardaslegends.domain.Faction;
import com.ardaslegends.domain.QFaction;
import com.ardaslegends.repository.exceptions.FactionRepositoryException;
import lombok.val;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Objects;
import java.util.Optional;

/**
 * Custom repository implementation for managing {@link Faction} entities.
 * <p>
 * This implementation provides custom query methods for {@link Faction} entities.
 * </p>
 */
public class FactionRepositoryImpl extends QuerydslRepositorySupport implements FactionRepositoryCustom {

    /**
     * Constructs a new {@link FactionRepositoryImpl}.
     */
    public FactionRepositoryImpl() {
        super(Faction.class);
    }

    /**
     * Queries a {@link Faction} by its name.
     *
     * @param factionName the name of the faction.
     * @return the {@link Faction} with the specified name.
     * @throws FactionRepositoryException if no faction with the specified name is found.
     * @throws NullPointerException       if the faction name is null.
     */
    @Override
    public Faction queryByName(String factionName) {
        val fetchedFaction = queryByNameOptional(factionName);

        if (fetchedFaction.isEmpty()) {
            throw FactionRepositoryException.entityNotFound("factionName", factionName);
        }

        return fetchedFaction.get();
    }

    /**
     * Queries an {@link Optional} containing a {@link Faction} by its name.
     *
     * @param factionName the name of the faction.
     * @return an {@link Optional} containing the found {@link Faction}, or empty if not found.
     * @throws NullPointerException if the faction name is null.
     */
    @Override
    public Optional<Faction> queryByNameOptional(String factionName) {
        Objects.requireNonNull(factionName);
        QFaction qFaction = QFaction.faction;

        Faction fetchedFaction = from(qFaction)
                .where(qFaction.name.equalsIgnoreCase(factionName)
                        .or(qFaction.aliases.any().equalsIgnoreCase(factionName)))
                .fetchFirst();

        return Optional.ofNullable(fetchedFaction);
    }
}