package com.ardaslegends.presentation.api;

import com.ardaslegends.domain.Faction;
import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.RPChar;
import com.ardaslegends.presentation.api.response.player.PlayerResponse;
import com.ardaslegends.presentation.api.response.player.PlayerRpCharResponse;
import com.ardaslegends.presentation.api.response.player.PlayerUpdateDiscordIdResponse;
import com.ardaslegends.presentation.api.response.player.rpchar.RpCharResponse;
import com.ardaslegends.service.FactionService;
import com.ardaslegends.service.PlayerService;
import com.ardaslegends.service.dto.player.*;
import com.ardaslegends.service.dto.player.rpchar.CreateRPCharDto;
import com.ardaslegends.service.dto.player.rpchar.UpdateRpCharDto;
import com.ardaslegends.util.TestDataFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@Transactional
public class PlayerRestControllerTest {

    private PlayerService mockPlayerService;

    private MockMvc mockMvc;

    private Player player;
    private Player player3;
    private RPChar rpChar;
    private Faction gondor;
    private Faction mordor;

    private PlayerResponse expectedPlayerResponse;
    private RpCharResponse expectedRpCharResponse;
    private PlayerRpCharResponse expectedPlayerRpCharResponse;
    private DiscordIdDto discordIdDto;


    @BeforeEach
    void setup() {
        mockPlayerService = mock(PlayerService.class);
        FactionService mockFactionService = mock(FactionService.class);
        PlayerRestController playerRestController = new PlayerRestController(mockPlayerService, mockFactionService);
        mockMvc = MockMvcBuilders.standaloneSetup(playerRestController).build();

        player = TestDataFactory.playerLuktronic();
        rpChar = TestDataFactory.rpcharBelegorn(player);
        gondor = TestDataFactory.factionGondor(player);
        mordor = TestDataFactory.factionMordor(null);
        player3 = TestDataFactory.playerHabKeinTeammate(gondor);

        expectedPlayerResponse = new PlayerResponse(player);
        expectedPlayerRpCharResponse = new PlayerRpCharResponse(player);
        expectedRpCharResponse = new RpCharResponse(rpChar);
        discordIdDto = new DiscordIdDto(player.getDiscordID());

    }

    // Create Method Tests

    @Test
    void ensureCreatePlayerWorksProperly() throws Exception {
        val newPlayer = TestDataFactory.playerVernonRoche(gondor);
        CreatePlayerDto createPlayerDto = new CreatePlayerDto(newPlayer.getIgn(), newPlayer.getDiscordID(), newPlayer.getFaction().getName());
        expectedPlayerResponse = new PlayerResponse(newPlayer);

        when(mockPlayerService.createPlayer(createPlayerDto)).thenReturn(newPlayer);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(createPlayerDto);

        var result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080"+PlayerRestController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedPlayerResponse));
    }

    // Create RPChar Test

    @Test
    void ensureCreateRpCharWorksProperly() throws Exception {
        val newRpChar = new RPChar(player3, "Canathir", "Master of Coin", "Gondor gear", true, "someLink");
        expectedRpCharResponse = new RpCharResponse(newRpChar);
        CreateRPCharDto createRPCharDto = new CreateRPCharDto(newRpChar.getOwner().getDiscordID(), newRpChar.getName(), newRpChar.getTitle(), newRpChar.getGear(), newRpChar.getPvp());

        when(mockPlayerService.createRoleplayCharacter(createRPCharDto)).thenReturn(newRpChar);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(createRPCharDto);

        var result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/player"+PlayerRestController.PATH_RPCHAR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedRpCharResponse));
    }

    // Read Methods Test

    @Test
    void ensureGetByIgnWorksProperly() throws Exception {
        when(mockPlayerService.getPlayerByIgn(player.getIgn())).thenReturn(player);

        var result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/player"+PlayerRestController.PATH_GET_BY_IGN.replace("{ign}", player.getIgn()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(new ObjectMapper().writeValueAsString(expectedPlayerRpCharResponse));
    }

    @Test
    void ensureGetByDiscordIdWorksProperly() throws Exception {
        when(mockPlayerService.getPlayerByDiscordId(player.getDiscordID())).thenReturn(player);

        var result = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/player"+PlayerRestController.PATH_GET_BY_DISCORD_ID.replace("{discId}", player.getDiscordID()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(new ObjectMapper().writeValueAsString(expectedPlayerRpCharResponse));
    }

    @Test
    void ensureUpdatePlayerFactionWorks() throws Exception {
        UpdatePlayerFactionDto dto = new UpdatePlayerFactionDto(player.getDiscordID(), mordor.getName());
        player.setFaction(mordor);
        expectedPlayerResponse = new PlayerResponse(player);

        when(mockPlayerService.updatePlayerFaction(dto)).thenReturn(player);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(dto);

        var result = mockMvc.perform(MockMvcRequestBuilders.patch("http://localhost:8080/api/player"+PlayerRestController.PATH_FACTION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedPlayerResponse));
    }

    @Test
    void ensureUpdateIgnWorksProperly() throws Exception {
        UpdatePlayerIgnDto dto = new UpdatePlayerIgnDto("HanslaRoi", player.getDiscordID());
        player.setIgn(dto.ign());
        expectedPlayerResponse = new PlayerResponse(player);

        when(mockPlayerService.updateIgn(dto)).thenReturn(player);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(dto);

        var result = mockMvc.perform(MockMvcRequestBuilders.patch("http://localhost:8080/api/player"+PlayerRestController.PATH_IGN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedPlayerResponse));
    }

