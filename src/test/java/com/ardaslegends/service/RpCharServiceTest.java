package com.ardaslegends.service;

import com.ardaslegends.domain.*;
import com.ardaslegends.repository.ArmyRepository;
import com.ardaslegends.repository.MovementRepository;
import com.ardaslegends.repository.claimbuild.ClaimbuildRepository;
import com.ardaslegends.repository.faction.FactionRepository;
import com.ardaslegends.repository.rpchar.RpcharRepository;
import com.ardaslegends.service.dto.player.rpchar.StationRpCharDto;
import com.ardaslegends.service.dto.player.rpchar.UnstationRpCharDto;
import com.ardaslegends.service.exceptions.logic.claimbuild.ClaimBuildServiceException;
import com.ardaslegends.service.exceptions.logic.rpchar.RpCharServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
public class RpCharServiceTest {

    private RpCharService rpCharService;

    private ArmyRepository mockArmyRepository;
    private MovementRepository mockMovementRepository;
    private FactionRepository mockFactionRepository;
    private PlayerService mockPlayerService;
    private UnitTypeService mockUnitTypeService;
    private ClaimbuildRepository mockClaimbuildRepository;
    private RpcharRepository mockRpCharRepository;

    private Faction faction;
    private Region region1;
    private Region region2;
    private UnitType unitType;
    private Unit unit;
    private RPChar rpchar;
    private Player player;
    private Army army;
    private Movement movement;
    private ClaimBuild claimBuild;

    @BeforeEach
    void setup() {
        mockArmyRepository = mock(ArmyRepository.class);
        mockMovementRepository = mock(MovementRepository.class);
        mockFactionRepository = mock(FactionRepository.class);
        mockPlayerService = mock(PlayerService.class);
        mockUnitTypeService = mock(UnitTypeService.class);
        mockClaimbuildRepository = mock(ClaimbuildRepository.class);
        mockRpCharRepository = mock(RpcharRepository.class);
        rpCharService = new RpCharService(mockRpCharRepository, mockPlayerService, mockClaimbuildRepository);


        region1 = Region.builder().id("90").build();
        region2 = Region.builder().id("91").build();
        unitType = UnitType.builder().unitName("Gondor Archer").tokenCost(1.5).build();
        unit = Unit.builder().unitType(unitType).army(army).amountAlive(5).count(10).build();
        faction = Faction.builder().name("Gondor").allies(new ArrayList<>()).build();
        claimBuild = ClaimBuild.builder().name("Nimheria").type(ClaimBuildType.CASTLE).siege("Ram, Trebuchet, Tower").region(region1).ownedBy(faction).specialBuildings(List.of(SpecialBuilding.HOUSE_OF_HEALING)).stationedArmies(List.of()).build();
        rpchar = RPChar.builder().name("Belegorn").isHealing(false).injured(false).currentRegion(region1).build();
        player = Player.builder().discordID("1234").faction(faction).build();
        player.addActiveRpChar(rpchar);
        army = Army.builder().name("Knights of Gondor").armyType(ArmyType.ARMY).faction(faction).units(List.of(unit)).freeTokens(30 - unit.getCount() * unitType.getTokenCost()).currentRegion(region1).stationedAt(claimBuild).sieges(new ArrayList<>()).build();
        movement = Movement.builder().isCharMovement(false).isCurrentlyActive(true).army(army).path(List.of(PathElement.builder().region(region1).build())).build();

        when(mockPlayerService.getPlayerByDiscordId(player.getDiscordID())).thenReturn(player);
        when(mockRpCharRepository.findRpcharByName(rpchar.getName())).thenReturn(Optional.of(rpchar));
        when(mockArmyRepository.findArmyByName(army.getName())).thenReturn(Optional.of(army));
        when(mockArmyRepository.save(army)).thenReturn(army);
        when(mockMovementRepository.findMovementByArmyAndIsCurrentlyActiveTrue(army)).thenReturn(Optional.of(movement));
        when(mockClaimbuildRepository.findClaimBuildByName(claimBuild.getName())).thenReturn(Optional.of(claimBuild));
    }


    // Station Tests

