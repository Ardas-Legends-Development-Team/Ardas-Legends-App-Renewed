package com.ardaslegends.service.exceptions.logic.war;

import com.ardaslegends.service.exceptions.logic.LogicException;

public class BattleServiceException extends LogicException {
    public static final String FACTIONS_NOT_AT_WAR = "Cannot declare a battle because %s and %s are not at war with each other!";
    public static final String BATTLE_NOT_ABLE_DUE_HOURS = "Region can not be reached within 24 hours";
    public static final String NOT_ENOUGH_HEALTH = "Army does not have enough health";
    public static final String MORE_THAN_ONE_ACTIVE_MOVEMENT = "Army can not have more than one active movement simultaneously";
    public static final String DEFENDING_ARMY_IS_MOVING_AWAY = "The army you are trying to attack is moving away from you and will already reach the next region in %d hours!";
    public static final String ATTACKING_ARMY_HAS_ANOTHER_MOVEMENT = "Attacking army has another ongoing movement!";

    public static BattleServiceException factionsNotAtWar(String factionName1, String factionName2) { return new BattleServiceException(FACTIONS_NOT_AT_WAR.formatted(factionName1, factionName2)); }

    public static BattleServiceException battleNotAbleDueHours (){ return  new BattleServiceException(BATTLE_NOT_ABLE_DUE_HOURS.formatted());}
    public static BattleServiceException defendingArmyIsMovingAway (int hoursUntilNextRegion){return new BattleServiceException(DEFENDING_ARMY_IS_MOVING_AWAY.formatted(hoursUntilNextRegion));}
    public static BattleServiceException attackingArmyHasAnotherMovement(){return new BattleServiceException(ATTACKING_ARMY_HAS_ANOTHER_MOVEMENT.formatted());}
    public static BattleServiceException notEnoughHealth(){ return new BattleServiceException(NOT_ENOUGH_HEALTH.formatted());}
    public static BattleServiceException moreThanOneActiveMovement(){return new BattleServiceException(MORE_THAN_ONE_ACTIVE_MOVEMENT.formatted());}
    protected BattleServiceException(String message, Throwable rootCause) {
        super(message, rootCause);
    }
    protected BattleServiceException(String message) {
        super(message);
    }
}
