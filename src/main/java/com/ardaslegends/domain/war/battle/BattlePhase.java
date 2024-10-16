package com.ardaslegends.domain.war.battle;

/**
 * Represents the different phases of a battle.
 */
public enum BattlePhase {
    /**
     * After declaration, before the 24h timer runs out.
     * During this phase, allies can aid.
     */
    PRE_BATTLE,

    /**
     * During time freeze, waiting for the battle to happen.
     */
    ONGOING,

    /**
     * After the battle happened and results are submitted.
     */
    CONCLUDED
}