    @Test
    void ensureUpdateDiscordIdWorksProperly() throws Exception {
        UpdateDiscordIdDto dto = new UpdateDiscordIdDto(player.getDiscordID(), "NEW" + player.getDiscordID());
        player.setDiscordID(dto.newDiscordId());
        PlayerUpdateDiscordIdResponse expectedResponse = new PlayerUpdateDiscordIdResponse(player, dto.oldDiscordId());

        when(mockPlayerService.updateDiscordId(dto)).thenReturn(player);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(dto);

        var result = mockMvc.perform(MockMvcRequestBuilders.patch("http://localhost:8080/api/player"+PlayerRestController.PATH_DISCORDID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedResponse));
    }

    @Test
    void ensureUpdateCharacterNameWorksProperly() throws Exception {
        UpdateRpCharDto dto = new UpdateRpCharDto(player.getDiscordID(), "New name", null, null, null, null, null);
        rpChar.setName(dto.charName());
        expectedRpCharResponse = new RpCharResponse(rpChar);

        when(mockPlayerService.updateCharacterName(dto)).thenReturn(rpChar);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(dto);

        var result = mockMvc.perform(MockMvcRequestBuilders.patch("http://localhost:8080/api/player"+PlayerRestController.PATH_RPCHAR_NAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedRpCharResponse));
    }

    @Test
    void ensureUpdateCharacterTitleWorksProperly() throws Exception {
        UpdateRpCharDto dto = new UpdateRpCharDto(player.getDiscordID(), null, "New Title", null, null, null, null);
        rpChar.setTitle(dto.title());
        expectedRpCharResponse = new RpCharResponse(rpChar);

        when(mockPlayerService.updateCharacterTitle(dto)).thenReturn(rpChar);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(dto);

        var result = mockMvc.perform(MockMvcRequestBuilders.patch("http://localhost:8080/api/player"+PlayerRestController.PATH_RPCHAR_TITLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedRpCharResponse));
    }

    @Test
    void ensureUpdateCharacterGearWorksProperly() throws Exception {
        UpdateRpCharDto dto = new UpdateRpCharDto(player.getDiscordID(), null, null, null, null, "New Gear", null);
        rpChar.setGear(dto.gear());
        expectedRpCharResponse = new RpCharResponse(rpChar);

        when(mockPlayerService.updateCharacterGear(dto)).thenReturn(rpChar);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(dto);

        var result = mockMvc.perform(MockMvcRequestBuilders.patch("http://localhost:8080/api/player"+PlayerRestController.PATH_RPCHAR_GEAR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedRpCharResponse));
    }

    @Test
    void ensureUpdateCharacterPvPWorksProperly() throws Exception {
        UpdateRpCharDto dto = new UpdateRpCharDto(player.getDiscordID(), null, null, null, null, null, false);
        rpChar.setPvp(dto.pvp());
        expectedRpCharResponse = new RpCharResponse(rpChar);

        when(mockPlayerService.updateCharacterPvp(dto)).thenReturn(rpChar);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(dto);

        var result = mockMvc.perform(MockMvcRequestBuilders.patch("http://localhost:8080/api/player"+PlayerRestController.PATH_RPCHAR_PVP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedRpCharResponse));
    }

    @Test
    void ensureDeletePlayerWorksProperly() throws Exception {
        when(mockPlayerService.deletePlayer(discordIdDto)).thenReturn(player);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(discordIdDto);

        var result = mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedPlayerResponse));
    }

    @Test
    void ensureDeleteRpCharWorksProperly() throws Exception {
        when(mockPlayerService.deleteRpChar(discordIdDto)).thenReturn(rpChar);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(discordIdDto);

        var result = mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/player"+PlayerRestController.PATH_RPCHAR)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedRpCharResponse));
    }

    @Test
    void ensureInjureCharWorksProperly() throws Exception {
        rpChar.setInjured(true);
        expectedRpCharResponse = new RpCharResponse(rpChar);

        when(mockPlayerService.injureChar(discordIdDto)).thenReturn(rpChar);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(discordIdDto);

        var result = mockMvc.perform(MockMvcRequestBuilders.patch("http://localhost:8080/api/player"+PlayerRestController.PATH_INJURE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedRpCharResponse));
    }

    @Test
    void ensureStartHealWorksProperly() throws Exception {
        rpChar.setInjured(true);
        expectedRpCharResponse = new RpCharResponse(rpChar);

        when(mockPlayerService.healStart(discordIdDto)).thenReturn(rpChar);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(discordIdDto);

        var result = mockMvc.perform(MockMvcRequestBuilders.patch("http://localhost:8080/api/player"+PlayerRestController.PATH_HEAL_START)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedRpCharResponse));
    }

    @Test
    void ensureStopHealWorksProperly() throws Exception {
        rpChar.setInjured(true);
        expectedRpCharResponse = new RpCharResponse(rpChar);

        when(mockPlayerService.healStop(discordIdDto)).thenReturn(rpChar);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        String requestJson = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(discordIdDto);

        var result = mockMvc.perform(MockMvcRequestBuilders.patch("http://localhost:8080/api/player"+PlayerRestController.PATH_HEAL_STOP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(expectedRpCharResponse));
    }
}