    @Test
    void ensureStationThrowsCbSeWhenClaimbuildWithGivenNameDoesNotExist() {
        log.debug("Testing if station throws CB Se when no claimbuild exists with given name");

        log.trace("Initializing data");
        when(mockClaimbuildRepository.findClaimBuildByName(claimBuild.getName())).thenReturn(Optional.empty());

        StationRpCharDto dto = new StationRpCharDto(player.getDiscordID(), rpchar.getName(), claimBuild.getName());

        log.debug("Expecting SE on call");
        log.debug("Calling station()");
        var result = assertThrows(ClaimBuildServiceException.class, () -> rpCharService.station(dto));

        assertThat(result.getMessage()).contains("Found no claimbuild with name");
        log.info("Test passed: station throws SE when no cb found");
    }

    @Test
    void ensureStationThrowsSeWhenCharacterIsAlreadyStationed() {
        log.debug("Testing if station throws Se when army is already stationed");

        rpchar.setStationedAt(claimBuild);

        StationRpCharDto dto = new StationRpCharDto(player.getDiscordID(), rpchar.getName(), claimBuild.getName());

        log.debug("Calling station(), expecting Se");
        var result = assertThrows(RpCharServiceException.class, () -> rpCharService.station(dto));

        assertThat(result.getMessage()).contains("is already stationed at");
        log.info("Test passed: station throws Se when already stationed");
    }

    @Test
    void ensureStationThrowsSeWhenClaimbuildIsNotInTheSameOrAlliedFaction() {
        log.debug("Testing if station throws Se when claimbuild is not in the same or allied faction");

        claimBuild.setOwnedBy(Faction.builder().name("Kek123").build());
        rpchar.setStationedAt(null);
        StationRpCharDto dto = new StationRpCharDto(player.getDiscordID(), rpchar.getName(), claimBuild.getName());

        log.debug("Calling station(), expecting Se");
        var result = assertThrows(RpCharServiceException.class, () -> rpCharService.station(dto));

        assertThat(result.getMessage()).contains("is not in the same or allied faction");
        log.info("Test passed: station throws Se when claimbuild is not in the same or allied faction");


    }

    @Test
    void ensureStationThrowsWhenClaimbuildIsNotInTheSameRegionAsCharacter() {
        log.debug("Testing if station throws Se when claimbuild is not in the same region as army");

        claimBuild.setRegion(region2);
        rpchar.setStationedAt(null);
        StationRpCharDto dto = new StationRpCharDto(player.getDiscordID(), rpchar.getName(), claimBuild.getName());

        log.debug("Calling station(), expecting Se");
        var result = assertThrows(RpCharServiceException.class, () -> rpCharService.station(dto));

        assertThat(result.getMessage()).contains("is not in the same region as");
        log.info("Test passed: station throws Se when claimbuild is not in the same region as character");
    }

    @Test
    void ensureStationWorksWhenClaimbuildIsInTheSameRegionAsCharacter() {
        log.debug("Testing if station works when claimbuild is in the same region as character");

        rpchar.setStationedAt(null);
        StationRpCharDto dto = new StationRpCharDto(player.getDiscordID(), rpchar.getName(), claimBuild.getName());

        log.debug("Calling station(), expecting no errors");
        var result = assertDoesNotThrow(() -> rpCharService.station(dto));

        log.info("Test passed: station does not throw Se when claimbuild is in the same region as character");
    }

    // Unstation tests

    @Test
    void ensureUnstationWorksProperly() {
        log.debug("Testing if unstation works properly with correct values");

        rpchar.setStationedAt(claimBuild);

        UnstationRpCharDto dto = new UnstationRpCharDto(player.getDiscordID(), rpchar.getName());

        log.debug("Calling unstation, expecting no errors");
        var result = rpCharService.unstation(dto);

        assertThat(result.getStationedAt()).isNull();
        log.info("Test passed: Unstation works correctly with correct values");
    }

    @Test
    void ensureUnstationThrowsSeWhenCharacterIsNotStationed() {
        log.debug("Testing if unstation() throws Se when character is not stationed at a Cb");

        rpchar.setStationedAt(null);

        UnstationRpCharDto dto = new UnstationRpCharDto(player.getDiscordID(), rpchar.getName());

        log.debug("calling unstation(), expecting Se");
        var result = assertThrows(RpCharServiceException.class, () -> rpCharService.unstation(dto));

        assertThat(result.getMessage()).contains("is not stationed at any claimbuild");
        log.info("Test passed: unstation throws Se when character is not stationed at a Claimbuild");
    }

}
