package com.ardaslegends.domain.applications;

import com.ardaslegends.domain.Faction;
import com.ardaslegends.domain.Player;
import com.ardaslegends.domain.RPChar;
import com.ardaslegends.presentation.discord.utils.ALColor;
import com.ardaslegends.presentation.discord.utils.DiscordUtils;
import com.ardaslegends.service.exceptions.logic.applications.RoleplayApplicationServiceException;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.stream.Collectors;

/**
 * Represents a roleplay application.
 * This class extends {@link AbstractApplication} and provides specific fields and methods for roleplay applications.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@ToString
@Entity
@Table(name = "roleplay_apps")
public class RoleplayApplication extends AbstractApplication<RPChar> implements DiscordUtils {

    /**
     * The faction associated with the roleplay application.
     */
    @NotNull
    @ManyToOne
    private Faction faction;

    /**
     * The name of the character in the roleplay application.
     */
    @NotBlank
    private String characterName;

    /**
     * The title of the character in the roleplay application.
     */
    @NotBlank
    private String characterTitle;

    /**
     * The reason why the applicant wants to be this character.
     */
    @NotBlank
    private String whyDoYouWantToBeThisCharacter;

    /**
     * The gear associated with the character in the roleplay application.
     */
    @NotBlank
    private String gear;

    /**
     * Indicates whether the character is involved in PvP.
     */
    @NotNull
    private Boolean pvp;

    /**
     * The link to the lore of the character in the roleplay application.
     */
    @NotBlank
    private String linkToLore;

    /**
     * Constructs a new RoleplayApplication.
     *
     * @param applicant                     the player who applied
     * @param faction                       the faction associated with the roleplay application
     * @param characterName                 the name of the character
     * @param characterTitle                the title of the character
     * @param whyDoYouWantToBeThisCharacter the reason why the applicant wants to be this character
     * @param gear                          the gear associated with the character
     * @param pvp                           indicates whether the character is involved in PvP
     * @param linkToLore                    the link to the lore of the character
     */
    public RoleplayApplication(Player applicant, Faction faction, String characterName, String characterTitle, String whyDoYouWantToBeThisCharacter, String gear, Boolean pvp, String linkToLore) {
        super(applicant);
        this.faction = faction;
        this.characterName = characterName;
        this.characterTitle = characterTitle;
        this.whyDoYouWantToBeThisCharacter = whyDoYouWantToBeThisCharacter;
        this.gear = gear;
        this.pvp = pvp;
        this.linkToLore = linkToLore;
    }

    /**
     * Builds the application message embed.
     *
     * @return the application message embed
     */
    @Override
    public EmbedBuilder buildApplicationMessage() {
        return new EmbedBuilder()
                .setTitle("Roleplay Character Application")
                .addField("Applicant", applicant.getIgn())
                .addField("Character", characterName)
                .addField("Title", characterTitle)
                .addField("Faction", faction.getName())
                .addField("Gear", gear)
                .addField("Link to RP", linkToLore)
                .setColor(ALColor.YELLOW)
                .setThumbnail(getFactionBanner(faction.getName()))
                .setTimestampToNow()
                .addField("Upvoted By", getAcceptedBy().stream().map(Player::getIgn).collect(Collectors.joining(", ")))
                .addInlineField("Downvoted By", getDeclinedBy().stream().map(Player::getIgn).collect(Collectors.joining(", ")));
    }

    /**
     * Builds the accepted message embed.
     *
     * @return the accepted message embed
     */
    @Override
    public EmbedBuilder buildAcceptedMessage() {
        return new EmbedBuilder()
                .setTitle("Accepted: " + applicant.getIgn() + "'s Character")
                .addField("Character", characterName)
                .addInlineField("Title", characterTitle)
                .addField("Faction", faction.getName())
                .addInlineField("Gear", gear)
                .addField("Link to RP", linkToLore)
                .setColor(ALColor.GREEN)
                .setThumbnail(getFactionBanner(faction.getName()))
                .setTimestampToNow();
    }

    /**
     * Finishes the application process and creates a {@link RPChar}.
     *
     * @return the created {@link RPChar}
     * @throws RoleplayApplicationServiceException if the application state is not accepted
     */
    @Override
    protected RPChar finishApplication() {
        if (state != ApplicationState.ACCEPTED) {
            throw RoleplayApplicationServiceException.applicationNotYetAccepted(getId());
        }
        return new RPChar(this);
    }

    /**
     * Returns the required vote count for the application to be accepted.
     *
     * @return the required vote count
     */
    @Override
    protected Short getRequiredVoteCount() {
        return 2;
    }
}