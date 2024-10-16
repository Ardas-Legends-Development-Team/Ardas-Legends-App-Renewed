package com.ardaslegends.repository.war.battle;

import com.ardaslegends.domain.war.battle.Battle;
import com.ardaslegends.domain.war.battle.QBattle;
import com.ardaslegends.repository.exceptions.NotFoundException;
import com.ardaslegends.repository.exceptions.RepositoryNullPointerException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * Custom repository implementation for managing {@link Battle} entities.
 * <p>
 * This implementation provides custom query methods for {@link Battle} entities.
 * </p>
 */
@Slf4j
public class BattleRepositoryImpl extends QuerydslRepositorySupport implements BattleRepositoryCustom {

    /**
     * Constructs a new {@link BattleRepositoryImpl}.
     */
    public BattleRepositoryImpl() {
        super(Battle.class);
    }

    /**
     * Queries a {@link Battle} by its ID or throws an exception if not found.
     *
     * @param id the ID of the battle.
     * @return the {@link Battle} with the specified ID.
     * @throws RepositoryNullPointerException if the ID is null.
     * @throws NotFoundException              if no battle with the specified ID is found.
     */
    @Override
    public Battle queryByIdOrElseThrow(Long id) {
        log.debug("Finding battle by id [{}]", id);
        if (id == null) {
            log.warn("Id was null in BattleRepositoryImpl.findByIdOrElseThrow");
            throw RepositoryNullPointerException.queryMethodParameterWasNull("id", "findByIdOrElseThrow");
        }

        QBattle qBattle = QBattle.battle;

        val result = from(qBattle)
                .where(qBattle.id.eq(id))
                .fetchFirst();

        if (result == null) {
            log.warn("Could not find battle with id [{}]", id);
            throw NotFoundException.genericNotFound("battle", "id", id.toString());
        }

        log.debug("Found battle [{}]", result);
        return result;
    }
}