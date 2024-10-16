package com.ardaslegends.repository.war;

import com.ardaslegends.domain.Faction;
import com.ardaslegends.domain.war.War;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Repository interface for managing {@link War} entities.
 */
@Repository
public interface WarRepository extends JpaRepository<War, Long>, WarRepositoryCustom {
    Set<War> findByName(String name);

    /**
     * Find all wars that a faction is a participant in.
     * <p>
     * This method will return all wars that a faction is a participant in, regardless of whether the war is active or not.
     * </p>
     *
     * @param faction The faction to find wars for.
     * @return A set of wars that the faction is a participant in.
     */
    @Query("""
            select w from War w 
                left join w.aggressors aggressors 
                left join w.defenders defenders
            where (aggressors.warParticipant = ?1
            or defenders.warParticipant = ?1)
            and w.isActive = true""")
    Set<War> findAllActiveWarsWithFaction(Faction faction);
}
